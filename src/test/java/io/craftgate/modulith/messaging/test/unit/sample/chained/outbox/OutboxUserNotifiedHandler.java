package io.craftgate.modulith.messaging.test.unit.sample.chained.outbox;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.chained.notifyuser.UserNotifiedDomainEvent;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@MessageHandlerConfig(selector = UserNotifiedDomainEvent.class, isChained = true)
public class OutboxUserNotifiedHandler extends VoidMessageHandler<UserNotifiedDomainEvent> {

    @Override
    protected void handle(UserNotifiedDomainEvent message) {
        log.info("Outbox save is happened");
        publish("OUTBOX_COMPLETED");
    }
}
