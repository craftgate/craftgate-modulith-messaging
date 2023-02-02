package io.craftgate.modulith.messaging.api.handler;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.api.annotation.Registerable;
import io.craftgate.modulith.messaging.api.util.TransactionManagerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
public abstract class VoidMessageHandler<M extends Message> implements Registerable {

    protected abstract void handle(M message);

    public void process(M message) {
        if (isTransactional()) {
            var transactionTemplate = new TransactionTemplate(TransactionManagerProvider.provide());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    execute(message);
                }
            });
            return;
        }
        execute(message);
    }

    private void execute(M message) {
        log.debug("[======== Chain Start ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
        log.debug("Void Message Handler ({}) process is started with message {}", this.getClass().getSimpleName(), message);
        handle(message);
        log.debug("Void Message Handler ({}) process is completed", this.getClass().getSimpleName());
        log.debug("[======== Chain End ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
    }
}
