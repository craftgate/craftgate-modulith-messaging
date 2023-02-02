package io.craftgate.modulith.messaging.test.unit.sample.failingunchained.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
