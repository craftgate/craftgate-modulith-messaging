package io.craftgate.modulith.messaging.test.unit.sample.parallel.shared;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
