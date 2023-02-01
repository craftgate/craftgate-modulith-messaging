package io.craftgate.modulith.messaging.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MessageHandlerConfig {

    Class<?> selector() default void.class;

    String[] key() default "";

    boolean isTransactional() default false;

    boolean isChained() default false;
}