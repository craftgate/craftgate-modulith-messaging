# NOTICE

**Craftgate Modulith Messaging** (`craftgate-modulith-messaging`)

Copyright (c) 2023 Craftgate

This product is developed and maintained by Craftgate (https://craftgate.io).

This software is licensed under the terms of the Apache License, Version 2.0
(the "License"); you may not use this software except in compliance with the
License. A full copy of the License is included in the [`LICENSE`](./LICENSE)
file distributed with this software.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

---

## Third-Party Software

This product depends on the third-party software components listed below. Each
component is the property of its respective owners and is governed by its own
license. Craftgate Modulith Messaging does not modify or bundle these
components; they are referenced as external dependencies and resolved via
Gradle / Maven Central.

License texts are available at the URLs provided. Inclusion of a component in
this list does not imply endorsement by its authors.

### Provided (Compile-Time) Dependencies

All non-test dependencies of this library are declared as `compileOnly`
("provided"). They are **not** bundled into the published artifact and are
**not** pulled in transitively. The consuming application is expected to supply
compatible versions of these components on its own classpath at runtime.

#### Guava

- **Coordinates:** `com.google.guava:guava:31.1-jre`
- **Copyright:** Copyright (c) The Guava Authors
- **License:** Apache License, Version 2.0
- **Homepage:** https://github.com/google/guava
- **License text:** https://www.apache.org/licenses/LICENSE-2.0

#### Spring Framework — Transaction (spring-tx)

- **Coordinates:** `org.springframework:spring-tx:6.0.3`
- **Copyright:** Copyright (c) 2002-2023 the original author or authors
  (VMware, Inc.)
- **License:** Apache License, Version 2.0
- **Homepage:** https://spring.io/projects/spring-framework
- **License text:** https://www.apache.org/licenses/LICENSE-2.0

#### Logback Classic

- **Coordinates:** `ch.qos.logback:logback-classic:1.4.5`
- **Copyright:** Copyright (c) 1999-2022 QOS.ch Sarl
- **License:** Dual-licensed under the Eclipse Public License v1.0 and the
  GNU Lesser General Public License v2.1 (recipient may choose either)
- **Homepage:** https://logback.qos.ch/
- **License text:** https://logback.qos.ch/license.html

#### SLF4J API

- **Coordinates:** `org.slf4j:slf4j-api:2.0.6`
- **Copyright:** Copyright (c) 2004-2022 QOS.ch
- **License:** MIT License
- **Homepage:** https://www.slf4j.org/
- **License text:** https://www.slf4j.org/license.html

#### SLF4J Bridges (jcl-over-slf4j, log4j-over-slf4j)

- **Coordinates:** `org.slf4j:jcl-over-slf4j:2.0.6`,
  `org.slf4j:log4j-over-slf4j:2.0.6`
- **Copyright:** Copyright (c) 2004-2022 QOS.ch
- **License:** Apache License, Version 2.0
- **Homepage:** https://www.slf4j.org/
- **License text:** https://www.apache.org/licenses/LICENSE-2.0

#### Project Lombok

- **Coordinates:** `org.projectlombok:lombok:1.18.24`
- **Copyright:** Copyright (c) The Project Lombok Authors
- **License:** MIT License
- **Usage:** Compile-time annotation processor only — not present at runtime.
- **Homepage:** https://projectlombok.org/
- **License text:** https://github.com/projectlombok/lombok/blob/master/LICENSE

### Test-Only Dependencies

These components are used by the test suite only. They are **not** required at
runtime and are **not** included in the distributed artifact.

- **AssertJ Core** `org.assertj:assertj-core:3.23.1` — Apache License 2.0
  (https://assertj.github.io/doc/)
- **JUnit Jupiter** `org.junit.jupiter:junit-jupiter:5.9.1` — Eclipse Public
  License v2.0 (https://junit.org/junit5/)
- **HikariCP** `com.zaxxer:HikariCP:5.0.1` — Apache License 2.0
  (https://github.com/brettwooldridge/HikariCP)
- **Spring Boot Starters** `org.springframework.boot:spring-boot-starter-web`,
  `spring-boot-starter-data-jpa`, `spring-boot-starter-test` `:3.0.2` —
  Apache License 2.0 (https://spring.io/projects/spring-boot)
- **MySQL Connector/J** `mysql:mysql-connector-java:8.0.32` — GNU General Public
  License v2.0 with the Universal FOSS Exception, v1.0
  (https://github.com/mysql/mysql-connector-j)

---

## Attribution

If you redistribute this software, in source or binary form, you must retain
the copyright notice and license text of Craftgate Modulith Messaging, together
with the attributions for the third-party components listed above, in
accordance with their respective license terms.

For questions regarding licensing or attribution, contact info@craftgate.io.
