# ============================================================================
# Dockerfile Multi-Stage para Spring Boot 3.5.9 + Java 21
# Optimizado para Dokploy
# ============================================================================

# ============================================================================
# STAGE 1: Build
# ============================================================================
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copiar archivos de dependencias primero (mejor cache de capas)
COPY pom.xml .
COPY .mvn .mvn

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests -B

# ============================================================================
# STAGE 2: Runtime
# ============================================================================
FROM eclipse-temurin:21-jre-alpine

# Metadatos
LABEL maintainer="CADET System"
LABEL description="CADET Backend - Sistema de Gestión"
LABEL version="1.0"

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xms512m -Xmx1024m" \
    SERVER_PORT=8080 \
    SPRING_PROFILES_ACTIVE=prod

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Copiar el JAR compilado desde el stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Copiar archivo de credenciales de Google Cloud si existe
COPY --chown=spring:spring src/main/resources/cadet-sistema-97e2062b979e.json /app/cadet-sistema-97e2062b979e.json 2>/dev/null || true

# Cambiar al usuario no-root
USER spring:spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:${SERVER_PORT}/actuator/health || exit 1

# Exponer puerto
EXPOSE ${SERVER_PORT}

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
