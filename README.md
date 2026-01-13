# CADET - Sistema de Gestión

Sistema de gestión para el Colegio de Administradores de Empresas de Tarija (CADET).

## Tecnologías

- **Java 21**
- **Spring Boot 3.4.1**
- **PostgreSQL**
- **Thymeleaf**
- **Spring Security 6**
- **Maven**

## Estructura del Proyecto

```
CADET-SOURCE/
├── src/
│   ├── main/
│   │   ├── java/app/
│   │   │   ├── config/          # Configuración de seguridad y servicios
│   │   │   ├── controller/      # Controladores MVC
│   │   │   ├── restcontroller/  # API REST
│   │   │   ├── service/         # Lógica de negocio
│   │   │   ├── repository/      # Acceso a datos
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── dto/             # Objetos de transferencia
│   │   │   └── util/            # Utilidades
│   │   └── resources/
│   │       ├── static/          # Recursos estáticos (CSS, JS, imágenes)
│   │       ├── templates/       # Plantillas Thymeleaf
│   │       └── application.properties
│   └── test/java/
├── pom.xml
├── docker-compose.yml
└── README.md
```

## Requisitos Previos

- **Java 21 JDK** o superior
- **Maven 3.8+**
- **PostgreSQL 12+**
- Docker (opcional, para base de datos)

## Configuración

### 1. Base de Datos

Crear un archivo `.env` en la raíz del proyecto basado en `.env.template`:

```bash
cp .env.template .env
```

Editar `.env` con tus credenciales de base de datos:

```properties
DB_DEV_USER=tu_usuario
DB_DEV_PASSWORD=tu_password
DB_DEV_NAME=cadet_bd
DB_DEV_URL=jdbc:postgresql://localhost:5432/cadet_bd
```

### 2. Google Drive API (Opcional)

Si vas a usar la integración con Google Drive, coloca el archivo de credenciales JSON en:
```
src/main/resources/cadet-sistema-XXXXXXXX.json
```

## Compilación y Ejecución

### Compilar el proyecto:
```bash
mvn clean install
```

### Ejecutar en Desarrollo:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Generar JAR ejecutable:
```bash
mvn clean package
```

### Ejecutar en Producción:
```bash
java -jar target/cadetapp-0.0.1-SNAPSHOT.jar
```

## Credenciales por Defecto

Al iniciar por primera vez, se crean las siguientes credenciales:

- **Usuario:** cadet
- **Contraseña:** cadet.2024
- **Rol:** ROLE_ADMIN

**⚠️ IMPORTANTE:** Cambiar estas credenciales en producción.

## Docker

Para ejecutar con Docker Compose (PostgreSQL):

```bash
docker-compose up -d
```

## Notas de Migración

Este proyecto ha sido migrado y modernizado:
- **Spring Boot:** 2.7.10 → 3.4.1
- **Java:** 11 → 21
- **Spring Security:** 5 → 6

### Cambios Importantes

1. **Jakarta EE:** Migración automática de `javax.*` a `jakarta.*`
2. **Thymeleaf Security:** Actualizado a `thymeleaf-extras-springsecurity6`
3. **Configuración de Seguridad:** Adaptada para Spring Security 6
4. **Código limpio:** Eliminados artefactos de decompilación

## Licencia

Propietario - CADET Tarija
