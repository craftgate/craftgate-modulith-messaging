package io.craftgate.modulith.messaging.api.model;

import io.craftgate.modulith.messaging.api.util.CurrentDateTimeProvider;
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

    @Builder.Default
    private LocalDateTime messageCreatedAt = CurrentDateTimeProvider.now();

    @Builder.Default
    private String traceId = UUID.randomUUID().toString();

    @Setter
    private String producer;

    @ToString.Include(name = "type")
    public String getType() {
        MessageType messageType = this.getClass().getAnnotation(MessageType.class);
        return Objects.isNull(messageType) ? this.getClass().getSimpleName() : messageType.name();
    }
}
