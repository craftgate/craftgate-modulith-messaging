package io.craftgate.modulith.messaging.test.unit.sample.unchained.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
