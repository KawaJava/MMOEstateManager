# MMOEstateManager

📌 **English version is available below the Polish version. Scroll down to read it. You can also use the table of contents below to quickly jump to it.**

### 📄 **Spis treści (Polska wersja)**

- [Opis](#opis)  
- [Technologie](#technologie)  
- [Wymagania](#wymagania)  
- [Instalacja](#instalacja)  
  - [1. Klonowanie repozytorium](#1-klonowanie-repozytorium)  
  - [2. Konfiguracja bazy danych](#2-konfiguracja-bazy-danych)  
  - [3. Uzupełnianie pliku application.properties](#3-uzupełnij-plik-applicationproperties)  
- [Uruchamianie aplikacji](#uruchamianie-aplikacji)  
- [Uruchamianie testów](#uruchamianie-testów)  
- [Uwagi końcowe](#uwagi-końcowe)

---

### 📄 **Table of Contents (English version)**

- [Description](#description)  
- [Technologies](#technologies)  
- [Requirements](#requirements)  
- [Installation](#installation)  
  - [1. Clone the repository](#1-clone-the-repository)  
  - [2. Database Configuration](#2-database-configuration)  
  - [3. Fill in the application.properties file](#3-fill-in-the-applicationproperties-file)  
- [Running the Application](#running-the-application)  
- [Running Tests](#running-tests)  
- [Final Notes](#final-notes)



## Opis
Aplikacja, której celem jest pomoc w zarządzaniu graczom MMO swoimi włościami i kontrolowanie stanu złota. Aplikacja pozwala na szybszy i łatwiejszy dostęp do danych o złocie i szeryfach/wójtach w danej włości oraz przegląd danych historycznych, które dają możliwość śledzenia zmian i wykrywania, gdzie np. ilość złota była zbyt duża względem ustawionego limitu. Gracze, którzy nie uzupełniają danych o złocie w gminach mogą dostać automatycznie emaila z prośbą o uaktualnienie danych bez konieczności ręcznego wysyłania takiego powiadomienia.

W zależności od posiadanych uprawnień - Admin lub User można np. aktualizować wójtów w gminach lub edytować/przeglądać całość danych. Aplikacja jest zabezpieczona nowoczesnym JWT Tokenem, a dane muszą przejść walidację nim zostaną zapisane do bazy danych (Spring Validation).

Program zawiera kod bardzo zbliżony do tego, którego używałbym w komercyjnym projekcie (dobre pokrycie testami jednostkowymi, zgodność z zasadami SOLID, DRY, KISS, wykorzystanie nowości z Javy 8+ oraz częste refactoringi). Program zawiera zarówno proste operacje CRUD, jak i bardziej skomplikowane metody do obliczeń BigDecimalowych oraz analizy danych z tabel historycznych. Obecnie pracuję nad dodaniem testów integracyjnych oraz frontendem w Angularze.

---

## Technologie
- **Java:** 21
- **Spring Boot 3:** Web, Data-JPA, Liquibase, AOP, Mail Sender, Scheduler, Validation, Security – JWT Token
- **Baza danych:** MySQL
- **REST API**
- **Testy:** JUnit, Mockito, AssertJ, Postman
- **Wzorce projektowe:** adapter, strategia, builder itp.
- **Pozostałe:** Lombok, Maven, architektura warstwowa, Git

---

## Wymagania
Aby uruchomić projekt lokalnie, musisz mieć zainstalowane:
- Java JDK 21
- Apache Maven
- MySQL (z użytkownikiem i bazą danych)
- Git
- IntelliJ IDEA / Eclipse (lub inne IDE)

---

## Instalacja

### 1. Klonowanie repozytorium
```bash
git clone https://github.com/KawaJava/MMOEstateManager.git
cd MMOEstateManager
```

### 2. Konfiguracja bazy danych
Utwórz nową bazę danych w MySQL, np. mmoestate

### 3. Uzupełnij plik application.properties
Plik znajduje się w: src/main/resources/application.properties
#### Konfiguracja pliku `application.properties`

Uzupełnij zgodnie z poniższym wzorem:

```properties
#### === UZUPEŁNIJ PONIŻSZE ===
spring.datasource.url=jdbc:mysql://localhost:3306/tu_nazwa_twojej_bazy?useSSL=false&serverTimezone=UTC
spring.datasource.username=TU_TWÓJ_LOGIN_MYSQL
spring.datasource.password=TU_TWOJE_HASŁO_MYSQL

# === NIE ZMIENIAJ JEŚLI NIE WIESZ CO ROBISZ ===
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=25

# Konfiguracja ustawień aplikacji np. automatycznego wysyłania emaili
gold.limit.default=50000.0
time.to.add.updated.gold=30
cron.borough.time.email.expression=0 */10 * * * *
app.email.sender=fakeEmailService

email.for.sending.gold.asking=

# UZUPEŁNIJ PONIŻSZE
spring.mail.username=
spring.mail.password=

# JWT konfiguracja (możesz ustawić swój własny secret)
spring.security.jwt.secret=ZMIEN_TO_NA_SWÓJ_SECRET
spring.security.jwt.expiration=3600
```

## Uruchamianie aplikacji

### Z użyciem Mavena
```bash
mvn clean install
mvn spring-boot:run
```
### Lub uruchomienie z pliku JAR
```bash
mvn package
java -jar target/MMOEstateManager.jar
```

## Uruchamianie testów
W celu uruchomienia testów jednostkowych użyj polecenia:
```bash
mvn test
```
Testy sprawdzają zarówno warstwę logiki biznesowej, jak i poprawność działania komponentów aplikacji.
## Uwagi końcowe
Cały projekt został napisany przy użyciu dobrych praktyk programistycznych (SOLID, DRY, KISS) oraz architektury warstwowej.

Schemat bazy danych oraz migracje kontroluje Liquibase – zmiany w bazie należy wprowadzać wyłącznie poprzez modyfikację plików changelog w repozytorium.

Niebawem na moim koncie ukaże się frontend do obsługi tej aplikacji w Angularze 13. Do tego czasu aplikację można obsługiwać za pomocą Postmana.

Aplikacja implementuje dostęp oparty o role, dzięki czemu każdy użytkownik – zarówno zwykły gracz, jak i administrator – ma przypisane funkcje zgodne z wymogami biznesowymi.

W razie pytań lub problemów zachęcam do otworzenia zgłoszenia (issue) w repozytorium.


## 🇬🇧 English version

## Description
An application designed to help MMO players manage their estates and monitor their gold balance. The app enables faster and easier access to data about gold and sheriffs/mayors in a given estate, as well as an overview of historical data, which allows tracking of changes and identifying cases where, for example, the amount of gold exceeded the set limit. Players who fail to update gold data in their regions can automatically receive an email reminder without the need for manually sending such a notification.

Depending on the assigned role – Admin or User – one can, for example, update mayors in municipalities or edit/view all data. The application is secured using a modern JWT Token, and all data must pass validation before being saved to the database (Spring Validation).

The program contains code very similar to what I would use in a commercial project (good unit test coverage, adherence to SOLID, DRY, KISS principles, use of Java 8+ features, and frequent refactoring). The application includes both simple CRUD operations and more complex methods for BigDecimal calculations and analysis of data from historical tables. I'm currently working on adding integration tests and developing the frontend in Angular.

---

## Technologies
- **Java:** 21  
- **Spring Boot 3:** Web, Data-JPA, Liquibase, AOP, Mail Sender, Scheduler, Validation, Security – JWT Token  
- **Database:** MySQL  
- **REST API**  
- **Testing:** JUnit, Mockito, AssertJ, Postman  
- **Design Patterns:** adapter, strategy, builder, etc.  
- **Others:** Lombok, Maven, layered architecture, Git  

---

## Requirements
To run the project locally, you need to have installed:
- Java JDK 21  
- Apache Maven  
- MySQL (with a user and a database)  
- Git  
- IntelliJ IDEA / Eclipse (or any other IDE)  

---

## Installation

### 1. Clone the repository
```bash
git clone https://github.com/KawaJava/MMOEstateManager.git
cd MMOEstateManager
```

### 2. Database Configuration
Create a new database in MySQL, e.g., `mmoestate`.

### 3. Fill in the `application.properties` file
The file is located at: `src/main/resources/application.properties`

#### Configuration of the `application.properties` file

Fill it out according to the following template:

```properties
#### === FILL IN THE FOLLOWING ===
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# === DO NOT CHANGE IF YOU DON'T KNOW WHAT YOU'RE DOING ===
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=25

# Application-specific settings, e.g., automatic email reminders
gold.limit.default=50000.0
time.to.add.updated.gold=30
cron.borough.time.email.expression=0 */10 * * * *
app.email.sender=fakeEmailService

email.for.sending.gold.asking=

# FILL IN THE FOLLOWING
spring.mail.username=
spring.mail.password=

# JWT configuration (you can set your own secret)
spring.security.jwt.secret=CHANGE_THIS_TO_YOUR_SECRET
spring.security.jwt.expiration=3600
```
## Running the Application

### Using Maven
```bash
mvn clean install
mvn spring-boot:run
```

### Or run from the JAR file
```bash
mvn package
java -jar target/MMOEstateManager.jar
```

## Running Tests
To execute unit tests, use the following command:
```bash
mvn test
```
The tests cover both the business logic layer and ensure the correct behavior of application components.

## Final Notes
The entire project was developed using best programming practices (SOLID, DRY, KISS) and a layered architecture.

The database schema and migrations are managed using Liquibase – all changes to the database should be made exclusively by modifying the changelog files in the repository.

A frontend for this application built with Angular 13 will be published on my profile soon. Until then, the application can be operated using Postman.

The application implements role-based access control, ensuring that each user – whether a regular player or an administrator – has permissions aligned with business requirements.

If you have any questions or encounter any issues, feel free to open an issue in the repository.
