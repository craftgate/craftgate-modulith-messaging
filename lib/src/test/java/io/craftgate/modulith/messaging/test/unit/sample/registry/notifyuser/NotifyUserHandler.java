package io.craftgate.modulith.messaging.test.unit.sample.registry.notifyuser;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.MessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.registry.createuser.CreateUserUseCase;
import io.craftgate.modulith.messaging.test.unit.sample.registry.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.registry.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@MessageHandlerConfig(selector = CreateUserUseCase.class, isChained = true)
public class NotifyUserHandler extends MessageHandler<CreateUserUseCase, User> {

    private final UserPort userPort;

    @Override
    public User handle(CreateUserUseCase message) {
        User user = userPort.retrieveUser(message.getUsername());
        user.doNotify();
        log.info("User notified: {}", user);
        return user;
    }
}
