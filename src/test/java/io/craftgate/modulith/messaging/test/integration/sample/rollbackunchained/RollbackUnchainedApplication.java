package io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained;

import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.configuration.RollbackUnchainedComponentScanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RollbackUnchainedComponentScanConfiguration.class)
public class RollbackUnchainedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RollbackUnchainedApplication.class, args);
    }
}
