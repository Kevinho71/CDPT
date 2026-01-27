#!/bin/bash
# ============================================================================
# Script de Build y Test Local - CADET Backend
# ============================================================================
# Este script simula el build de producción localmente
# Úsalo antes de hacer deployment a Dokploy
# ============================================================================

set -e  # Exit on error

echo "============================================================================"
echo "🚀 CADET Backend - Local Production Build & Test"
echo "============================================================================"
echo ""

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# ============================================================================
# 1. Verificar Pre-requisitos
# ============================================================================
echo -e "${YELLOW}📋 Verificando pre-requisitos...${NC}"

# Check Docker
if ! command -v docker &> /dev/null; then
    echo -e "${RED}❌ Docker no está instalado${NC}"
    exit 1
fi
echo -e "${GREEN}✅ Docker instalado${NC}"

# Check Docker Compose
if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}❌ Docker Compose no está instalado${NC}"
    exit 1
fi
echo -e "${GREEN}✅ Docker Compose instalado${NC}"

# Check .env file
if [ ! -f .env ]; then
    echo -e "${RED}❌ Archivo .env no encontrado${NC}"
    echo "Copia .env.template a .env y configura las variables"
    exit 1
fi
echo -e "${GREEN}✅ Archivo .env encontrado${NC}"

echo ""

# ============================================================================
# 2. Limpiar builds anteriores
# ============================================================================
echo -e "${YELLOW}🧹 Limpiando builds anteriores...${NC}"
docker-compose -f docker-compose.prod.yml down -v 2>/dev/null || true
docker system prune -f
echo -e "${GREEN}✅ Limpieza completada${NC}"
echo ""

# ============================================================================
# 3. Build de la aplicación
# ============================================================================
echo -e "${YELLOW}🔨 Compilando aplicación (esto puede tardar 3-5 minutos)...${NC}"
docker build -t cadet-backend:test .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Build exitoso${NC}"
else
    echo -e "${RED}❌ Build falló${NC}"
    exit 1
fi
echo ""

# ============================================================================
# 4. Verificar tamaño de la imagen
# ============================================================================
echo -e "${YELLOW}📦 Verificando tamaño de la imagen...${NC}"
IMAGE_SIZE=$(docker images cadet-backend:test --format "{{.Size}}")
echo -e "${GREEN}✅ Tamaño de la imagen: ${IMAGE_SIZE}${NC}"
echo ""

# ============================================================================
# 5. Levantar servicios
# ============================================================================
echo -e "${YELLOW}🚀 Levantando servicios...${NC}"
docker-compose -f docker-compose.prod.yml up -d

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Servicios iniciados${NC}"
else
    echo -e "${RED}❌ Error al iniciar servicios${NC}"
    exit 1
fi
echo ""

# ============================================================================
# 6. Esperar a que la aplicación esté lista
# ============================================================================
echo -e "${YELLOW}⏳ Esperando a que la aplicación esté lista...${NC}"
sleep 10

MAX_ATTEMPTS=30
ATTEMPT=0

while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
    if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo -e "${GREEN}✅ Aplicación está lista${NC}"
        break
    fi
    ATTEMPT=$((ATTEMPT + 1))
    echo -n "."
    sleep 2
done

if [ $ATTEMPT -eq $MAX_ATTEMPTS ]; then
    echo -e "${RED}❌ Timeout esperando la aplicación${NC}"
    echo "Ver logs con: docker-compose -f docker-compose.prod.yml logs app"
    exit 1
fi
echo ""

# ============================================================================
# 7. Tests de Health Check
# ============================================================================
echo -e "${YELLOW}🏥 Ejecutando health checks...${NC}"

# Test 1: Health endpoint
echo -n "  - Health endpoint... "
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo -e "${GREEN}✅${NC}"
else
    echo -e "${RED}❌${NC}"
fi

# Test 2: Database connection
echo -n "  - Database connection... "
HEALTH_RESPONSE=$(curl -s http://localhost:8080/actuator/health)
if echo "$HEALTH_RESPONSE" | grep -q "UP"; then
    echo -e "${GREEN}✅${NC}"
else
    echo -e "${RED}❌${NC}"
fi

echo ""

# ============================================================================
# 8. Mostrar información
# ============================================================================
echo "============================================================================"
echo -e "${GREEN}✨ Build local completado exitosamente${NC}"
echo "============================================================================"
echo ""
echo "📊 Información de los servicios:"
echo "  🌐 Aplicación: http://localhost:8080"
echo "  📊 Health Check: http://localhost:8080/actuator/health"
echo "  📚 Swagger UI: http://localhost:8080/swagger-ui.html"
echo "  🔐 Login: http://localhost:8080/login"
echo "  🗄️  Base de Datos: localhost:5432"
echo ""
echo "📝 Comandos útiles:"
echo "  Ver logs:        docker-compose -f docker-compose.prod.yml logs -f app"
echo "  Detener:         docker-compose -f docker-compose.prod.yml down"
echo "  Reiniciar:       docker-compose -f docker-compose.prod.yml restart app"
echo "  Ver containers:  docker-compose -f docker-compose.prod.yml ps"
echo ""
echo "============================================================================"
echo ""

# ============================================================================
# 9. Opcional: Abrir browser
# ============================================================================
read -p "¿Deseas abrir el navegador para ver la aplicación? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    if [[ "$OSTYPE" == "darwin"* ]]; then
        open http://localhost:8080
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        xdg-open http://localhost:8080
    elif [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "win32" ]]; then
        start http://localhost:8080
    fi
fi

echo ""
echo -e "${GREEN}🎉 ¡Todo listo! Tu aplicación está corriendo localmente en modo producción${NC}"
echo ""
