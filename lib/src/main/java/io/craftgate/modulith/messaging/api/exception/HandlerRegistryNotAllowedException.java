package io.craftgate.modulith.messaging.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandlerRegistryNotAllowedException extends RuntimeException {

    private final String key;
    private final String[] args;

    public HandlerRegistryNotAllowedException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public HandlerRegistryNotAllowedException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
