package io.craftgate.modulith.messaging.api.util;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import org.springframework.transaction.PlatformTransactionManager;

@DomainComponent
public class TransactionManagerProvider {

    private static PlatformTransactionManager transactionManager;

    public TransactionManagerProvider(PlatformTransactionManager transactionManager) {
        TransactionManagerProvider.transactionManager = transactionManager;
    }

    public static PlatformTransactionManager provide() {
        return transactionManager;
    }
}
