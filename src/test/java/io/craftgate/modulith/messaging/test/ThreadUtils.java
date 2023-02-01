package io.craftgate.modulith.messaging.test;

import io.craftgate.modulith.messaging.api.util.MessageTestCollector;

import java.util.Arrays;

public class ThreadUtils {

    public static void waitCompletion(WaitMatcher matcher, String... keys) throws InterruptedException {
        boolean continueWaiting = true;
        long totalWaitInMs = 0;
        long iterationInMs = 50;
        long maxTotalWainInMs = 2000;
        while (continueWaiting && !checkKeys(matcher, keys)) {
            //noinspection BusyWait
            Thread.sleep(iterationInMs);
            totalWaitInMs += iterationInMs;
            if (totalWaitInMs >= maxTotalWainInMs) continueWaiting = false;
        }
    }

    private static boolean checkKeys(WaitMatcher matcher, String... keys) {
        return matcher.equals(WaitMatcher.ALL) ?
                Arrays.stream(keys).allMatch(MessageTestCollector::contains) :
                Arrays.stream(keys).anyMatch(MessageTestCollector::contains);
    }

    public enum WaitMatcher {
        ALL,
        ANY
    }
}
