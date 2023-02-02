package io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.infra.rest;

import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.shared.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
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
