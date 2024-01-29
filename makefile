# Detect OS
OS := $(if $(ComSpec),Windows,Unix)

db-migrate: ## Execute database migrations using Flyway
	mvnw flyway:migrate

db-clean: ## Clean the database schema using Flyway
	mvnw flyway:clean

db-repair: ## Repair the Flyway schema history table
	mvnw flyway:repair

db-validate: ## Validate the applied database migrations against available ones
	mvnw flyway:validate

db-info: ## Show information about the applied and pending migrations
	mvnw flyway:info

db-create-migration: ## Create a new database migration script
ifeq ($(OS),Windows)
	@powershell New-Item -ItemType File -Name "src\main\resources\db\migrations\V$$(Get-Date -Format "yyyyMMddHHmmss")__$(NAME).sql"
else
	@touch ./src/main/resources/db/migrations/V$(shell date +"%Y%m%d%H%M%S")__$(NAME).sql
endif

code-format: ## Format the codebase using Spotless
	mvnw spotless:apply

run: ## Run the Spring Boot application
	mvnw spring-boot:run