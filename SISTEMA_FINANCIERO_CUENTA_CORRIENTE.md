# Sistema Financiero - Cuenta Corriente con Algoritmo FIFO

## 📋 Descripción General

Sistema completo de gestión financiera basado en **Cuenta Corriente (Ledger)** con separación entre **Obligaciones (DEBE)** y **Transacciones (HABER)**, conciliados mediante un **Algoritmo FIFO** (First In, First Out).

### Beneficios Principales
✅ **Pagos Parciales**: El socio puede pagar la mitad de una cuota
✅ **Pagos Acumulados**: Pagar 3 meses juntos en una sola transacción
✅ **Trazabilidad Contable**: Saber exactamente qué pago cubrió qué mes
✅ **Control de Morosidad**: Identificación automática de socios morosos
✅ **Generación Automática**: Mensualidades creadas el día 1 de cada mes

---

## 🏗️ Arquitectura del Sistema

### Modelo de Datos

```
┌────────────────────────────────────────────────────────────────┐
│                  CONFIGURACION_CUOTAS                          │
│  (Reglas: Cuánto se cobra por gestión)                        │
│  - monto_matricula: 500 Bs                                     │
│  - monto_mensualidad: 100 Bs                                   │
│  - dia_limite_pago: 10                                         │
└────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌────────────────────────────────────────────────────────────────┐
│              ESTADO_CUENTA_SOCIO (El DEBE)                     │
│  Obligaciones financieras del socio                            │
│  - tipo_obligacion: MATRICULA, MENSUALIDAD, MULTA              │
│  - monto_original: Lo que debe pagar                           │
│  - monto_pagado_acumulado: Lo que ha pagado                    │
│  - estado_pago: PENDIENTE, PARCIAL, PAGADO, VENCIDO           │
└────────────────────────────────────────────────────────────────┘
                              │
                              │
                              ▼
┌────────────────────────────────────────────────────────────────┐
│            DETALLE_PAGO_DEUDA (La Conciliación)                │
│  Relaciona pagos con deudas (C:C Many-to-Many)                 │
│  - monto_aplicado: Cuánto de X pago cubrió Y deuda            │
└────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌────────────────────────────────────────────────────────────────┐
│             TRANSACCION_PAGO (El HABER)                        │
│  Dinero real que ingresa al sistema                            │
│  - monto_total: Monto del pago                                 │
│  - estado: EN_REVISION, APROBADO, RECHAZADO                    │
│  - comprobante_url: Imagen del comprobante                     │
└────────────────────────────────────────────────────────────────┘
```

---

## 🔄 Flujos de Negocio

### FLUJO 1: Generación Automática de Deudas

#### A. Deuda de Matrícula (Al Aprobar Afiliación)
**Trigger**: Cuando `SolicitudAfiliacionService.aprobarSolicitud()` se ejecuta

**Lógica**:
1. Busca la configuración de la gestión actual en `configuracion_cuotas`
2. Crea un registro en `estado_cuenta_socio`:
   ```java
   tipo_obligacion = "MATRICULA"
   monto_original = config.montoMatricula (ej: 500 Bs)
   estado_pago = "PENDIENTE"
   fecha_vencimiento = hoy + 30 días
   ```

#### B. Mensualidades (Cron Job Automático)
**Trigger**: Día 1 de cada mes a las 00:15 AM (Bolivia Time)

**Cron Expression**: `cron = "0 15 0 1 * ?"`, `zone = "America/La_Paz"`

**Lógica**:
1. Busca todos los socios activos (`estado = 1`)
2. Para cada socio, verifica si ya tiene mensualidad del mes
3. Si NO existe, crea:
   ```java
   tipo_obligacion = "MENSUALIDAD"
   mes = mes_actual (1-12)
   gestion = año_actual (2026)
   monto_original = config.montoMensualidad (ej: 100 Bs)
   fecha_vencimiento = año_actual/mes_actual/dia_limite (ej: 2026-02-10)
   estado_pago = "PENDIENTE"
   ```

**Ubicación del Scheduler**: [`MensualidadesScheduler.java`](cadet_backend/src/main/java/app/finanza/scheduler/MensualidadesScheduler.java)

---

### FLUJO 2: Registro de Pago por el Socio

**Endpoint**: `POST /api/transacciones-pago/socio/{socioId}/registrar`

**Parámetros**:
```
- montoTotal: 250 (String)
- metodoPago: "TRANSFERENCIA"
- referenciaBancaria: "REF-12345"
- observaciones: "Pago de 3 meses"
- comprobante: [Archivo JPG/PNG]
```

**Lógica**:
1. Valida que el archivo comprobante exista
2. Sube el comprobante a Cloudinary (carpeta: `comprobantes`)
3. Crea el registro en `transaccion_pago`:
   ```java
   estado = "EN_REVISION" // Pendiente de aprobación
   comprobante_url = "https://cloudinary.com/..."
   ```
4. **NO se toca ninguna deuda** en este punto

**Respuesta**:
```json
{
  "id": 42,
  "socioId": 15,
  "socioNombre": "Juan Pérez",
  "montoTotal": 250.00,
  "estado": "EN_REVISION",
  "compro banteUrl": "https://...",
  "fechaPago": "2026-02-07T10:30:00"
}
```

**Ejemplo cURL**:
```bash
curl -X POST "http://localhost:8080/api/transacciones-pago/socio/15/registrar" \
  -F "montoTotal=250" \
  -F "metodoPago=TRANSFERENCIA" \
  -F "referenciaBancaria=REF-12345" \
  -F "observaciones=Pago de 3 meses" \
  -F "comprobante=@comprobante.jpg"
```

---

### FLUJO 3: Algoritmo FIFO - Aprobación y Conciliación (⭐ CORAZÓN DEL SISTEMA)

**Endpoint**: `POST /api/transacciones-pago/{transaccionId}/aprobar`

**Parámetros**:
```
- adminId: 5 (opcional)
```

#### 🔍 Algoritmo Paso a Paso

**Prerrequisitos**:
- La transacción debe estar en estado `EN_REVISION`
- El socio debe tener deudas pendientes

**Pasos**:

```
1. VALIDACIÓN
   ✓ Verificar que la transacción existe
   ✓ Verificar que estado = "EN_REVISION"
   ✓ Si ya fue procesada, lanzar error (Idempotencia)

2. OBTENER FONDOS DISPONIBLES
   monto_disponible = transaccion.monto_total  // Ej: 250 Bs

3. BUSCAR DEUDAS (CRÍTICO: Ordenamiento FIFO)
   deudas = SELECT * FROM estado_cuenta_socio 
            WHERE socio_id = X 
            AND estado_pago IN ('PENDIENTE', 'PARCIAL')
            ORDER BY fecha_vencimiento ASC, id ASC  // MÁS ANTIGUAS PRIMERO

4. ITERAR SOBRE CADA DEUDA (Bucket Logic)
   
   Para cada deuda en deudas:
   
     a) Calcular saldo pendiente
        saldo = monto_original - monto_pagado_acumulado
        
     b) Determinar cuánto aplicar
        SI monto_disponible >= saldo:
           monto_aplicar = saldo  // Cubro toda la deuda
           nuevo_estado = "PAGADO"
        SINO:
           monto_aplicar = monto_disponible  // Cubro solo parte
           nuevo_estado = "PARCIAL"
     
     c) Crear detalle de conciliación
        INSERT INTO detalle_pago_deuda (
          fk_transaccion, fk_estado_cuenta, monto_aplicado
        )
     
     d) Actualizar la deuda
        UPDATE estado_cuenta_socio
        SET monto_pagado_acumulado += monto_aplicar,
            estado_pago = nuevo_estado
     
     e) Reducir fondos disponibles
        monto_disponible -= monto_aplicar
     
     f) SI monto_disponible = 0: BREAK  // Se acabó el dinero

5. MARCAR TRANSACCIÓN COMO APROBADO
   UPDATE transaccion_pago SET estado = 'APROBADO'

6. RETORNAR DETALLE DE CONCILIACIÓN
```

#### Ejemplo Real

**Escenario**:
- Socio sube comprobante por **250 Bs**
- Tiene 3 deudas pendientes:
  - Enero: 100 Bs (PENDIENTE)
  - Febrero: 100 Bs (PENDIENTE)
  - Marzo: 100 Bs (PENDIENTE)

**Ejecución del Algoritmo**:

```
Inicio: Tengo 250 Bs

Iteración 1 (Enero - más antigua):
  - Saldo deuda: 100 Bs
  - Tengo: 250 Bs
  - Aplico: 100 Bs COMPLETOS
  - Enero → PAGADO
  - Me quedan: 150 Bs

Iteración 2 (Febrero):
  - Saldo deuda: 100 Bs
  - Tengo: 150 Bs
  - Aplico: 100 Bs COMPLETOS
  - Febrero → PAGADO
  - Me quedan: 50 Bs

Iteración 3 (Marzo):
  - Saldo deuda: 100 Bs
  - Tengo: 50 Bs
  - Aplico: 50 Bs PARCIALES
  - Marzo → PARCIAL (debe 50 Bs)
  - Me quedan: 0 Bs  ❌ SE ACABÓ EL DINERO

Resultado:
  ✅ Enero: PAGADO
  ✅ Febrero: PAGADO
  ⚠️ Marzo: PARCIAL (50/100 pagados)
```

**Respuesta JSON**:
```json
{
  "id": 42,
  "socioId": 15,
  "montoTotal": 250.00,
  "estado": "APROBADO",
  "detallesConciliacion": [
    {
      "deudaId": 101,
      "tipoObligacion": "MENSUALIDAD",
      "mes": 1,
      "gestion": 2026,
      "montoAplicado": 100.00,
      "estadoDeudaDespues": "PAGADO"
    },
    {
      "deudaId": 102,
      "tipoObligacion": "MENSUALIDAD",
      "mes": 2,
      "gestion": 2026,
      "montoAplicado": 100.00,
      "estadoDeudaDespues": "PAGADO"
    },
    {
      "deudaId": 103,
      "tipoObligacion": "MENSUALIDAD",
      "mes": 3,
      "gestion": 2026,
      "montoAplicado": 50.00,
      "estadoDeudaDespues": "PARCIAL"
    }
  ]
}
```

**Ubicación del Código**: [`TransaccionPagoServiceImpl.java#aprobarYConciliar`](cadet_backend/src/main/java/app/finanza/service/impl/TransaccionPagoServiceImpl.java#L152-L264)

---

### FLUJO 4: Consulta de Morosidad

**Endpoint**: `GET /api/finanzas/estado-cuenta/socio/{socioId}/morosidad`

**Lógica**:
```sql
SELECT COUNT(*) FROM estado_cuenta_socio 
WHERE socio_id = X 
  AND estado_pago != 'PAGADO' 
  AND fecha_vencimiento < CURRENT_DATE
```

**Respuesta**:
```json
{
  "socioId": 15,
  "esMoroso": true,
  "deudasVencidas": 2,
  "mensaje": "El socio tiene 2 deuda(s) vencida(s)"
}
```

**Casos de Uso**:
- ❌ Bloquear acceso al socio moroso
- ❌ Ocultar socio de búsquedas públicas
- ❌ Impedir participación en votaciones
- 📧 Enviar recordatorios de pago

---

## 📡 API Reference

### Estado de Cuenta

#### Obtener Estado de Cuenta Completo
```http
GET /api/finanzas/estado-cuenta/socio/{socioId}
```

#### Obtener Solo Deudas Pendientes
```http
GET /api/finanzas/estado-cuenta/socio/{socioId}/pendientes
```

#### Verificar Morosidad
```http
GET /api/finanzas/estado-cuenta/socio/{socioId}/morosidad
```

#### Crear Obligación Manual (Admin)
```http
POST /api/finanzas/estado-cuenta
Content-Type: application/json

{
  "socioId": 15,
  "tipoObligacion": "MULTA",
  "gestion": 2026,
  "montoOriginal": 50.00,
  "fechaVencimiento": "2026-03-15"
}
```

#### Trigger Manual: Generar Mensualidades (Admin)
```http
POST /api/finanzas/estado-cuenta/generar-mensualidades
```

---

### Transacciones de Pago

#### Registrar Pago (Socio)
```http
POST /api/transacciones-pago/socio/{socioId}/registrar
Content-Type: multipart/form-data

montoTotal=250
metodoPago=TRANSFERENCIA
referenciaBancaria=REF-12345
observaciones=Pago de 3 meses
comprobante=[archivo]
```

#### Aprobar Pago (Admin) - Ejecuta FIFO
```http
POST /api/transacciones-pago/{transaccionId}/aprobar?adminId=5
```

#### Rechazar Pago (Admin)
```http
POST /api/transacciones-pago/{transaccionId}/rechazar?adminId=5&motivo=Comprobante ilegible
```

#### Obtener Pagos Pendientes de Revisión
```http
GET /api/transacciones-pago/pendientes-revision
```

---

## 🛡️ Validaciones Implementadas

### 1. Transaccionalidad (`@Transactional`)
✅ **Algoritmo FIFO es atómico**: Si falla alguna operación, se hace rollback completo
✅ **Garantía**: O todo se guarda, o nada se guarda

### 2. Idempotencia
✅ **Validación**: Antes de procesar, verifica `estado = "EN_REVISION"`
✅ **Protección**: Doble clic en "Aprobar" NO ejecuta el algoritmo dos veces

### 3. No Borrado Físico
✅ **Pagos y deudas NUNCA se eliminan** con `DELETE`
✅ **Anulación**: Cambiar estado a `ANULADO` y ejecutar lógica inversa
✅ **Auditoría**: Mantiene integridad histórica

### 4. Ordenamiento FIFO
✅ **CRÍTICO**: Query usa `ORDER BY fecha_vencimiento ASC`
✅ **Garantía**: Se pagan primero las deudas más antiguas

---

## 📊 Estructura de Base de Datos

```sql
-- Tabla 1: Configuración de Cuotas
CREATE TABLE configuracion_cuotas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    gestion INT UNIQUE NOT NULL,
    monto_matricula DECIMAL(10,2) NOT NULL,
    monto_mensualidad DECIMAL(10,2) NOT NULL,
    dia_limite_pago INT DEFAULT 10,
    estado INT DEFAULT 1
);

-- Tabla 2: Estado de Cuenta (Las Deudas)
CREATE TABLE estado_cuenta_socio (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_socio INT NOT NULL,
    tipo_obligacion VARCHAR(50) NOT NULL, -- MATRICULA, MENSUALIDAD, MULTA
    gestion INT NOT NULL,
    mes INT, -- NULL para Matrícula
    monto_original DECIMAL(10,2) NOT NULL,
    fecha_emision DATE DEFAULT CURRENT_DATE,
    fecha_vencimiento DATE NOT NULL,
    estado_pago VARCHAR(20) DEFAULT 'PENDIENTE', -- PENDIENTE, PAGADO, PARCIAL, VENCIDO
    monto_pagado_acumulado DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (fk_socio) REFERENCES socio(id),
    INDEX idx_fecha_venc (fecha_vencimiento),
    INDEX idx_estado (estado_pago)
);

-- Tabla 3: Transacciones de Pago (El Dinero)
CREATE TABLE transaccion_pago (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_socio INT NOT NULL,
    fk_usuario_admin INT,
    monto_total DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(50),
    comprobante_url VARCHAR(500),
    referencia_bancaria VARCHAR(100),
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    estado VARCHAR(20) DEFAULT 'EN_REVISION', -- EN_REVISION, APROBADO, RECHAZADO
    FOREIGN KEY (fk_socio) REFERENCES socio(id),
    FOREIGN KEY (fk_usuario_admin) REFERENCES usuario(id),
    INDEX idx_estado (estado)
);

-- Tabla 4: Detalle de Conciliación (Many-to-Many)
CREATE TABLE detalle_pago_deuda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_transaccion INT NOT NULL,
    fk_estado_cuenta INT NOT NULL,
    monto_aplicado DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (fk_transaccion) REFERENCES transaccion_pago(id),
    FOREIGN KEY (fk_estado_cuenta) REFERENCES estado_cuenta_socio(id)
);
```

---

## 🚀 Testing

### 1. Crear Configuración de Cuotas
```http
POST /api/finanzas/configuracion-cuotas
Content-Type: application/json

{
  "gestion": 2026,
  "montoMatricula": 500.00,
  "montoMensualidad": 100.00,
  "diaLimitePago": 10
}
```

### 2. Socio Registra Pago
```bash
curl -X POST "http://localhost:8080/api/transacciones-pago/socio/15/registrar" \
  -F "montoTotal=250" \
  -F "metodoPago=TRANSFER ENCIA" \
  -F "comprobante=@comprobante.jpg"
```

### 3. Admin Aprueba y Ejecuta FIFO
```bash
curl -X POST "http://localhost:8080/api/transacciones-pago/42/aprobar?adminId=5"
```

### 4. Verificar Estado de Cuenta
```bash
curl "http://localhost:8080/api/finanzas/estado-cuenta/socio/15/pendientes"
```

---

## 📁 Archivos Implementados

### Entidades
- ✅ [`ConfiguracionCuotasEntity.java`](cadet_backend/src/main/java/app/finanza/entity/ConfiguracionCuotasEntity.java)
- ✅ [`EstadoCuentaSocioEntity.java`](cadet_backend/src/main/java/app/finanza/entity/EstadoCuentaSocioEntity.java)
- ✅ [`TransaccionPagoEntity.java`](cadet_backend/src/main/java/app/finanza/entity/TransaccionPagoEntity.java)
- ✅ [`DetallePagoDeudaEntity.java`](cadet_backend/src/main/java/app/finanza/entity/DetallePagoDeudaEntity.java)

### Repositorios
- ✅ [`EstadoCuentaSocioRepository.java`](cadet_backend/src/main/java/app/finanza/repository/EstadoCuentaSocioRepository.java) - Con queries FIFO
- ✅ [`TransaccionPagoRepository.java`](cadet_backend/src/main/java/app/finanza/repository/TransaccionPagoRepository.java)
- ✅ [`DetallePagoDeudaRepository.java`](cadet_backend/src/main/java/app/finanza/repository/DetallePagoDeudaRepository.java)

### Servicios
- ✅ [`EstadoCuentaSocioService.java`](cadet_backend/src/main/java/app/finanza/service/EstadoCuentaSocioService.java)
- ✅ [`EstadoCuentaSocioServiceImpl.java`](cadet_backend/src/main/java/app/finanza/service/impl/EstadoCuentaSocioServiceImpl.java) - Generación de deudas
- ✅ [`TransaccionPagoServiceImpl.java`](cadet_backend/src/main/java/app/finanza/service/impl/TransaccionPagoServiceImpl.java) - **Algoritmo FIFO**

### Controllers
- ✅ [`EstadoCuentaSocioController.java`](cadet_backend/src/main/java/app/finanza/controller/EstadoCuentaSocioController.java)
- ✅ [`TransaccionPagoController.java`](cadet_backend/src/main/java/app/finanza/controller/TransaccionPagoController.java) - Actualizado

### Scheduler
- ✅ [`MensualidadesScheduler.java`](cadet_backend/src/main/java/app/finanza/scheduler/MensualidadesScheduler.java) - Cron job para Bolivia

### DTOs
- ✅ [`EstadoCuentaSocioDTO.java`](cadet_backend/src/main/java/app/finanza/dto/EstadoCuentaSocioDTO.java)
- ✅ [`DetalleConciliacionDTO.java`](cadet_backend/src/main/java/app/finanza/dto/DetalleConciliacionDTO.java)

---

## 🎓 Conceptos Clave

### ¿Qué es FIFO?
**First In, First Out** (Primero en Entrar, Primero en Salir)

Cuando el socio paga, el dinero se aplica primero a las deudas **más antiguas** (las que vencen primero).

**Ejemplo**:
- Enero vence el 10/01
- Febrero vence el 10/02
- Marzo vence el 10/03

Si paga en marzo, el dinero va primero a Enero, luego a Febrero, y finalmente a Marzo.

### ¿Qué es el Bucket Logic?
Es la técnica de "llenar baldes" secuencialmente:

```
Tengo 250 Bs

Balde 1 (Enero): ⬜⬜⬜ Necesita 100 Bs
                 ✅✅✅ Lleno (me sobran 150 Bs)

Balde 2 (Febrero): ⬜⬜⬜ Necesita 100 Bs
                   ✅✅✅ Lleno (me sobran 50 Bs)

Balde 3 (Marzo): ⬜⬜⬜ Necesita 100 Bs
                 ✅⬜⬜ PARCIAL (me sobran 0 Bs)
```

---

## ⚠️ Notas Importantes

1. **Zona Horaria**: El cron job usa `America/La_Paz` (Bolivia, UTC-4)
2. **No Borrado**: Los registros financieros NUNCA se eliminan con DELETE
3. **Transaccionalidad**: Todo el algoritmo FIFO es una transacción atómica
4. **Idempotencia**: El mismo pago no se puede aprobar dos veces
5. **Ordenamiento FIFO**: Las fechas de vencimiento determinan el orden

---

¡Sistema completo y listo para producción! 🎉
