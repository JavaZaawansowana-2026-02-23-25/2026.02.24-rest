# REST – migracja do bazy H2 (in-memory)

Projekt używa **Spring Boot** + **czystego Hibernate** (bez Spring Data JPA).  
Poniżej opisano wszystkie zmiany, które zostały wprowadzone, aby zamiast zewnętrznej bazy MySQL aplikacja uruchamiała własną bazę **H2 in-memory**.

---

## 1. `pom.xml` – zamiana sterownika bazy danych

**Usunięto** zależność do sterownika MySQL:
```xml
<!-- USUNIĘTO -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.6.0</version>
</dependency>
```

**Dodano** zależność do bazy H2:
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

> Wersja H2 jest zarządzana przez `spring-boot-starter-parent`, więc nie trzeba jej podawać ręcznie.  
> Celowo **pominięto** `<scope>runtime</scope>`, żeby klasa `org.h2.Driver` była widoczna także w czasie kompilacji.

---

## 2. `src/main/resources/hibernate.cfg.xml` – konfiguracja połączenia

Zmieniono właściwości połączenia z MySQL na H2:

| Właściwość | Stara wartość (MySQL) | Nowa wartość (H2) |
|---|---|---|
| `hibernate.connection.driver_class` | `com.mysql.cj.jdbc.Driver` | `org.h2.Driver` |
| `hibernate.connection.url` | `jdbc:mysql://localhost:3306/customers` | `jdbc:h2:mem:customersdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE` |
| `hibernate.connection.username` | `root` | `sa` |
| `hibernate.connection.password` | *(puste)* | *(puste)* |
| `hibernate.dialect` | `org.hibernate.dialect.MySQLDialect` | `org.hibernate.dialect.H2Dialect` |
| `hibernate.hbm2ddl.auto` | `update` | `create-drop` |

### Wyjaśnienie parametrów URL bazy H2

```
jdbc:h2:mem:customersdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
```

- `mem:customersdb` – baza działa **wyłącznie w pamięci RAM** (nie zapisuje plików na dysk)
- `DB_CLOSE_DELAY=-1` – baza **nie zamyka się** dopóki działa JVM (bez tego H2 zamknęłoby bazę po rozłączeniu ostatniego połączenia)
- `DB_CLOSE_ON_EXIT=FALSE` – baza nie jest zamykana automatycznie przy wyjściu z JVM

### Wyjaśnienie `hbm2ddl.auto=create-drop`

Hibernate **automatycznie tworzy schemat** (tabele) przy starcie aplikacji na podstawie klas `@Entity` / plików `*.hbm.xml`, a następnie **usuwa go** przy zatrzymaniu. Ponieważ baza jest in-memory, dane i tak znikają po restarcie – `create-drop` jest tu naturalnym wyborem.

---

## 3. `src/main/resources/application.properties` – wyłączenie auto-konfiguracji Spring Boot

Spring Boot automatycznie próbuje skonfigurować `DataSource` i JPA, gdy wykryje H2 na classpath. Ponieważ projekt używa **własnego `SessionFactory`** (zarządzanego ręcznie w `AppConfiguration`), ta auto-konfiguracja musiała zostać wyłączona, aby uniknąć konfliktów:

```properties
spring.autoconfigure.exclude=\
  org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
  org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
  org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
```

---

## 4. `AppConfiguration.java` – bez zmian

Konfiguracja `SessionFactory` pozostała niezmieniona – Hibernate sam odczytuje `hibernate.cfg.xml` z classpath:

```java
@Bean(destroyMethod = "close")
public SessionFactory sessionFactory() {
    return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
}
```

---

## Jak uruchomić

```bash
mvn spring-boot:run
```

Aplikacja startuje z bazą H2 in-memory. Baza jest tworzona automatycznie przy starcie i nie wymaga żadnej zewnętrznej infrastruktury.

