package io.craftgate.modulith.messaging.test.integration;

import io.craftgate.modulith.messaging.test.unit.AbstractUnitTest;
import io.craftgate.modulith.messaging.test.integration.sample.chained.TransactionalChainedApplication;
import io.craftgate.modulith.messaging.test.integration.sample.chained.infra.jpa.UserEntity;
import io.craftgate.modulith.messaging.test.integration.sample.chained.infra.jpa.UserJpaRepository;
import io.craftgate.modulith.messaging.test.integration.sample.chained.infra.rest.CreateUserRequest;
import io.craftgate.modulith.messaging.test.integration.sample.chained.infra.rest.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TransactionalChainedApplication.class)
public class TransactionalChainedIT extends AbstractIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private final ParameterizedTypeReference<UserDto> userReference = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_create_user_in_transaction() {
        // given
        var request = CreateUserRequest.builder()
                .username("rcarlos")
                .name("Roberto")
                .surname("Carlos")
                .build();

        // when
        ResponseEntity<UserDto> userDtoResponseEntity = testRestTemplate.exchange("/users", HttpMethod.POST, new HttpEntity<>(request), userReference);

        // then
        assertThat(userDtoResponseEntity.getBody()).isNotNull()
                .extracting("username", "name", "surname")
                .containsExactly("rcarlos", "Roberto", "Carlos");

        Optional<UserEntity> user = userJpaRepository.findByUsername("rcarlos");
        assertThat(user).isPresent().get()
                .extracting("username", "name", "surname")
                .containsExactly("rcarlos", "Roberto", "Carlos");
    }
}
