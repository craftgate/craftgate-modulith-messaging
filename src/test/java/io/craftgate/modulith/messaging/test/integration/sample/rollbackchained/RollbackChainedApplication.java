package io.craftgate.modulith.messaging.test.integration.sample.rollbackchained;

import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.configuration.RollbackChainedComponentScanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RollbackChainedComponentScanConfiguration.class)
public class RollbackChainedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RollbackChainedApplication.class, args);
    }
}
