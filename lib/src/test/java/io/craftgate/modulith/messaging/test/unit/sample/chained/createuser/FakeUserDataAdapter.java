package io.craftgate.modulith.messaging.test.unit.sample.chained.createuser;

import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.chained.shared.UserPort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FakeUserDataAdapter implements UserPort {

    private Map<String, User> data = new HashMap<>();

    public void initialize(User... users) {
        Arrays.stream(users).forEach(user -> data.put(user.getUsername(), user));
    }

    @Override
    public void saveUser(User user) {
        data.put(user.getUsername(), user);
    }

    @Override
    public User retrieveUser(String username) {
        return data.get(username);
    }
}
