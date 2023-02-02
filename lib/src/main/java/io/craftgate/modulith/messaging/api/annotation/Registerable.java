package io.craftgate.modulith.messaging.api.annotation;

import java.util.Objects;
import java.util.stream.Stream;

public interface Registerable {

    default String[] selector() {
        MessageHandlerConfig annotation = this.getClass().getAnnotation(MessageHandlerConfig.class);
        if (Objects.isNull(annotation)) throw new UnsupportedOperationException("Message selector is missing");
        return Objects.isNull(annotation.selector()) || annotation.selector().length == 0 || "void".equals(annotation.selector()[0].getSimpleName()) ?
                annotation.key() :
                Stream.of(annotation.selector()).map(Class::getSimpleName).toArray(String[]::new);
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
