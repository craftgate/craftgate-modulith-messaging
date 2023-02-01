package io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.outbox;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.notifyuser.UserNotifiedDomainEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@MessageHandlerConfig(selector = UserNotifiedDomainEvent.class, isChained = false, isTransactional = true)
public class OutboxUserCreatedHandler extends VoidMessageHandler<UserNotifiedDomainEvent> {

    @Override
    public void handle(UserNotifiedDomainEvent useCase) {
        log.info("Outbox save is happened");
        throw new RuntimeException("fail fail fail");
    }
}
