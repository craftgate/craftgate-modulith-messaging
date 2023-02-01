package io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.shared;

import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.createuser.CreateUserUseCase;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.createuser.UserCreatedDomainEvent;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.notifyuser.UserNotifiedDomainEvent;
import io.craftgate.modulith.messaging.api.model.AggregateRoot;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class User extends AggregateRoot {

    private String username;
    private String name;
    private String surname;

    public static User of(String username, String name, String surname) {
        return User.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .build();
    }

    public static User create(CreateUserUseCase message) {
        User user = User.builder()
                .username(message.getUsername())
                .name(message.getName())
                .surname(message.getSurname())
                .build();
        user.registerMessage(UserCreatedDomainEvent.of(user));
        return user;
    }

    public void doNotify() {
        this.registerMessage(UserNotifiedDomainEvent.of(this));
    }

    @Override
    public String key() {
        return username;
    }
}
