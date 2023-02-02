package io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser;

import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.User;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
