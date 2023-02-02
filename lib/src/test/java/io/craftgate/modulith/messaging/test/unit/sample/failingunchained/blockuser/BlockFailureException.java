package io.craftgate.modulith.messaging.test.unit.sample.failingunchained.blockuser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockFailureException extends RuntimeException {

    private final String key;
    private final String[] args;

    public BlockFailureException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public BlockFailureException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
