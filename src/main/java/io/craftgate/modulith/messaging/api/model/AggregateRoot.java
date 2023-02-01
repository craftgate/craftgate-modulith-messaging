package io.craftgate.modulith.messaging.api.model;

import io.craftgate.modulith.messaging.api.MessagePublisher;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Stream;

@Slf4j
@Getter
@ToString(callSuper = true)
public abstract class AggregateRoot {

    private Queue<Message> messages = new LinkedList<>();
    private Queue<Message> consumedMessages = new LinkedList<>();

    public void registerMessage(Message message) {
        this.messages.offer(message);
    }

    public abstract String key();

    public void publishMessages() {
        Stream.generate(() -> messages.poll())
                .takeWhile(Objects::nonNull) //Note that this is only available in java 9
                .forEach(this::publishMessage);
    }

    private void publishMessage(Message message) {
        log.debug("Message publishing started for message: {} ", message);
        MessagePublisher.publish(message);
        consumedMessages.offer(message);
        log.debug("Message publishing completed for message: {} ", message);
    }
}
