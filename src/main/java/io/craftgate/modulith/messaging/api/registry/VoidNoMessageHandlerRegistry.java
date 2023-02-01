package io.craftgate.modulith.messaging.api.registry;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.handler.VoidNoMessageHandler;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@ToString
@DomainComponent
public class VoidNoMessageHandlerRegistry extends Registry {

    private final Multimap<String, VoidNoMessageHandler> mapping = LinkedHashMultimap.create();

    public VoidNoMessageHandlerRegistry(VoidNoMessageHandler... messageHandlers) {
        Arrays.stream(messageHandlers).forEach(messageHandler -> {
            Arrays.stream(messageHandler.selector()).forEach(selector -> mapping.put(selector, messageHandler));
            log.debug("Void No Message handler registered: {} => {}", messageHandler.selector(), messageHandler.getClass().getSimpleName());
        });
    }

    public Collection<VoidNoMessageHandler> find(String key) {
        return this.mapping.get(key);
    }
}
