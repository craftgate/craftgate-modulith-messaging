package io.craftgate.modulith.messaging.api.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CurrentDateTimeProvider {

    private static LocalDateTime customLocalDateTime;

    private CurrentDateTimeProvider() {
    }

    public static LocalDateTime now() {
        return customLocalDateTime == null ? LocalDateTime.now() : customLocalDateTime;
    }

    public static void setCustomLocalDateTime(LocalDateTime dateTime) {
        customLocalDateTime = dateTime;
    }

    public static void setCustomLocalDateTime(LocalTime time) {
        customLocalDateTime = LocalDateTime.of(LocalDate.now(), time);
    }

    public static void reset() {
        customLocalDateTime = null;
    }
}
