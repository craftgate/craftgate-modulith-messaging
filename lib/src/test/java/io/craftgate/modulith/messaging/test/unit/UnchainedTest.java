package io.craftgate.modulith.messaging.test.unit;

import io.craftgate.modulith.messaging.test.AbstractTest;
import io.craftgate.modulith.messaging.api.MessagePublisher;
import io.craftgate.modulith.messaging.test.ThreadUtils;
import io.craftgate.modulith.messaging.api.registry.MessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.NoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidNoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.auditactivities.AuditUserBlockedHandler;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser.BlockUserHandler;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser.BlockUserUseCase;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser.FakeUserDataAdapter;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser.UserBlockedDomainEvent;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.cleantempdata.CleanTempDataHandler;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.shared.User;
import io.craftgate.modulith.messaging.api.util.CurrentDateTimeProvider;
import io.craftgate.modulith.messaging.api.util.MessageTestCollector;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.craftgate.modulith.messaging.test.ThreadUtils.WaitMatcher.ALL;
import static org.assertj.core.api.Assertions.assertThat;

public class UnchainedTest extends AbstractTest {

    @Test
    void should_block_user_when_handlers_are_unchained() throws InterruptedException {
        // setup
        FakeUserDataAdapter fakeUserPort = new FakeUserDataAdapter();
        new MessagePublisher(
                new MessageHandlerRegistry(new BlockUserHandler(fakeUserPort)),
                new NoMessageHandlerRegistry(),
                new VoidMessageHandlerRegistry(new AuditUserBlockedHandler()),
                new VoidNoMessageHandlerRegistry(new CleanTempDataHandler())
        );

        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        CurrentDateTimeProvider.setCustomLocalDateTime(now);

        User user = User.of("rcarlos", "Roberto", "Carlos");
        fakeUserPort.initialize(user);

        // given
        BlockUserUseCase useCase = BlockUserUseCase.builder()
                .username("rcarlos")
                .blockReason("fraud")
                .blockExpiryDate(CurrentDateTimeProvider.now().plusDays(1))
                .build();

        // when
        MessagePublisher.publish(useCase);
        ThreadUtils.waitCompletion(ALL, "CLEANUP_COMPLETED");

        // then
        assertThat(user).isEqualTo(user.toBuilder()
                .isBlocked(true)
                .blockReason("fraud")
                .blockExpiryDate(CurrentDateTimeProvider.now().plusDays(1))
                .build());

        assertThat(user.getConsumedMessages())
                .hasSize(1)
                .contains((UserBlockedDomainEvent.builder()
                        .username("rcarlos")
                        .name("Roberto")
                        .surname("Carlos")
                        .registerDate(now)
                        .isBlocked(true)
                        .blockReason("fraud")
                        .blockExpiryDate(CurrentDateTimeProvider.now().plusDays(1))
                        .build())
                );

        assertThat(MessageTestCollector.dumpMessages())
                .hasSize(2)
                .contains(UserBlockedDomainEvent.builder()
                                .username("rcarlos")
                                .name("Roberto")
                                .surname("Carlos")
                                .registerDate(now)
                                .isBlocked(true)
                                .blockReason("fraud")
                                .blockExpiryDate(CurrentDateTimeProvider.now().plusDays(1))
                                .build(),
                        BlockUserUseCase.builder()
                                .username("rcarlos")
                                .blockReason("fraud")
                                .blockExpiryDate(CurrentDateTimeProvider.now().plusDays(1))
                                .build()
                );

        assertThat(MessageTestCollector.dumpKeys())
                .hasSize(2)
                .contains("AUDIT_COMPLETED", "CLEANUP_COMPLETED");
    }
}
