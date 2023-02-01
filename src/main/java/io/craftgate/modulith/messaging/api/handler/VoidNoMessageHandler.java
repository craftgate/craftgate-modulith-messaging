package io.craftgate.modulith.messaging.api.handler;

import io.craftgate.modulith.messaging.api.annotation.Registerable;
import io.craftgate.modulith.messaging.api.util.TransactionManagerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
public abstract class VoidNoMessageHandler implements Registerable {

    protected abstract void handle();

    public void process() {
        if (isTransactional()) {
            var transactionTemplate = new TransactionTemplate(TransactionManagerProvider.provide());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    execute();
                }
            });
            return;
        }
        execute();
    }

    private void execute() {
        log.debug("[======== Chain Start ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
        log.debug("Void No Message Handler ({}) process is started", this.getClass().getSimpleName());
        handle();
        log.debug("Void No Message Handler ({}) process is completed", this.getClass().getSimpleName());
        log.debug("[======== Chain End ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
    }
}
