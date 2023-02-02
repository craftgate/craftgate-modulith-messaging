package io.craftgate.modulith.messaging.test.unit;

import io.craftgate.modulith.messaging.api.MessagePublisher;
import io.craftgate.modulith.messaging.api.registry.MessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.NoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidNoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.util.CurrentDateTimeManager;
import io.craftgate.modulith.messaging.test.unit.sample.failingunchained.blockuser.BlockFailureException;
import io.craftgate.modulith.messaging.test.unit.sample.failingunchained.blockuser.BlockUserHandler;
import io.craftgate.modulith.messaging.test.unit.sample.failingunchained.blockuser.BlockUserUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FailingUnchainedTest extends AbstractUnitTest {

    @Test
    void should_block_user_when_handlers_are_unchained() {
        // setup
        new MessagePublisher(
                new MessageHandlerRegistry(new BlockUserHandler()),
                new NoMessageHandlerRegistry(),
                new VoidMessageHandlerRegistry(),
                new VoidNoMessageHandlerRegistry()
        );

        // given
        BlockUserUseCase useCase = BlockUserUseCase.builder()
                .username("rcarlos")
                .blockReason("fraud")
                .blockExpiryDate(CurrentDateTimeManager.now().plusDays(1))
                .build();

        // when
        assertThrows(BlockFailureException.class, () -> MessagePublisher.publishAndGet(useCase));
    }
}
