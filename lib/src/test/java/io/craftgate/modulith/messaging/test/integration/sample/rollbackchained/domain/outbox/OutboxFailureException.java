package io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.outbox;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutboxFailureException extends RuntimeException {

    private final String key;
    private final String[] args;

    public OutboxFailureException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public OutboxFailureException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
