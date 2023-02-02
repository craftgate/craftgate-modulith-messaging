package io.craftgate.modulith.messaging.test.integration.sample.chained.domain.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
