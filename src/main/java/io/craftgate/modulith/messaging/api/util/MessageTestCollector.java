package io.craftgate.modulith.messaging.api.util;

import io.craftgate.modulith.messaging.api.model.Message;
import io.craftgate.modulith.messaging.api.MessagePublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MessageTestCollector {

    private static MessageTestCollector instance;
    private List<Message> messagesData = new ArrayList<>();
    private List<String> keysData = new ArrayList<>();

    private MessageTestCollector() {
    }

    public static void activate() {
        instance = new MessageTestCollector();
        MessagePublisher.registerMessageCollector(instance);
    }

    public static void passivate() {
        instance = null;
        MessagePublisher.registerMessageCollector(null);
    }

    public static void put(Message message) {
        log.debug("Message collector stores the message: {}", message);
        instance.messagesData.add(message);
    }

    public static void put(String key) {
        log.debug("Message collector stores the key: {}", key);
        instance.keysData.add(key);
    }

    public static List<Message> dumpMessages() {
        List<Message> dump = new ArrayList<>(instance.messagesData);
        instance.messagesData.clear();
        return dump;
    }

    public static List<String> dumpKeys() {
        List<String> dump = new ArrayList<>(instance.keysData);
        instance.keysData.clear();
        return dump;
    }

    public static boolean contains(String key) {
        return instance.keysData.contains(key);
    }

    public static boolean contains(Message message) {
        return instance.messagesData.contains(message);
    }

    public static void reset() {
        instance.messagesData.clear();
        instance.keysData.clear();
    }
}
