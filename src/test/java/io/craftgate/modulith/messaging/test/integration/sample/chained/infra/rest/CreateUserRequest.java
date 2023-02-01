package io.craftgate.modulith.messaging.test.integration.sample.chained.infra.rest;

import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.createuser.CreateUserUseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String username;
    private String name;
    private String surname;

    public CreateUserUseCase toMessage() {
        return CreateUserUseCase.builder()
                .username(username)
                .name(name)
                .surname(surname)
                .build();
    }
}
