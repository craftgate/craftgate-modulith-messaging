package io.craftgate.modulith.messaging.test.unit;

import io.craftgate.modulith.messaging.api.util.MessageTestCollector;
import io.craftgate.modulith.messaging.api.util.TransactionManagerProvider;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.FakePlatformTransactionalManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AbstractUnitTest {

    @BeforeEach
    void setUp() {
        MessageTestCollector.activate();
        new TransactionManagerProvider(new FakePlatformTransactionalManager());
    }

    @AfterEach
    void tearDown() {
        MessageTestCollector.reset();
    }

}
