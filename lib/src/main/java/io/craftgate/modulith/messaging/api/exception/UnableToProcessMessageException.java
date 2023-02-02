package io.craftgate.modulith.messaging.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnableToProcessMessageException extends RuntimeException {

    private final String key;
    private final String[] args;

    public UnableToProcessMessageException(String key, Exception e) {
        super(key, e);
        this.key = key;
        args = new String[0];
    }

    public UnableToProcessMessageException(String key, Exception e, String... args) {
        super(key, e);
        this.key = key;
        this.args = args;
    }
}
