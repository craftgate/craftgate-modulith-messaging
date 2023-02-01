package io.craftgate.modulith.messaging.test.integration.sample.chained;

import io.craftgate.modulith.messaging.test.integration.sample.chained.configuration.TransactionalChainedComponentScanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TransactionalChainedComponentScanConfiguration.class)
public class TransactionalChainedApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionalChainedApplication.class, args);
    }
}
