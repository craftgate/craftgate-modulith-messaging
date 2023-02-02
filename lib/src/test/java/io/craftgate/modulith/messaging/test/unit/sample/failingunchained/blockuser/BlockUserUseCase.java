package io.craftgate.modulith.messaging.test.unit.sample.failingunchained.blockuser;

import io.craftgate.modulith.messaging.api.model.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = false)
public class BlockUserUseCase extends Message {

    private String username;
    private String blockReason;
    private LocalDateTime blockExpiryDate;
}
