package io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser;

import io.craftgate.modulith.messaging.api.annotation.MessageType;
import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.shared.User;
import io.craftgate.modulith.messaging.api.util.CurrentDateTimeProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@MessageType(name = "USER_BLOCKED")
public class UserBlockedDomainEvent extends Message {

    private String username;
    private String name;
    private String surname;
    private LocalDateTime registerDate;
    private boolean isBlocked;
    private String blockReason;
    private LocalDateTime blockExpiryDate;

    public static Message of(User user) {
        return UserBlockedDomainEvent.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .registerDate(CurrentDateTimeProvider.now())
                .isBlocked(user.isBlocked())
                .blockReason(user.getBlockReason())
                .blockExpiryDate(user.getBlockExpiryDate())
                .build();
    }
}
