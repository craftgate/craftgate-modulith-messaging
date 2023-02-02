package io.craftgate.modulith.messaging.test.unit;

import io.craftgate.modulith.messaging.test.AbstractTest;
import io.craftgate.modulith.messaging.api.MessagePublisher;
import io.craftgate.modulith.messaging.api.registry.MessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.NoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidNoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.test.unit.sample.chained.createuser.CreateUserHandler;
import io.craftgate.modulith.messaging.test.unit.sample.chained.createuser.CreateUserUseCase;
import io.craftgate.modulith.messaging.test.unit.sample.chained.createuser.FakeUserDataAdapter;
import io.craftgate.modulith.messaging.test.unit.sample.chained.createuser.UserCreatedDomainEvent;
import io.craftgate.modulith.messaging.test.unit.sample.chained.notifyuser.NotifyUserHandler;
import io.craftgate.modulith.messaging.test.unit.sample.chained.notifyuser.UserNotifiedDomainEvent;
import io.craftgate.modulith.messaging.test.unit.sample.chained.outbox.OutboxUserNotifiedHandler;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.User;
import io.craftgate.modulith.messaging.api.util.CurrentDateTimeProvider;
import io.craftgate.modulith.messaging.api.util.MessageTestCollector;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ChainedTest extends AbstractTest {

    @Test
    void should_create_user_when_handlers_are_chained() {
        // setup
        FakeUserDataAdapter fakeUserPort = new FakeUserDataAdapter();
        new MessagePublisher(
                new MessageHandlerRegistry(new CreateUserHandler(fakeUserPort)),
                new NoMessageHandlerRegistry(),
                new VoidMessageHandlerRegistry(new OutboxUserNotifiedHandler(), new NotifyUserHandler(fakeUserPort)),
                new VoidNoMessageHandlerRegistry()
        );

        // given
        MessageTestCollector.activate();
        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        CurrentDateTimeProvider.setCustomLocalDateTime(now);
        var usecase = CreateUserUseCase.builder()
                .username("rcarlos")
                .name("Roberto")
                .surname("Carlos")
                .build();

        // when
        var createdUser = MessagePublisher.publishAndGet(usecase);

        // then
        assertThat(createdUser).isEqualTo(User.of("rcarlos", "Roberto", "Carlos"));

        assertThat(createdUser.getConsumedMessages())
                .hasSize(2)
                .contains(UserNotifiedDomainEvent.builder()
                                .username("rcarlos")
                                .build(),
                        (UserCreatedDomainEvent.builder()
                                .username("rcarlos")
                                .name("Roberto")
                                .surname("Carlos")
                                .registerDate(now)
                                .build())
                );

        assertThat(MessageTestCollector.dumpMessages())
                .hasSize(3)
                .contains(UserNotifiedDomainEvent.builder()
                                .username("rcarlos")
                                .build(),
                        UserCreatedDomainEvent.builder()
                                .username("rcarlos")
                                .name("Roberto")
                                .surname("Carlos")
                                .registerDate(now)
                                .build(),
                        CreateUserUseCase.builder()
                                .username("rcarlos")
                                .name("Roberto")
                                .surname("Carlos")
                                .build()
                );

        assertThat(MessageTestCollector.dumpKeys())
                .hasSize(1)
                .contains("OUTBOX_COMPLETED");
    }
}
