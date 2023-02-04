package io.craftgate.modulith.messaging.api.model;

import io.craftgate.modulith.messaging.api.util.CurrentDateTimeManager;
import io.craftgate.modulith.messaging.api.annotation.MessageType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@SuperBuilder
public abstract class Message {

    /**
     * The "messageCreatedAt" field shows the creation date of the message. It is the current date time in the system's
     * timezone.
     */
    @Builder.Default
    private LocalDateTime messageCreatedAt = CurrentDateTimeManager.now();

    /**
     * The "traceId" field is a unique value for each created message. It can be used for troubleshooting.
     */
    @Builder.Default
    private String traceId = UUID.randomUUID().toString();

    /**
     * The "producer" field shows the producer of this message. Leave it empty if it is not important. For instance,
     * if you may want to set it to something in order to generate the name of kafka topic.
     */
    @Setter
    private String producer;

    /**
     * The "key" field is a unique identifier for messages. It is mainly used for marking one or multiple messages
     * with same value. For instance, if you want to process multiple messages at the same partition of kafka (i.e. in
     * the same queue), give same key to all these messages and kafka will process them in order.
     */
    @Setter
    private String key;

    /**
     * The "type" field is the given type stated in MessageType annotation as in the example blow:
     * <pre>@MessageType(name = "USER_CREATED")</pre>
     * If annotation is missing, the class name of current message class is returned.
     *
     * @return String
     */
    @ToString.Include(name = "type")
    public String getType() {
        MessageType messageType = this.getClass().getAnnotation(MessageType.class);
        return Objects.isNull(messageType) ? this.getClass().getSimpleName() : messageType.name();
    }
}
