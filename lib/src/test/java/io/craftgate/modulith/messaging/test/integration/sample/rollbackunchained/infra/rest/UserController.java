package io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.infra.rest;

import io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.shared.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.craftgate.modulith.messaging.api.MessagePublisher.publishAndGet;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request) {
        User user = publishAndGet(request.toMessage());
        return ResponseEntity.ok(UserDto.from(user));
    }
}
