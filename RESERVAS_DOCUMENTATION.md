# Documentación del Módulo de Reservas de Ambientes

## Descripción General

El módulo de reservas permite a los socios del colegio reservar espacios físicos (consultorios, auditorios, etc.) con integración automática al sistema financiero.

**Principio fundamental: "Una reserva confirmada = Una deuda pendiente"**

## Arquitectura

### 1. Entidades

#### AmbienteEntity
Catálogo de espacios físicos disponibles.

```java
- id: Integer (PK)
- nombre: String (Ej: "Consultorio 1")
- descripcion: Text
- precio_hora: BigDecimal 
- estado: Integer (1: Activo, 0: Inactivo)
```

#### ReservaAmbienteEntity
Registro de reservas con dual-state tracking.

```java
- id: Integer (PK)
- fk_ambiente: Integer (FK → ambiente)
- fk_socio: Integer (FK → socio)
- fecha_reserva: LocalDate
- hora_inicio: LocalTime
- hora_fin: LocalTime
- monto_total: BigDecimal (calculado automáticamente)
- estado_reserva: String ("CONFIRMADA", "CANCELADA")
- estado_pago: String ("PENDIENTE", "PAGADO")
- fecha_creacion: LocalDateTime
```

### 2. Integración con Módulo Financiero

Cuando se crea una reserva, automáticamente se genera un registro en `estado_cuenta_socio`:

```java
EstadoCuentaSocioEntity {
    tipo_obligacion: "ALQUILER"
    concepto: "Alquiler Consultorio 1 (15/Feb/2026)"
    fk_reserva: [ID de la reserva]
    monto_original: [precio_hora * (hora_fin - hora_inicio)]
    fecha_vencimiento: [fecha_reserva + 15 días]
    estado_pago: "PENDIENTE"
}
```

## Validaciones y Lógica de Negocio

### Validaciones al Crear Reserva

1. **Ambiente Activo**: Verifica que el ambiente existe y está en estado activo.

2. **Horario Lógico**: hora_fin debe ser posterior a hora_inicio.

3. **Solapamiento**: Verifica que no haya otra reserva confirmada en el mismo horario.
   ```sql
   WHERE ambiente_id = X 
     AND fecha_reserva = Y
     AND estado_reserva = 'CONFIRMADA'
     AND (hora_inicio < hora_fin_solicitada AND hora_fin > hora_inicio_solicitada)
   ```

4. **Política "Cero Morosidad"**: Bloquea reserva si el socio tiene CUALQUIER deuda vencida (sin importar antigüedad).
   ```java
   Long deudasVencidas = contarDeudasVencidas(socioId, LocalDate.now());
   if (deudasVencidas > 0) → BLOQUEADO
   ```
   
   **Ventaja**: Obliga a los socios a estar al día antes de usar instalaciones. Elimina el problema de la "Trampa FIFO" porque no hay deudas anteriores a la reserva.

### Cálculo de Monto

```java
long minutosReserva = ChronoUnit.MINUTES.between(horaInicio, horaFin);
BigDecimal horasReserva = new BigDecimal(minutosReserva).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
BigDecimal montoTotal = precioHora.multiply(horasReserva);
```

**Ejemplo**: Consultorio a 50 Bs/hora, reserva de 14:00 a 16:30
- Minutos: 150
- Horas: 2.5
- Monto: 50 * 2.5 = 125 Bs

### Flujo de Cancelación

1. **Reserva PENDIENTE**:
   - Cambiar estado_reserva → "CANCELADA"
   - **Eliminar** la deuda vinculada (no se cobra)

2. **Reserva PAGADA**:
   - Cambiar estado_reserva → "CANCELADA"
   - Marcar deuda como "[CANCELADA - SIN REEMBOLSO]"
   - Opción futura: generar "Saldo a Favor"

### Sincronización de Estados

Cuando el algoritmo FIFO marca una deuda como PAGADA, debe actualizar la reserva:

```java
reserva.setEstadoPago("PAGADO");
reservaRepository.save(reserva);
```

Esto permite al calendario mostrar visualmente qué reservas están pagas.

## API Endpoints

### Gestión de Ambientes

#### GET /api/ambientes
Lista ambientes activos.

**Response:**
```json
{
  "success": true,
  "message": "Ambientes obtenidos correctamente",
  "data": [
    {
      "id": 1,
      "nombre": "Consultorio 1",
      "descripcion": "Consultorio privado para terapia individual",
      "precioHora": 50.00,
      "estado": 1
    }
  ]
}
```

#### GET /api/ambientes/{id}
Obtiene un ambiente específico.

#### POST /api/ambientes
Crea nuevo ambiente (solo administradores).

**Request:**
```json
{
  "nombre": "Auditorio Principal",
  "descripcion": "Capacidad para 100 personas",
  "precioHora": 200.00,
  "estado": 1
}
```

#### PUT /api/ambientes/{id}
Actualiza ambiente existente.

#### DELETE /api/ambientes/{id}
Desactiva ambiente (soft delete: estado = 0).

---

### Gestión de Reservas

#### GET /api/reservas/disponibilidad
Verifica disponibilidad de un horario.

**Query Parameters:**
- `ambienteId`: Integer
- `fecha`: LocalDate (formato: YYYY-MM-DD)
- `horaInicio`: LocalTime (formato: HH:mm)
- `horaFin`: LocalTime (formato: HH:mm)

**Response:**
```json
{
  "success": true,
  "message": "Disponibilidad verificada",
  "data": {
    "disponible": true,
    "mensaje": "El horario está disponible"
  }
}
```

**Posibles mensajes de error:**
- "El ambiente no está disponible (estado inactivo)"
- "La hora de fin debe ser posterior a la hora de inicio"
- "El horario está ocupado"

#### POST /api/reservas
Crea una reserva (genera automáticamente la deuda).

**Request:**
```json
{
  "ambienteId": 1,
  "socioId": 42,
  "fechaReserva": "2026-02-15",
  "horaInicio": "14:00",
  "horaFin": "16:30"
}
```

**Response (Success):**
```json
{
  "success": true,
  "message": "Reserva creada correctamente. Se generó una deuda en su cuenta corriente.",
  "data": {
    "id": 123,
    "ambienteId": 1,
    "ambienteNombre": "Consultorio 1",
    "socioId": 42,
    "socioNombre": "Juan Pérez",
    "fechaReserva": "2026-02-15",
    "horaInicio": "14:00",
    "horaFin": "16:30",
    "montoTotal": 125.00,
    "estadoReserva": "CONFIRMADA",
    "estadoPago": "PENDIENTE",
    "fechaCreacion": "2026-01-20T10:30:00",
    "deudaId": 456
  }
}
```

**Response (Error - Morosidad):**
```json
{
  "success": false,
  "message": "No puede realizar reservas debido a morosidad. Tiene 2 deuda(s) vencida(s) hace más de 3 meses.",
  "data": null
}
```

#### DELETE /api/reservas/{id}
Cancela una reserva.

**Response:**
```json
{
  "success": true,
  "message": "Reserva cancelada correctamente",
  "data": null
}
```

#### GET /api/reservas/calendario
Obtiene todas las reservas de un día (vista de calendario).

**Query Parameters:**
- `ambienteId`: Integer
- `fecha`: LocalDate

**Response:**
```json
{
  "success": true,
  "message": "Reservas del día obtenidas correctamente",
  "data": [
    {
      "id": 123,
      "horaInicio": "08:00",
      "horaFin": "10:00",
      "socioNombre": "María López",
      "estadoReserva": "CONFIRMADA",
      "estadoPago": "PAGADO"
    },
    {
      "id": 124,
      "horaInicio": "14:00",
      "horaFin": "16:30",
      "socioNombre": "Juan Pérez",
      "estadoReserva": "CONFIRMADA",
      "estadoPago": "PENDIENTE"
    }
  ]
}
```

#### GET /api/reservas/socio/{socioId}/futuras
Obtiene reservas futuras de un socio.

**Response:**
```json
{
  "success": true,
  "message": "Reservas futuras obtenidas correctamente",
  "data": [...]
}
```

## Consideraciones Técnicas

### Atomicidad de Transacciones

El método `crearReserva()` es **@Transactional**:

```java
@Transactional
public ReservaResponseDTO crearReserva(ReservaCreateDTO dto) {
    // 1. Validaciones
    // 2. Crear reserva
    reserva = reservaRepository.save(reserva);
    
    // 3. Crear deuda
    deuda = estadoCuentaRepository.save(deuda);
    
    // Si cualquiera falla → ROLLBACK de ambos
    return construirResponse(reserva, deuda.getId());
}
```

**Garantía**: Nunca existirá una reserva sin su deuda correspondiente.

### Índice Parcial UNIQUE

El índice permite múltiples ALQUILER del mismo socio en el mismo mes:

```sql
CREATE UNIQUE INDEX idx_unique_mensualidad 
ON estado_cuenta_socio (fk_socio, tipo_obligacion, gestion, mes) 
WHERE tipo_obligacion IN ('MENSUALIDAD', 'MATRICULA');
```

**Resultado**:
- ❌ Bloqueado: 2 MENSUALIDAD de Febrero 2026 del mismo socio
- ✅ Permitido: 5 ALQUILER de Febrero 2026 del mismo socio

### Algoritmo de Detección de Solapamiento

```sql
SELECT COUNT(*) > 0 
FROM reserva_ambiente r
JOIN ambiente a ON r.fk_ambiente = a.id
WHERE a.id = :ambienteId
  AND r.fecha_reserva = :fecha
  AND r.estado_reserva = 'CONFIRMADA'
  AND (r.hora_inicio < :horaFin AND r.hora_fin > :horaInicio)
```

**Casos de prueba**:
- Existente: 10:00-12:00
- Solicitado: 11:00-13:00 → ❌ SOLAPA (11 < 12 AND 13 > 10)
- Solicitado: 08:00-10:00 → ✅ NO SOLAPA (8 < 10 BUT 10 = 10, fallará segunda condición)
- Solicitado: 12:00-14:00 → ✅ NO SOLAPA (12 = 12 BUT 14 > 10 no cumple ambas)

**NOTA**: El algoritmo usa `<` y `>` (sin igual), por lo que:
- Reserva 10:00-12:00 seguida de 12:00-14:00 **SÍ está permitida** (sin gap).

### Sincronización con Sistema FIFO

Después de que `TransaccionPagoService` ejecuta el algoritmo FIFO y marca deudas como PAGADAS, debe llamar a:

```java
reservaService.marcarReservaComoPagada(reservaId);
```

Esto actualiza `reserva_ambiente.estado_pago = 'PAGADO'` para reflejar el pago en el calendario.

## Escenarios de Uso

### Escenario 1: Reserva Normal
1. Psicólogo selecciona "Consultorio 1" para el 15/Feb, 14:00-16:30
2. Sistema verifica disponibilidad → OK
3. Sistema verifica morosidad → OK (no tiene deudas antiguas)
4. Sistema crea reserva (monto: 125 Bs)
5. Sistema crea deuda en estado_cuenta_socio (vencimiento: 02/Mar)
6. Psicólogo ve en su oficina virtual: "Deuda: Alquiler Consultorio 1 (15/Feb/2026) - 125 Bs"

### Escenario 2: Bloqueo por Política "Cero Morosidad"
1. Psicólogo intenta reservar "Auditorio" para el 20/Feb
2. Sistema verifica morosidad → BLOQUEADO
3. Consulta: ¿Tiene deudas vencidas? → SÍ (Mensualidad Enero 2026 - vencida hace 1 mes)
4. Respuesta: "No puede realizar reservas mientras tenga deudas pendientes. Por favor, regularice sus obligaciones antes de reservar. Deudas vencidas: 1"
5. **Resultado**: El socio DEBE pagar todo antes de reservar (no hay excepciones por antigüedad)

### Escenario 3: Cancelación con Deuda Pendiente
1. Psicólogo cancela reserva del 15/Feb (aún no pagó)
2. Sistema cambia reserva.estado_reserva → "CANCELADA"
3. Sistema **elimina** la deuda vinculada
4. Resultado: No debe pagar nada

### Escenario 4: Cancelación con Deuda Pagada
1. Psicólogo pagó 125 Bs (FIFO lo aplicó a la reserva)
2. Psicólogo cancela reserva del 15/Feb
3. Sistema cambia reserva.estado_reserva → "CANCELADA"
4. Sistema actualiza deuda.concepto → "... [CANCELADA - SIN REEMBOLSO]"
5. Resultado: **No hay reembolso** (política del colegio)

### Escenario 5: Eliminación de la "Trampa del FIFO" (Política "Cero Morosidad")
**ANTES** (con límite de 3 meses):
1. Psicólogo debe Enero 2026: 100 Bs (vencida hace 1 mes)
2. Sistema permite reservar porque "solo tiene 1 mes de atraso"
3. Hace reserva: 50 Bs
4. Paga 50 Bs pensando que es para la reserva
5. FIFO lo aplica a Enero (más antigua)
6. Resultado: Confusión y reserva sigue PENDIENTE

**AHORA** (Cero Morosidad):
1. Psicólogo debe Enero 2026: 100 Bs (vencida)
2. Intenta reservar → **BLOQUEADO INMEDIATAMENTE**
3. Sistema: "No puede reservar mientras tenga deudas pendientes"
4. Psicólogo paga 100 Bs (limpia Enero)
5. **AHORA SÍ** puede reservar
6. Hace reserva: 50 Bs
7. Paga 50 Bs
8. FIFO busca la deuda más antigua → ¡Es la reserva!
9. Resultado: **Pago directo a la reserva, cero confusión**

**Ventaja**: Al obligar a pagar TODO antes de reservar, el saldo siempre está en cero al momento de la reserva. El pago subsecuente va DIRECTAMENTE a la reserva (porque no hay nada más viejo).

## Mejoras Futuras

### 1. Sistema de Reembolsos
- Generar "Saldo a Favor" cuando cancelan reserva pagada
- Aplicar saldo a futuras deudas automáticamente

### 2. Políticas de Cancelación
- Gratuita si cancela con >48 horas de anticipación
- 50% de penalización si cancela con 24-48 horas
- 100% cobrado si cancela con <24 horas

### 3. Recordatorios Automáticos
- Email 3 días antes de la reserva
- SMS 1 día antes
- Notificación 2 horas antes

### 4. Bloqueo de Horarios por Admin
- Marcar horarios como "No Disponibles" para mantenimiento
- Crear reservas sin generar deuda (eventos del colegio)

### 5. Reportes
- Ambientes más reservados
- Ingresos proyectados vs reales por ALQUILER
- Tasa de cancelación por socio
- Horarios pico de uso

## Estructura de Archivos

```
app/reservas/
├── entity/
│   ├── AmbienteEntity.java
│   └── ReservaAmbienteEntity.java
├── repository/
│   ├── AmbienteRepository.java
│   └── ReservaAmbienteRepository.java
├── dto/
│   ├── AmbienteDTO.java
│   ├── ReservaCreateDTO.java
│   ├── ReservaResponseDTO.java
│   ├── DisponibilidadRequestDTO.java
│   └── DisponibilidadResponseDTO.java
├── service/
│   ├── ReservaService.java
│   └── impl/
│       └── ReservaServiceImpl.java
└── controller/
    ├── AmbienteController.java
    └── ReservaController.java
```

## Base de Datos

### Tablas Principales
- `ambiente`: Catálogo de espacios
- `reserva_ambiente`: Registro de reservas
- `estado_cuenta_socio`: Deudas generadas (compartida con finanzas)

### Foreign Keys
- `reserva_ambiente.fk_ambiente` → `ambiente.id`
- `reserva_ambiente.fk_socio` → `socio.id`
- `estado_cuenta_socio.fk_reserva` → `reserva_ambiente.id` (nullable)

### Índices
- `idx_reserva_ambiente`: Búsqueda por ambiente
- `idx_reserva_socio`: Reservas de un socio
- `idx_reserva_fecha`: Calendario de reservas
- `idx_unique_mensualidad`: Previene duplicados MENSUALIDAD/MATRICULA

---

**Versión:** 1.0  
**Fecha:** Enero 2026  
**Autor:** Sistema CADET - Módulo de Reservas
