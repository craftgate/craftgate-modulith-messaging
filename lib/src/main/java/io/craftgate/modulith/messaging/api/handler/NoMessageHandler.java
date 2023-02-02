package io.craftgate.modulith.messaging.api.handler;

import io.craftgate.modulith.messaging.api.annotation.MessageHandlerConfig;
import io.craftgate.modulith.messaging.api.model.AggregateRoot;
import io.craftgate.modulith.messaging.api.annotation.Registerable;
import io.craftgate.modulith.messaging.api.util.TransactionManagerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

@Slf4j
public abstract class NoMessageHandler<R extends AggregateRoot> implements Registerable {

    protected abstract R handle();

    public R process() {
        if (isTransactional()) {
            var transactionTemplate = new TransactionTemplate(TransactionManagerProvider.provide());
            return transactionTemplate.execute(status -> execute());
        }
        return execute();
    }

    private R execute() {
        log.debug("[======== Chain Start ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
        log.debug("No Message Handler ({}) process is started", this.getClass().getSimpleName());
        R aggregate = handle();
        if (Objects.isNull(aggregate)) return null;

        aggregate.publishMessages();
        log.debug("No Message Handler ({}) process is completed", this.getClass().getSimpleName());
        log.debug("[======== Chain End ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
        return aggregate;
    }
}
