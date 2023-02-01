package io.craftgate.modulith.messaging.test.unit.sample.unchained.cleantempdata;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.handler.VoidNoMessageHandler;
import lombok.extern.slf4j.Slf4j;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publish;

@Slf4j
@MessageHandlerConfig(key = {"AUDIT_COMPLETED", "XYZ_HAPPENED"}, isChained = false)
public class CleanTempDataHandler extends VoidNoMessageHandler {

    @Override
    protected void handle() {
        log.info("Clean temp data is happened");
        publish("CLEANUP_COMPLETED");
    }
}
