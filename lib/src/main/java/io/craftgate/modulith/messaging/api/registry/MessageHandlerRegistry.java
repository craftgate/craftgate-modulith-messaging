package io.craftgate.modulith.messaging.api.registry;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.exception.HandlerRegistryNotAllowedException;
import io.craftgate.modulith.messaging.api.handler.MessageHandler;
import io.craftgate.modulith.messaging.api.model.Message;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@ToString
@DomainComponent
public class MessageHandlerRegistry extends Registry {

    private Multimap<String, MessageHandler<?, ?>> mapping = LinkedHashMultimap.create();

    @SafeVarargs
    public MessageHandlerRegistry(MessageHandler<? extends Message, ?>... messageHandlers) {
        Arrays.stream(messageHandlers).forEach(messageHandler -> {
            Arrays.stream(messageHandler.selector()).forEach(selector -> {
                if (mapping.containsKey(selector)) throw new HandlerRegistryNotAllowedException("io.craftgate.messaging.handlerRegistryNotAllowed", messageHandler.getClass().getSimpleName());
                mapping.put(selector, messageHandler);
            });
            log.debug("Message Handler registered: {} => {}", messageHandler.selector(), messageHandler.getClass().getSimpleName());
        });
    }

    public Collection<MessageHandler<?, ?>> find(String key) {
        return this.mapping.get(key);
    }
}
