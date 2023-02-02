package io.craftgate.modulith.messaging.test.unit.sample.failingunchained.blockuser;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.MessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.failingunchained.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.failingunchained.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@MessageHandlerConfig(selector = BlockUserUseCase.class, isChained = false)
public class BlockUserHandler extends MessageHandler<BlockUserUseCase, User> {

    @Override
    public User handle(BlockUserUseCase message) {
        throw new BlockFailureException("fail fail fail");
    }
}
