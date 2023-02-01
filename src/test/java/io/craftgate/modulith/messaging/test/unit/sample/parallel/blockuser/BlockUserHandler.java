package io.craftgate.modulith.messaging.test.unit.sample.parallel.blockuser;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.MessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.parallel.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.parallel.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@MessageHandlerConfig(selector = BlockUserUseCase.class, isChained = false)
public class BlockUserHandler extends MessageHandler<BlockUserUseCase, User> {

    private final UserPort userPort;

    @Override
    public User handle(BlockUserUseCase message) {
        var user = userPort.retrieveUser(message.getUsername());
        user.block(message.getBlockReason(), message.getBlockExpiryDate());
        userPort.saveUser(user);

        log.info("User blocked: {}", user);
        return user;
    }
}
