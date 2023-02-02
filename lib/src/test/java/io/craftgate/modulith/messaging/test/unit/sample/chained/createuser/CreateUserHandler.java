package io.craftgate.modulith.messaging.test.unit.sample.chained.createuser;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.MessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@MessageHandlerConfig(selector = CreateUserUseCase.class, isChained = true, isTransactional = true)
public class CreateUserHandler extends MessageHandler<CreateUserUseCase, User> {

    private final UserPort userPort;

    @Override
    public User handle(CreateUserUseCase message) {
        User user = User.create(message);
        userPort.saveUser(user);

        log.info("User created: {}", user);
        return user;
    }
}
