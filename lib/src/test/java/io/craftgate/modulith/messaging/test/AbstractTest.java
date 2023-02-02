package io.craftgate.modulith.messaging.test;

import io.craftgate.modulith.messaging.api.util.MessageTestCollector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AbstractTest {

    @BeforeEach
    void setUp() {
        MessageTestCollector.activate();
    }

    @AfterEach
    void tearDown() {
        MessageTestCollector.reset();
    }

}
