package io.craftgate.modulith.messaging.api.registry;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.handler.VoidMessageHandler;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@ToString
@DomainComponent
public class VoidMessageHandlerRegistry extends Registry {

    private final Multimap<String, VoidMessageHandler<?>> mapping = LinkedHashMultimap.create();

    @SafeVarargs
    public VoidMessageHandlerRegistry(VoidMessageHandler<? extends Message>... messageHandlers) {
        Arrays.stream(messageHandlers).forEach(messageHandler -> {
            Arrays.stream(messageHandler.selector()).forEach(selector -> mapping.put(selector, messageHandler));
            log.debug("Void Message Handler registered: {} => {}", messageHandler.selector(), messageHandler.getClass().getSimpleName());
        });
    }

    public Collection<VoidMessageHandler<?>> find(String key) {
        return this.mapping.get(key);
    }
}
