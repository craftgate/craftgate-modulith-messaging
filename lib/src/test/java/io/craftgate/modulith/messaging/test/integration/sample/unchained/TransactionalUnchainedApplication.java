package io.craftgate.modulith.messaging.test.integration.sample.unchained;

import io.craftgate.modulith.messaging.test.integration.sample.unchained.configuration.TransactionalUnchainedComponentScanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TransactionalUnchainedComponentScanConfiguration.class)
public class TransactionalUnchainedApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionalUnchainedApplication.class, args);
    }
}
