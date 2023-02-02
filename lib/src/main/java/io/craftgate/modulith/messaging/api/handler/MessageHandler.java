package io.craftgate.modulith.messaging.api.handler;

import io.craftgate.modulith.messaging.api.annotation.Registerable;
import io.craftgate.modulith.messaging.api.model.AggregateRoot;
import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.api.util.TransactionManagerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

@Slf4j
public abstract class MessageHandler<M extends Message, R extends AggregateRoot> implements Registerable {

    protected abstract R handle(M message);

    public R process(M message) {
        if (isTransactional()) {
            var transactionTemplate = new TransactionTemplate(TransactionManagerProvider.provide());
            return transactionTemplate.execute(status -> execute(message));
        }
        return execute(message);
    }

    private R execute(M message) {
        log.debug("[======== Chain Start ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
        log.debug("Message Handler ({}) process is started with message {}", this.getClass().getSimpleName(), message);
        R aggregate = handle(message);
        if (Objects.isNull(aggregate)) return null;
        
        aggregate.publishMessages();
        log.debug("Message Handler ({}) process is ended", this.getClass().getSimpleName());
        log.debug("[======== Chain End ({}) ========]", this.getClass().getSimpleName() + "-" + Thread.currentThread().getName());
        return aggregate;
    }
}
