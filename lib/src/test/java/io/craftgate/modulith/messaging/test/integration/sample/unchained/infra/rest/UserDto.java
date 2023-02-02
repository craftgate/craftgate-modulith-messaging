package io.craftgate.modulith.messaging.test.integration.sample.unchained.infra.rest;

import io.craftgate.modulith.messaging.test.integration.sample.unchained.domain.shared.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {

    private String username;
    private String name;
    private String surname;

    public static UserDto from(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }
}
