package io.craftgate.modulith.messaging.test.integration.sample.chained.domain.notifyuser;

import io.craftgate.modulith.messaging.api.MessagePublisher;
import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.createuser.UserCreatedDomainEvent;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.UserPort;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.User;
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
        log.info("User notified: {}", user);
        user.publishMessages();
    }
}
