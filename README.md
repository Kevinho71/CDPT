# CADET - Sistema de Gestión

Este es el código fuente del proyecto CADET convertido desde un JAR descompilado a un proyecto Maven estándar.

## Estructura del Proyecto

```
CADET-SOURCE/
 src/
    main/
       java/
          app/
              config/
              controller/
              dto/
              entity/
              repository/
              restcontroller/
              service/
              util/
       resources/
           static/
           templates/
           application.properties
           cadet-sistema-97e2062b979e.json
    test/
        java/
 pom.xml
 README.md
```

## Requisitos Previos

- Java 11 o superior
- Maven 3.6 o superior
- PostgreSQL
- Cuenta de Google Drive API (para funcionalidad de almacenamiento)

## Configuración

1. Configurar base de datos PostgreSQL
2. Configurar variables de entorno:
   - `DATABASE_URL`: URL de conexión a PostgreSQL
   - `DATABASE_USERNAME`: Usuario de la base de datos
   - `DATABASE_PASSWORD`: Contraseña de la base de datos

3. Configurar Google Drive API:
   - Colocar el archivo de credenciales JSON en `src/main/resources/`

## Compilación y Ejecución

### Compilar el proyecto:
```bash
mvn clean install
```

### Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

### Generar JAR ejecutable:
```bash
mvn clean package
java -jar target/cadetapp-0.0.1-SNAPSHOT.jar
```

## Tecnologías Utilizadas

- Spring Boot 2.7.10
- Spring Data JPA
- Spring Security
- Thymeleaf
- PostgreSQL
- Google Drive API
- ZXing (QR Code generation)

## Notas Importantes

- Este proyecto fue reconstruido desde un JAR descompilado
- Algunos comentarios del código original pueden haberse perdido
- Revisar la declaración del package en los archivos .java (puede necesitar corrección)
- Configurar correctamente las credenciales de Google Drive antes de ejecutar
