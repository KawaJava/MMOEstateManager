# MMOEstateManager

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
## Konfiguracja pliku `application.properties`

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

# Konfiguracja ustawień apkikacji np. automatycznego wysyłania emaili
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

