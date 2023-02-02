package io.craftgate.modulith.messaging.api.annotation;

import java.util.Objects;

public interface Registerable {

    default String[] selector() {
        MessageHandlerConfig annotation = this.getClass().getAnnotation(MessageHandlerConfig.class);
        if (Objects.isNull(annotation)) throw new UnsupportedOperationException("Message selector is missing");
        return Objects.nonNull(annotation.selector()) && !"void".equals(annotation.selector().getSimpleName()) ? new String[]{annotation.selector().getSimpleName()} : annotation.key();
    }

    default boolean isChained() {
        MessageHandlerConfig annotation = this.getClass().getAnnotation(MessageHandlerConfig.class);
        if (Objects.isNull(annotation)) return false;
        return annotation.isChained();
    }

    default boolean isTransactional() {
        MessageHandlerConfig annotation = this.getClass().getAnnotation(MessageHandlerConfig.class);
        if (Objects.isNull(annotation)) return true;
        return annotation.isTransactional();
    }
}
