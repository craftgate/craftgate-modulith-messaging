package io.craftgate.modulith.messaging.test.unit.sample.registry.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
