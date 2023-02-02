package io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.notifyuser;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.createuser.UserCreatedDomainEvent;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.shared.User;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
@MessageHandlerConfig(selector = UserCreatedDomainEvent.class, isChained = true, isTransactional = true)
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
