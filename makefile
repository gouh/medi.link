# Comando para ejecutar la migración
db-migrate:
	mvnw flyway:migrate -Dflyway.configFiles=flyway.conf

# Comando para limpiar la base de datos (¡Cuidado con usarlo en producción!)
db-clean:
	mvnw flyway:clean -Dflyway.configFiles=flyway.conf

# Comando para reparar metadatos de Flyway en caso de errores en migraciones
db-repair:
	mvnw flyway:repair -Dflyway.configFiles=flyway.conf

# Comando para validar las migraciones
db-validate:
	mvnw flyway:validate -Dflyway.configFiles=flyway.conf

# Comando para obtener información sobre el estado de las migraciones
db-info:
	mvnw flyway:info -Dflyway.configFiles=flyway.conf

# make db-create-migration NAME="create_schema"
db-create-migration:
ifeq ($(OS),Windows_NT)
	@powershell New-Item -ItemType File -Name "src/main/resources/db/migrations/V1__$(NAME)_$$(Get-Date -Format "yyyyMMddHHmmss").sql"
else
    @touch ./src/main/resources/db/migrations/V1__$(NAME)_$(shell date +"%Y%m%d%H%M%S").sql
endif
