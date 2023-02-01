package io.craftgate.modulith.messaging.test.integration.sample.rollbackchained.domain.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
