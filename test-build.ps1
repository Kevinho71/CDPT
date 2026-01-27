# ============================================================================
# PowerShell Script de Build y Test Local - CADET Backend
# ============================================================================
# Este script simula el build de producción localmente (Windows)
# Úsalo antes de hacer deployment a Dokploy
# ============================================================================

$ErrorActionPreference = "Stop"

Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host "🚀 CADET Backend - Local Production Build & Test" -ForegroundColor Cyan
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host ""

# ============================================================================
# 1. Verificar Pre-requisitos
# ============================================================================
Write-Host "📋 Verificando pre-requisitos..." -ForegroundColor Yellow

# Check Docker
try {
    docker --version | Out-Null
    Write-Host "✅ Docker instalado" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker no está instalado" -ForegroundColor Red
    exit 1
}

# Check Docker Compose
try {
    docker-compose --version | Out-Null
    Write-Host "✅ Docker Compose instalado" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker Compose no está instalado" -ForegroundColor Red
    exit 1
}

# Check .env file
if (!(Test-Path .env)) {
    Write-Host "❌ Archivo .env no encontrado" -ForegroundColor Red
    Write-Host "Copia .env.template a .env y configura las variables"
    exit 1
}
Write-Host "✅ Archivo .env encontrado" -ForegroundColor Green
Write-Host ""

# ============================================================================
# 2. Limpiar builds anteriores
# ============================================================================
Write-Host "🧹 Limpiando builds anteriores..." -ForegroundColor Yellow
docker-compose -f docker-compose.prod.yml down -v 2>$null
docker system prune -f
Write-Host "✅ Limpieza completada" -ForegroundColor Green
Write-Host ""

# ============================================================================
# 3. Build de la aplicación
# ============================================================================
Write-Host "🔨 Compilando aplicación (esto puede tardar 3-5 minutos)..." -ForegroundColor Yellow
docker build -t cadet-backend:test .

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Build exitoso" -ForegroundColor Green
} else {
    Write-Host "❌ Build falló" -ForegroundColor Red
    exit 1
}
Write-Host ""

# ============================================================================
# 4. Verificar tamaño de la imagen
# ============================================================================
Write-Host "📦 Verificando tamaño de la imagen..." -ForegroundColor Yellow
$imageSize = docker images cadet-backend:test --format "{{.Size}}"
Write-Host "✅ Tamaño de la imagen: $imageSize" -ForegroundColor Green
Write-Host ""

# ============================================================================
# 5. Levantar servicios
# ============================================================================
Write-Host "🚀 Levantando servicios..." -ForegroundColor Yellow
docker-compose -f docker-compose.prod.yml up -d

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Servicios iniciados" -ForegroundColor Green
} else {
    Write-Host "❌ Error al iniciar servicios" -ForegroundColor Red
    exit 1
}
Write-Host ""

# ============================================================================
# 6. Esperar a que la aplicación esté lista
# ============================================================================
Write-Host "⏳ Esperando a que la aplicación esté lista..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

$maxAttempts = 30
$attempt = 0
$ready = $false

while ($attempt -lt $maxAttempts) {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing -TimeoutSec 2
        if ($response.StatusCode -eq 200) {
            Write-Host "`n✅ Aplicación está lista" -ForegroundColor Green
            $ready = $true
            break
        }
    } catch {
        Write-Host "." -NoNewline
    }
    $attempt++
    Start-Sleep -Seconds 2
}

if (-not $ready) {
    Write-Host "`n❌ Timeout esperando la aplicación" -ForegroundColor Red
    Write-Host "Ver logs con: docker-compose -f docker-compose.prod.yml logs app"
    exit 1
}
Write-Host ""

# ============================================================================
# 7. Tests de Health Check
# ============================================================================
Write-Host "🏥 Ejecutando health checks..." -ForegroundColor Yellow

# Test 1: Health endpoint
Write-Host "  - Health endpoint... " -NoNewline
try {
    $healthResponse = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing
    if ($healthResponse.StatusCode -eq 200) {
        Write-Host "✅" -ForegroundColor Green
    } else {
        Write-Host "❌" -ForegroundColor Red
    }
} catch {
    Write-Host "❌" -ForegroundColor Red
}

# Test 2: Database connection
Write-Host "  - Database connection... " -NoNewline
try {
    $healthResponse = Invoke-RestMethod -Uri "http://localhost:8080/actuator/health"
    if ($healthResponse.status -eq "UP") {
        Write-Host "✅" -ForegroundColor Green
    } else {
        Write-Host "❌" -ForegroundColor Red
    }
} catch {
    Write-Host "❌" -ForegroundColor Red
}

Write-Host ""

# ============================================================================
# 8. Mostrar información
# ============================================================================
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host "✨ Build local completado exitosamente" -ForegroundColor Green
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "📊 Información de los servicios:" -ForegroundColor Cyan
Write-Host "  🌐 Aplicación: http://localhost:8080"
Write-Host "  📊 Health Check: http://localhost:8080/actuator/health"
Write-Host "  📚 Swagger UI: http://localhost:8080/swagger-ui.html"
Write-Host "  🔐 Login: http://localhost:8080/login"
Write-Host "  🗄️  Base de Datos: localhost:5432"
Write-Host ""
Write-Host "📝 Comandos útiles:" -ForegroundColor Cyan
Write-Host "  Ver logs:        docker-compose -f docker-compose.prod.yml logs -f app"
Write-Host "  Detener:         docker-compose -f docker-compose.prod.yml down"
Write-Host "  Reiniciar:       docker-compose -f docker-compose.prod.yml restart app"
Write-Host "  Ver containers:  docker-compose -f docker-compose.prod.yml ps"
Write-Host ""
Write-Host "============================================================================" -ForegroundColor Cyan
Write-Host ""

# ============================================================================
# 9. Opcional: Abrir browser
# ============================================================================
$openBrowser = Read-Host "¿Deseas abrir el navegador para ver la aplicación? (y/n)"
if ($openBrowser -eq "y" -or $openBrowser -eq "Y") {
    Start-Process "http://localhost:8080"
}

Write-Host ""
Write-Host "🎉 ¡Todo listo! Tu aplicación está corriendo localmente en modo producción" -ForegroundColor Green
Write-Host ""
