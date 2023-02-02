package io.craftgate.modulith.messaging.test.integration.sample.chained.configuration;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = {
                "io.craftgate.modulith.messaging.test.integration.sample.chained",
                "io.craftgate.modulith.messaging.api",
        },
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = {DomainComponent.class}
                )
        }
)
public class TransactionalChainedComponentScanConfiguration {

}
