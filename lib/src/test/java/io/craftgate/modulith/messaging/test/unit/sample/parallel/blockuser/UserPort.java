package io.craftgate.modulith.messaging.test.unit.sample.parallel.blockuser;

import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.User;

public interface UserPort {

    void saveUser(User user);
    User retrieveUser(String username);

}
