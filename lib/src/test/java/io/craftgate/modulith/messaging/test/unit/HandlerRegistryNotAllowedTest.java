package io.craftgate.modulith.messaging.test.unit;

import io.craftgate.modulith.messaging.api.MessagePublisher;
import io.craftgate.modulith.messaging.api.exception.HandlerRegistryNotAllowedException;
import io.craftgate.modulith.messaging.api.registry.MessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.NoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidMessageHandlerRegistry;
import io.craftgate.modulith.messaging.api.registry.VoidNoMessageHandlerRegistry;
import io.craftgate.modulith.messaging.test.unit.sample.registry.auditactivities.AuditUserBlockedHandler;
import io.craftgate.modulith.messaging.test.unit.sample.registry.createuser.CreateUserHandler;
import io.craftgate.modulith.messaging.test.unit.sample.registry.createuser.FakeUserDataAdapter;
import io.craftgate.modulith.messaging.test.unit.sample.registry.notifyuser.NotifyUserHandler;
import io.craftgate.modulith.messaging.test.unit.sample.registry.outbox.OutboxUserNotifiedHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HandlerRegistryNotAllowedTest extends AbstractUnitTest {

    @Test
    void should_throw_exception_if_multiple_message_handler_registered_with_same_selector() {
        FakeUserDataAdapter fakeUserPort = new FakeUserDataAdapter();
        assertThrows(HandlerRegistryNotAllowedException.class, () -> new MessagePublisher(
                new MessageHandlerRegistry(new CreateUserHandler(fakeUserPort), new NotifyUserHandler(fakeUserPort)),
                new NoMessageHandlerRegistry(new OutboxUserNotifiedHandler()),
                new VoidMessageHandlerRegistry(),
                new VoidNoMessageHandlerRegistry()
        ));
    }

    @Test
    void should_throw_exception_if_multiple_void_message_handler_registered_with_same_selector() {
        FakeUserDataAdapter fakeUserPort = new FakeUserDataAdapter();
        assertThrows(HandlerRegistryNotAllowedException.class, () -> new MessagePublisher(
                new MessageHandlerRegistry(new CreateUserHandler(fakeUserPort)),
                new NoMessageHandlerRegistry(new OutboxUserNotifiedHandler(), new AuditUserBlockedHandler()),
                new VoidMessageHandlerRegistry(),
                new VoidNoMessageHandlerRegistry()
        ));
    }
}
