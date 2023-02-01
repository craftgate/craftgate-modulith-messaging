package io.craftgate.modulith.messaging.test.unit.sample.chained.notifyuser;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.chained.createuser.UserCreatedDomainEvent;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.UserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@MessageHandlerConfig(selector = UserCreatedDomainEvent.class, isChained = true)
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
