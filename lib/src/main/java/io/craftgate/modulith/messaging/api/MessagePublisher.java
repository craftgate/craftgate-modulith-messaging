package io.craftgate.modulith.messaging.api;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.api.exception.UnableToFindMessageHandlerException;
import io.craftgate.modulith.messaging.api.exception.UnableToProcessMessageException;
import io.craftgate.modulith.messaging.api.handler.*;
import io.craftgate.modulith.messaging.api.model.AggregateRoot;
import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.api.registry.*;
import io.craftgate.modulith.messaging.api.util.MessageTestCollector;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
@ToString
@DomainComponent
public class MessagePublisher {

    private static final Executor executor = Executors.newFixedThreadPool(100);

    private final MessageHandlerRegistry messageHandlerRegistry;
    private final NoMessageHandlerRegistry noMessageHandlerRegistry;
    private final VoidNoMessageHandlerRegistry voidNoMessageHandlerRegistry;
    private final VoidMessageHandlerRegistry voidMessageHandlerRegistry;

    private static MessageTestCollector messageTestCollector;

    public MessagePublisher(MessageHandlerRegistry messageHandlerRegistry,
                            NoMessageHandlerRegistry noMessageHandlerRegistry,
                            VoidMessageHandlerRegistry voidMessageHandlerRegistry,
                            VoidNoMessageHandlerRegistry voidNoMessageHandlerRegistry) {
        this.messageHandlerRegistry = messageHandlerRegistry;
        this.noMessageHandlerRegistry = noMessageHandlerRegistry;
        this.voidMessageHandlerRegistry = voidMessageHandlerRegistry;
        this.voidNoMessageHandlerRegistry = voidNoMessageHandlerRegistry;
        MessagePublisherContext.instance = this;
        log.debug("Message published setup completed: {}", this);
    }

    public static void registerMessageCollector(MessageTestCollector messageTestCollector) {
        MessagePublisher.messageTestCollector = messageTestCollector;
    }

    /****************************************
     * MESSAGE HANDLER PROCESSORS
     ***************************************/

    @SuppressWarnings("unchecked")
    private static <M extends Message> void publishWithMessageHandler(M message, MessageHandler<?, ?> messageHandler) {
        log.debug("Message Handler ({}) detected and triggered with message: {}", messageHandler.getClass().getSimpleName(), message);
        try {
            if (messageHandler.isChained()) ((MessageHandler<M, ?>) messageHandler).process(message);
            else new Thread(() -> ((MessageHandler<M, ?>) messageHandler).process(message)).start();
        } catch (Exception e) {
            log.debug("Error occurred when message handler ({}) processes message: {}", messageHandler.getClass().getSimpleName(), message, e);
            throw e;
        }
    }

    private static void publishWithNoMessageHandler(String key, NoMessageHandler<?> messageHandler) {
        log.debug("No Message Handler ({}) detected and triggered with key: {}", messageHandler.getClass().getSimpleName(), key);
        try {
            if (messageHandler.isChained()) messageHandler.process();
            else new Thread(messageHandler::process).start();
        } catch (Exception e) {
            log.debug("Error occurred when no message handler ({}) processes", messageHandler.getClass().getSimpleName(), e);
            throw e;
        }
    }

    private static void publishWithVoidNoMessageHandler(String key, VoidNoMessageHandler messageHandler) {
        log.debug("Void No Message handler ({}) detected and triggered with key: {}", messageHandler.getClass().getSimpleName(), key);
        try {
            if (messageHandler.isChained()) messageHandler.process();
            else new Thread(messageHandler::process).start();
        } catch (Exception e) {
            log.debug("Error occurred when void no message handler ({}) processes", messageHandler.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    private static <M extends Message> void publishWithVoidMessageHandler(M message, VoidMessageHandler<?> messageHandler) {
        log.debug("Void Message Handler ({}) detected and triggered with key: {}", messageHandler.getClass().getSimpleName(), message);
        try {
            if (messageHandler.isChained()) ((VoidMessageHandler<M>) messageHandler).process(message);
            else new Thread(() -> ((VoidMessageHandler<M>) messageHandler).process(message)).start();
        } catch (Exception e) {
            log.debug("Error occurred when void message handler ({}) processes message: {}", messageHandler.getClass().getSimpleName(), message, e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    private static <M extends Message> AggregateRoot publishAndGetWithMessageHandler(M message, MessageHandler<?, ?> messageHandler) {
        log.debug("Message Handler ({}) detected and triggered with key: {}", messageHandler.getClass().getSimpleName(), message);
        try {
            if (messageHandler.isChained()) return ((MessageHandler<M, ?>) messageHandler).process(message);
            else {
                FutureTask<?> futureTask = new FutureTask<>(() -> ((MessageHandler<M, ?>) messageHandler).process(message));
                new Thread(futureTask).start();
                return (AggregateRoot) futureTask.get();
            }
        } catch (InterruptedException ier) {
            log.debug("Thread interrupted when message handler ({}) processes message: {}", messageHandler.getClass().getSimpleName(), message, ier);
            Thread.currentThread().interrupt();
            throw new UnableToProcessMessageException("io.craftgate.messaging.processInterrupted", ier);
        } catch (ExecutionException ex) {
            log.debug("Error occurred during execution when message handler ({}) processes message: {}", messageHandler.getClass().getSimpleName(), message, ex.getCause());
            throw (RuntimeException) ex.getCause();
        } catch (Exception e) {
            log.debug("Error occurred when message handler ({}) processes message: {}", messageHandler.getClass().getSimpleName(), message, e);
            throw e;
        }
    }

    private static AggregateRoot publishAndGetWithNoMessageHandler(String key, NoMessageHandler<?> messageHandler) {
        log.debug("No Message Handler ({}) detected and triggered with key: {}", messageHandler.getClass().getSimpleName(), key);
        try {
            if (messageHandler.isChained()) return messageHandler.process();
            else {
                FutureTask<?> futureTask = new FutureTask<>(messageHandler::process);
                new Thread(futureTask).start();
                return (AggregateRoot) futureTask.get();
            }
        } catch (InterruptedException ier) {
            log.debug("Thread interrupted when message handler ({}) processes with key: {}", messageHandler.getClass().getSimpleName(), key, ier);
            Thread.currentThread().interrupt();
            throw new UnableToProcessMessageException("io.craftgate.messaging.processInterrupted", ier);
        } catch (ExecutionException ex) {
            log.debug("Error occurred during execution when message handler ({}) processes with key: {}", messageHandler.getClass().getSimpleName(), key, ex.getCause());
            throw (RuntimeException) ex.getCause();
        } catch (Exception e) {
            log.debug("Error occurred when message handler ({}) processes with key: {}", messageHandler.getClass().getSimpleName(), key, e);
            throw e;
        }
    }

    /****************************************
     * MESSAGE HANDLER FINDERS
     ***************************************/

    private <M extends Message> void findHandlerByMessageAndPublish(M message) {
        messageHandlerRegistry.find(message.getClass().getSimpleName()).forEach(messageHandler -> publishWithMessageHandler(message, messageHandler));
        voidMessageHandlerRegistry.find(message.getClass().getSimpleName()).forEach(messageHandler -> publishWithVoidMessageHandler(message, messageHandler));
    }

    private void findHandlerByKeyAndPublish(String key) {
        noMessageHandlerRegistry.find(key).forEach(messageHandler -> publishWithNoMessageHandler(key, messageHandler));
        voidNoMessageHandlerRegistry.find(key).forEach(messageHandler -> publishWithVoidNoMessageHandler(key, messageHandler));
    }

    private <M extends Message> AggregateRoot findHandlerByMessageAndPublishAndGet(M message) {
        voidMessageHandlerRegistry.find(message.getClass().getSimpleName())
                .forEach(messageHandler -> publishWithVoidMessageHandler(message, messageHandler));

        var messageHandler = messageHandlerRegistry.find(message.getClass().getSimpleName())
                .stream()
                .findFirst()
                .orElseThrow(() -> new UnableToFindMessageHandlerException("io.craftgate.messaging.handlerNotFound", message.getClass().getSimpleName()));

        return publishAndGetWithMessageHandler(message, messageHandler);
    }

    private AggregateRoot findHandlerByKeyAndPublishAndGet(String key) {
        voidNoMessageHandlerRegistry.find(key)
                .forEach(messageHandler -> publishWithVoidNoMessageHandler(key, messageHandler));

        var messageHandler = noMessageHandlerRegistry.find(key)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UnableToFindMessageHandlerException("io.craftgate.messaging.handlerNotFound", key));

        return publishAndGetWithNoMessageHandler(key, messageHandler);
    }

    /****************************************
     * STATIC METHODS
     ***************************************/

    public static <M extends Message> void publish(M message) {
        log.debug("Publish is triggered for message: {}", message);
        MessagePublisherContext.instance.findHandlerByMessageAndPublish(message);
        if (Objects.nonNull(messageTestCollector)) MessageTestCollector.put(message);
    }

    public static void publish(String key) {
        log.debug("Publish is triggered for key: {}", key);
        MessagePublisherContext.instance.findHandlerByKeyAndPublish(key);
        if (Objects.nonNull(messageTestCollector)) MessageTestCollector.put(key);
    }

    @SuppressWarnings("unchecked")
    public static <M extends Message, T extends AggregateRoot> T publishAndGet(M message) {
        log.debug("PublishAndGet is triggered for message: {}", message);
        var result = (T) MessagePublisherContext.instance.findHandlerByMessageAndPublishAndGet(message);
        if (Objects.nonNull(messageTestCollector)) MessageTestCollector.put(message);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends AggregateRoot> T publishAndGet(String key) {
        log.debug("PublishAndGet is triggered for key: {}", key);
        var result = (T) MessagePublisherContext.instance.findHandlerByKeyAndPublishAndGet(key);
        if (Objects.nonNull(messageTestCollector)) MessageTestCollector.put(key);
        return result;
    }

    /****************************************
     * CONTEXT FOR HOLDING STATIC INSTANCE
     ***************************************/

    static class MessagePublisherContext {

        private static MessagePublisher instance;

        private MessagePublisherContext() {
        }
    }
}
