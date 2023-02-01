package io.craftgate.modulith.messaging.test.integration.sample.chained.infra.jpa;

import io.craftgate.modulith.messaging.api.annotation.DomainComponent;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.User;
import io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared.UserPort;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class UserDataAdapter implements UserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void saveUser(User user) {
        userJpaRepository.save(UserEntity.fromModel(user));
    }

    @Override
    public User retrieveUser(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserEntity::toModel)
                .orElse(null);
    }
}
