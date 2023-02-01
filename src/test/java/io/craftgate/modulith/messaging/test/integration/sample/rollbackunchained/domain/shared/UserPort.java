package io.craftgate.modulith.messaging.test.integration.sample.rollbackunchained.domain.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
