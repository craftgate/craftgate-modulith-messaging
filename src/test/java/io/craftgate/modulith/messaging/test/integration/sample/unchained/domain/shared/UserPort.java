package io.craftgate.modulith.messaging.test.integration.sample.unchained.domain.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
