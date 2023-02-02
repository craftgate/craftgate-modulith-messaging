package io.craftgate.modulith.messaging.test.integration.sample.chained.infra.jpa;

import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String surname;

    public static UserEntity fromModel(User user) {
        var userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setUsername(user.getUsername());

        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .name(name)
                .surname(surname)
                .username(username)
                .build();
    }
}
