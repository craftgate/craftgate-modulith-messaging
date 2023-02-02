package io.craftgate.modulith.messaging.test.unit.sample.unchained.auditactivities;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser.UserBlockedDomainEvent;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@MessageHandlerConfig(selector = UserBlockedDomainEvent.class, isChained = false)
public class AuditUserBlockedHandler extends VoidMessageHandler<UserBlockedDomainEvent> {

    public void handle(UserBlockedDomainEvent useCase) {
        log.info("Audit happened for user blocked event");
        publish("AUDIT_COMPLETED");
    }
}
