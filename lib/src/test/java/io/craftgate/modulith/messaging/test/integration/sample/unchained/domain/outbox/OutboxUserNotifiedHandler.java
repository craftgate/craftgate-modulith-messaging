package io.craftgate.modulith.messaging.test.integration.sample.unchained.domain.outbox;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.integration.sample.unchained.domain.notifyuser.UserNotifiedDomainEvent;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@DomainComponent
@MessageHandlerConfig(selector = UserNotifiedDomainEvent.class, isChained = false, isTransactional = true)
public class OutboxUserNotifiedHandler extends VoidMessageHandler<UserNotifiedDomainEvent> {

    @Override
    public void handle(UserNotifiedDomainEvent useCase) {
        log.info("Outbox save is happened");
        publish("OUTBOX_COMPLETED");
    }
}
