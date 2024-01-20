db-migrate:
	mvnw flyway:migrate

db-clean:
	mvnw flyway:clean

db-repair:
	mvnw flyway:repair

db-validate:
	mvnw flyway:validate

db-info:
	mvnw flyway:info

db-create-migration:
	ifeq ($(OS),Windows_NT)
		@powershell New-Item -ItemType File -Name "src/main/resources/db/migrations/V1__$(NAME)_$$(Get-Date -Format "yyyyMMddHHmmss").sql"
	else
		@touch ./src/main/resources/db/migrations/V1__$(NAME)_$(shell date +"%Y%m%d%H%M%S").sql
	endif

code-format:
	mvnw spotless:apply

run:
	mvnw spring-boot:run
