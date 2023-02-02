package io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.createuser;

import io.craftgate.modulith.messaging.api.annotation.MessageType;
import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.api.util.CurrentDateTimeProvider;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.shared.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@MessageType(name = "USER_CREATED")
public class UserCreatedDomainEvent extends Message {

    private String username;
    private String name;
    private String surname;
    private LocalDateTime registerDate;

    public static Message of(User user) {
        return UserCreatedDomainEvent.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .registerDate(CurrentDateTimeProvider.now())
                .build();
    }
}