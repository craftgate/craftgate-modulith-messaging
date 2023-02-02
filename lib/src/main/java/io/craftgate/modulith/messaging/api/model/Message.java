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

    @Builder.Default
    private LocalDateTime messageCreatedAt = CurrentDateTimeManager.now();

    @Builder.Default
    private String traceId = UUID.randomUUID().toString();

    @Setter
    private String producer;

    @Setter
    private String key;

    @ToString.Include(name = "type")
    public String getType() {
        MessageType messageType = this.getClass().getAnnotation(MessageType.class);
        return Objects.isNull(messageType) ? this.getClass().getSimpleName() : messageType.name();
    }
}
