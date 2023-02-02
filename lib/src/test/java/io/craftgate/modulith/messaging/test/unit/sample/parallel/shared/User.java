package io.craftgate.modulith.messaging.test.unit.sample.parallel.shared;

import io.craftgate.modulith.messaging.api.model.AggregateRoot;
import io.craftgate.modulith.messaging.test.unit.sample.parallel.blockuser.UserBlockedDomainEvent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class User extends AggregateRoot {

    private String username;
    private String name;
    private String surname;

    private boolean isBlocked;
    private String blockReason;
    private LocalDateTime blockExpiryDate;

    public static User of(String username, String name, String surname) {
        return User.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .build();
    }

    public void block(String blockReason, LocalDateTime blockExpiryDate) {
        this.isBlocked = true;
        this.blockReason = blockReason;
        this.blockExpiryDate = blockExpiryDate;
        this.registerMessage(UserBlockedDomainEvent.of(this));
    }

    @Override
    public String key() {
        return username;
    }
}
