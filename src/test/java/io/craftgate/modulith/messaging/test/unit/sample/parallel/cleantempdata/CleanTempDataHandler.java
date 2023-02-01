package io.craftgate.modulith.messaging.test.unit.sample.parallel.cleantempdata;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import io.craftgate.modulith.messaging.test.unit.sample.parallel.blockuser.UserBlockedDomainEvent;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@MessageHandlerConfig(selector = UserBlockedDomainEvent.class, isChained = false)
public class CleanTempDataHandler extends VoidMessageHandler<UserBlockedDomainEvent> {

    @Override
    protected void handle(UserBlockedDomainEvent message) {
        log.info("Clean temp data is happened");
        publish("CLEANUP_COMPLETED");
    }
}
