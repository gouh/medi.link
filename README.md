## Description

This project utilizes Spring Boot 3.2.1 with Java 17 and is configured to work with a MariaDB database using Flyway for database migration management. The application connects to the database using the `org.mariadb.jdbc:mariadb-java-client:3.3.2` driver. Database migrations are managed through `org.flywaydb:flyway-maven-plugin:10.5.0` and `org.flywaydb:flyway-mysql:10.5.0`.

## Flyway Configuration

Flyway configuration is located in the `./flyway.conf` file with the following details:

```
flyway.url=jdbc:mariadb://localhost:3306/medilink
flyway.driver=org.mariadb.jdbc.Driver
flyway.user=root
flyway.password=root
flyway.locations=filesystem:src/main/resources/db/migrations
flyway.cleanDisabled=false
```

## Makefile Commands

The project includes a `Makefile` for easy management of database migrations:

- **db-migrate**: Executes the database migrations.
- **db-clean**: Cleans the database (warning: do not use in production).
- **db-repair**: Repairs Flyway metadata in case of migration errors.
- **db-validate**: Validates the database migrations.
- **db-info**: Displays information about the migration status.
- **db-create-migration**: Creates a new migration file with a given name and timestamp. Usage: `make db-create-migration NAME="migration_name"`.

## Spring Boot Configuration

The `application.properties` file is configured as follows:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/medilink
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# Show SQL queries in the log
spring.jpa.show-sql=true

# Hibernate dialect for MariaDB
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDB103Dialect
```

## Requirements

- Java 17
- Maven
- MariaDB
- Flyway

## Usage Instructions

1. Clone the repository and navigate to the project directory.
2. To execute any database migration command, use the `make` commands provided in the `Makefile`.

   For example, to run the migrations, use:
   ```sh
   make db-migrate
   ```

   To create a new migration with a specific name:
   ```sh
   make db-create-migration NAME="migration_name"
   ```

3. Ensure that your MariaDB service is active and accessible with the credentials provided in `application.properties` and `flyway.conf`.

4. To start the Spring Boot application, run:
   ```sh
   ./mvnw spring-boot:run
   ```
   or if you are using a Windows-based system:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

## Additional Notes

- The `db-clean` command in the `Makefile` should be used with caution, especially in production environments, as it will delete all data in the database.
- If you make changes to the JPA entities, ensure to create new migrations to reflect these changes in the database.
- The `spring.jpa.show-sql=true` property in `application.properties` is useful during development to view the generated SQL queries in the logs.

## Contributing

To contribute to this project, please create a new branch for your changes and, once completed, submit a Pull Request for review and merging into the main branch.
