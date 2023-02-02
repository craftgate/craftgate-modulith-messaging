package io.craftgate.modulith.messaging.test.unit.sample.registry.auditactivities;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.NoMessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.registry.shared.User;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@MessageHandlerConfig(key = "POST_PROCESS", isChained = true)
public class AuditUserBlockedHandler extends NoMessageHandler<User> {

    @Override
    protected User handle() {
        log.info("Audit save is happened");
        publish("AUDIT_COMPLETED");
        return null;
    }
}
