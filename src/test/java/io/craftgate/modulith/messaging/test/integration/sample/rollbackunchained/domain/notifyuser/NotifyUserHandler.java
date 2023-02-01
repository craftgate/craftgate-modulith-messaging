package io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.notifyuser;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.createuser.UserCreatedDomainEvent;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.shared.User;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
@MessageHandlerConfig(selector = UserCreatedDomainEvent.class, isChained = false, isTransactional = true)
public class NotifyUserHandler extends VoidMessageHandler<UserCreatedDomainEvent> {

    private final UserPort userPort;

    @Override
    public void handle(UserCreatedDomainEvent message) {
        User user = userPort.retrieveUser(message.getUsername());
        user.doNotify();
        user.publishMessages();
        log.info("User notified: {}", user);
    }
}
