package io.craftgate.modulith.messaging.test.unit.sample.registry.createuser;

import io.craftgate.modulith.messaging.api.model.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CreateUserUseCase extends Message {

    private String username;
    private String name;
    private String surname;
}
