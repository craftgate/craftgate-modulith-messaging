package io.craftgate.modulith.messaging.test.unit.sample.parallel.auditactivities;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.parallel.blockuser.UserBlockedDomainEvent;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@MessageHandlerConfig(selector = UserBlockedDomainEvent.class, isChained = false)
public class AuditUserBlockedHandler extends VoidMessageHandler<UserBlockedDomainEvent> {

    @Override
    public void handle(UserBlockedDomainEvent useCase) {
        try {
            log.debug("waiting for 1 sec");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Audit happened for user blocked event");
        publish("AUDIT_COMPLETED");
    }
}
