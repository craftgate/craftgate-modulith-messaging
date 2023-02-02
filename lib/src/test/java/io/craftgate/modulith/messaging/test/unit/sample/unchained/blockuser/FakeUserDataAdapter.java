package io.craftgate.modulith.messaging.test.unit.sample.unchained.blockuser;

import io.craftgate.modulith.messaging.test.unit.sample.unchained.shared.User;
import io.craftgate.modulith.messaging.test.unit.sample.unchained.shared.UserPort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FakeUserDataAdapter implements UserPort {

    private Map<String, User> data = new HashMap<>();

    public void initialize(User... users) {
        Arrays.stream(users).forEach(user -> data.put(user.key(), user));
    }

    @Override
    public void saveUser(User user) {
        data.put(user.key(), user);
    }

    @Override
    public User retrieveUser(String username) {
        return data.get(username);
    }
}
