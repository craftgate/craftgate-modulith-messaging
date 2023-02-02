package io.craftgate.modulith.messaging.test.integration.sample.chained.domain.createuser;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.MessageHandler;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.UserPort;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
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
