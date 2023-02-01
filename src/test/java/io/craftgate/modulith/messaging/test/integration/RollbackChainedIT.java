package io.craftgate.modulith.messaging.test.integration;

import io.craftgate.modulith.messaging.test.AbstractTest;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.RollbackChainedApplication;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.infra.jpa.UserEntity;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.infra.jpa.UserJpaRepository;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.infra.rest.CreateUserRequest;
import io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.infra.rest.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = RollbackChainedApplication.class)
public class RollbackChainedIT extends AbstractTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private final ParameterizedTypeReference<UserDto> errorResponseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_not_create_user_in_failing_transaction() {
        // given
        var request = CreateUserRequest.builder()
                .username("rcarlos")
                .name("Roberto")
                .surname("Carlos")
                .build();

        // when
        var result = testRestTemplate.exchange("/users", HttpMethod.POST, new HttpEntity<>(request), errorResponseType);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        Optional<UserEntity> user = userJpaRepository.findByUsername("rcarlos");
        assertThat(user).isNotPresent();
    }
}
