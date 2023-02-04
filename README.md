# Craftgate Modulith Messaging

This repository is a Java library for managing communication between internal modules.

## The Problem: High Coupling

High coupling refers to a situation where different components or modules of a software system are tightly
interconnected and depend on each other, making them difficult to change or reuse independently. This can cause several
problems in software development and maintenance, including:

* Increased complexity: The more interdependent the components are, the harder it becomes to understand the system as a
  whole and make changes to it.

* Decreased maintainability: High coupling makes it more challenging to modify or fix one component without affecting
  other parts of the system, making maintenance more difficult and time-consuming.

* Decreased testability: Testing becomes more complicated when components are tightly connected, as changing one
  component can cause unexpected side effects in other parts of the system.

* Decreased reusability: High coupling makes it harder to reuse components or modules in different parts of the system
  or in other systems, reducing their reusability.

Therefore, high coupling is considered a nightmare in software development because it can lead to decreased system
quality and increased development costs.

## The Solution: Decoupling with Effective Messaging

Alan Kay, computer scientist and pioneer of object-oriented programming, stated "The big idea is messaging in objects"
because he believed that the key to creating powerful and flexible computer systems was to design them based on the
principles of communication and collaboration between independent, self-contained units called objects. Objects
communicate with each other by sending and receiving messages, which triggers actions and produces results. This
approach to programming allows for a more natural and intuitive way to model and solve problems, making it easier for
programmers to create complex systems and enables them to build on the work of others.

The concept of objects and aggregate roots in DDD reflect the principles of object-oriented programming and demonstrate
the importance of encapsulation, reusability, abstraction, communication, and responsibility in software design.

Both share many similarities.

* Encapsulation: Both objects and aggregate roots are self-contained units that encapsulate their state and behavior,
  which promotes loose coupling and separation of concerns.
* Reusability: By design, objects and aggregate roots can
  be reused in different contexts, which increases the efficiency of software development and reduces duplication of
  code.
* Abstraction: Both objects and aggregate roots provide a level of abstraction over the underlying
  implementation, making it easier for developers to reason about complex systems and hide implementation details.
* Communication: Objects communicate with each other through messaging, while aggregate roots interact through their
  associated entities and value objects. The communication between these units is an essential aspect of both OOP and
  DDD, as it enables them to collaborate and achieve a common goal.
* Responsibilities: Objects and aggregate roots are
  designed with specific responsibilities in mind, which are encapsulated within their boundaries. This design
  principle helps to maintain the structure and maintainability of the system.

Effective communication is critical and forms a basis for applying Single Responsibility Rule and creating low coupled
systems. `Craftgate-Modulith-Messaging` is the messaging api for creating independent and decoupled modular
architecture.

## Core Concepts

### Module

A module is a logical grouping of related elements in the domain model, such as domain objects, services, and
components. A module provides a way to organize the elements in the domain model into meaningful and cohesive units.
Modules can be used to encapsulate the behavior and data of a specific part of the domain, to define the boundaries of
the model, and to encapsulate the relationships between different parts of the model.

Modules are designed to be reusable, maintainable, and scalable, and they can help to simplify the complexity of the
domain model. By organizing the elements in the domain model into modules, the model becomes easier to understand and
manage, and it becomes easier to make changes to the model without affecting other parts of the system. Modules can also
help to improve the overall performance and scalability of the system, as they provide a way to load only the parts of
the model that are needed for a specific operation.

### Message

A message is a unit of data that is sent from one system to another. In this context, it is a base class providing
default fields of each use case or domain event, such as producer, key or createdAt information.

### Aggregate Root

It is a cluster of related objects that can be treated as a single unit in the context of data changes. The aggregate
root is responsible for enforcing the invariants for the objects within its boundaries, and it acts as the entry point
for any operations on the objects within the aggregate. It acts as a single source of truth and protects the internal
state of the objects within the aggregate from external modifications that could violate the invariants.

### Message Handler

It is software component that is responsible for receiving and processing messages and for processing events that are
raised by other components in the system. The message handler acts as a subscriber to events and performs some action in
response to the events it receives.

### Registry

A registry for message handlers is a component that maintains a list of message handlers and their associated message
types. The registry is used to manage the mapping between messages and the message handlers that are responsible for
processing them. The registry provides a way to dynamically add, remove, or update the message handlers that are
associated with specific messages, making it possible to change the behavior of the system at runtime. The registry for
message handlers is often referred to as an message router, message bus, or message dispatcher. The message router also
acts as a mediator between the components, decoupling them and reducing the coupling in the system.

### Message Publisher

It is a component that is responsible for sending messages to one or more message subscribers (message handlers
registered to the system). It finds the correct Message Handler for a given message and let the message be processed at
the detected Message Handler.

# Getting Started

To use the library, simply add the dependency to your project and use them in your code.

## Features

* **Use case driven development**: This feature supports development that is centered around specific use cases, making it
  easier to create solutions that are tailored to the needs of the user.
* **DDD friendly decoupled modules**: The library is designed to be friendly to Domain-Driven Design, which emphasizes the
  separation of concerns and encapsulation of domain-specific knowledge. The decoupled modules allow for a flexible and
  modular design that can be adapted to changing requirements.
* **Transactional or non-transactional flows**: The library supports both transactional and non-transactional flows,
  providing developers with the ability to choose the appropriate approach for their use case. Transactional flows
  ensure that changes to the system are atomic, consistent, isolated, and durable, while non-transactional flows provide
  greater flexibility and performance.
* **Single-threaded or multi-thread processing of linked modules**: The library provides support for both single-threaded
  and multi-thread processing, allowing developers to choose the most appropriate approach for their use case.
  Single-threaded processing is useful for preserving the order of operations, while multi-threaded processing provides
  improved performance.
* **Parallel execution of modules processing the same use case or message**: This feature allows multiple modules to process
  the same use case or message in parallel, improving performance and scalability.

## How To Use In Your Project

### 1. Create a Message Handler

There are 4 types of message handlers you can extend for your own needs:

* **``MessageHandler``**: It expects to get an input parameter in type of `Message` and it returns a value in type
  of `AggregateRoot`.
* **``NoMessageHandler``**: It does not expect any input and returns a value in type of `AggregateRoot`.
* **``VoidMessageHandler``**: It expects to get an input parameter in type of `Message` and it does not return a value.
* **``VoidNoMessageHandler``**: It does not expect any input, and it does not return a value.

Your message handlers must extend from one of these 4 message handlers.

```java

@Slf4j
@DomainComponent
@RequiredArgsConstructor
@MessageHandlerConfig(selector = CreateUserUseCase.class, isChained = false, isTransactional = true)
public class CreateUserHandler extends MessageHandler<CreateUserUseCase, User> {

    @Override
    public User handle(CreateUserUseCase message) {
        // do some stuff here
    }
}
```

Message handlers must be configured by `MessageHandlerConfig` annotation. This annotation helps message handlers to
register themselves with a class or a string. In the example above, the message handler is registered with a key
`CreateUserUseCase.class`. Since `NoMessageHandler` and `VoidNoMessageHandler` do not expect any input parameter, you
should use `key` field of the annotation in String type to set an identifier for the given message handler.

`isChained=true` boolean field is used to link the current module to the current thread of the flow. `isChained=false`
boolean field is used for running the current module in a separate thread.

`isTransactional=true` boolean field makes the whole handling transactional being managed by Spring Framework's
`Spring-Tx` module. When you make your module transactional, it participates the current transaction if exists, or
creates a new transaction. It means, in case of a failure, rollback flow will be triggered. For now, Spring is the only
external library used for Transaction Management.

### 2. Register Out-Going Domain Messages at Your Aggregate Root

When you extend your aggregate root from `AggregateRoot`, you will be able to register your hand made domain
events/messages and let the message publisher mechanism publish them after current message handling is completed.

Here is a sample aggregate root showing how we can register `UserBlockedDomainEvent`.

```java

@Slf4j
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class User extends AggregateRoot {

    private String username;
    private String name;
    private String surname;

    private boolean isBlocked;
    private String blockReason;
    private LocalDateTime blockExpiryDate;

    public void block(String blockReason, LocalDateTime blockExpiryDate) {
        this.isBlocked = true;
        this.blockReason = blockReason;
        this.blockExpiryDate = blockExpiryDate;
        this.registerMessage(UserBlockedDomainEvent.of(this));
    }
}
```

### 3. Publish Your Message and Start The Flow

Once you create and configure your message handlers correctly, it is easy to publish any message with
the `MessagePublisher`.

In the example below, you can see how we can publish a usecase from a Spring controller.

```java

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request) {
        User user = MessagePublisher.publishAndGet(request.toMessage());
        return ResponseEntity.ok(UserDto.from(user));
    }
}
```

Or, you can publish any String with the same mechanism as below.

```java

@Slf4j
@DomainComponent
@MessageHandlerConfig(selector = UserNotifiedDomainEvent.class, isChained = true, isTransactional = true)
public class OutboxUserCreatedHandler extends VoidMessageHandler<UserNotifiedDomainEvent> {

    @Override
    public void handle(UserNotifiedDomainEvent useCase) {
        log.info("Outbox save is happened");
        publish("OUTBOX_COMPLETED");
    }
}
```

`MessagePublisher` provides 4 different static methods to publish messages.

* `<M extends Message> void publish(M message)`
* `void publish(String key)`
* `<M extends Message, T extends AggregateRoot> T publishAndGet(M message)`
* ` <T extends AggregateRoot> T publishAndGet(String key)`

You can choose one of them by checking whether you need the return value or have an existing input parameter.

### 4. Configure DI Framework to Do The Injection

The library contains `DomainComponent` annotation in order to mark services and components for dependency injection. For
instance, the following component let Spring configure component scan and autowire components defined in the library.

```java

@Configuration
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = {DomainComponent.class}
                )
        }
)
public class ComponentScanConfiguration {
}
```

## Examples

We prepared a comprehensive test suite with lots of examples for different scenarios. You can check [unit
tests](https://github.com/craftgate/craftgate-modulith-messaging/tree/main/lib/src/test/java/io/craftgate/modulith/messaging/test/unit)
and [integration
tests](https://github.com/craftgate/craftgate-modulith-messaging/tree/main/lib/src/test/java/io/craftgate/modulith/messaging/test/integration)
to understand how the modulith-messaging works.

## How To Build The Project

In order to run the tests and create jar files, please run the following command. Jar and source jar will be generated
under `lib/build/libs` folder.

```bash
./gradle build
```

If the commit you build has a version tag, i.e. a tag with a name like v0.1 or v1.1, the
jar files will have the version number in the name. Otherwise, the jar files will have `SNAPSHOT.${commit id}` value in
the name.

## Conclusion

This messaging library provides a simple and flexible solution for managing communication between internal modules. Try
it out and see how it can help improve your code. If you have any questions or suggestions, please feel free to reach
out!

## Prerequisites

Used technologies in the codebase:

* Java 17+
* Gradle 7+
* Spring Tx 6+
* Lombok 1.18+

## Contributing

If you wish to contribute to this repository, please fork the repository, make your changes and create a pull request.

## Code of Conduct

Please take a look at [code of conduct](CODE_OF_CONDUCT.md) before opening issues or creating pull requests.

## License

This project is maintained by the crafters of [Craftgate](https://craftgate.io) and licensed under
the [MIT license](/LICENSE).
