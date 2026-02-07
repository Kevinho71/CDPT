# Módulo Clínico Sistémico - Multi-Paciente

## 📋 Descripción General

Sistema clínico completo que evoluciona del modelo tradicional **"1 Cita = 1 Paciente"** al modelo **Sistémico** que permite:
- ✅ **Terapia Individual**: Un paciente, una sesión
- ✅ **Terapia de Pareja**: Dos pacientes, una sesión compartida
- ✅ **Terapia Familiar**: Múltiples pacientes (padres, hijos), una sesión
- ✅ **Terapia Grupal**: Varios pacientes en la misma sesión

### Beneficios Principales
✅ **Historial Compartido**: Una nota de terapia de pareja es visible para ambos miembros
✅ **Privacidad Controlada**: Las notas individuales NO aparecen en historiales de otros
✅ **Gestión de Leads**: Convierte solicitudes de la landing page en citas reales
✅ **Trazabilidad Completa**: Saber quiénes asistieron a cada sesión

---

## 🏗️ Arquitectura del Sistema

### Modelo de Datos

```
┌─────────────────────────────────────────────────────────┐
│            SOLICITUD_CITA (El Lead/Contacto)            │
│  Visitante llena formulario en landing page            │
│  - Estado: PENDIENTE, CONTACTADO, CONVERTIDO           │
└─────────────────────────────────────────────────────────┘
                         │
                         ▼ (Conversión)
┌─────────────────────────────────────────────────────────┐
│                  PACIENTE (Registro Real)               │
│  Datos completos del paciente del psicólogo             │
│  - CI, fecha nacimiento, teléfono, contacto emergencia  │
└─────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                CITA (La Sesión Agendada)                │
│  - Fecha, hora, modalidad (PRESENCIAL/VIRTUAL)          │
│  - Tipo: INDIVIDUAL, PAREJA, FAMILIA, GRUPO            │
│  - fk_paciente: El "titular" (quien reserva/paga)      │
└─────────────────────────────────────────────────────────┘
                         │
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│          CITA_PARTICIPANTES (La Magia Sistémica)        │
│  Tabla pivote que registra TODOS los asistentes        │
│  - tipo_participacion: TITULAR, PAREJA, HIJO, PADRE     │
└─────────────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│        HISTORIA_CLINICA (Las Notas del Psicólogo)       │
│  CAMBIO CRÍTICO: fk_paciente ahora es NULLABLE          │
│  - Si es NULL: Nota compartida (visible para todos)    │
│  - Si tiene ID: Nota privada (solo ese paciente)       │
└─────────────────────────────────────────────────────────┘
```

---

## 🔄 Flujos de Negocio

### FLUJO 1: Recepción del Lead (Landing Page)

**Actor**: Paciente Potencial (Visitante Anónimo)

**Trigger**: Llena formulario de contacto en perfil público del psicólogo

**Endpoint**: `POST /api/clinica/solicitudes-cita/public/{perfilSocioId}`

**Payload**:
```json
{
  "nombrePaciente": "Ana Pérez",
  "celular": "77123456",
  "email": "ana@example.com",
  "motivoConsulta": "Busco terapia de pareja, tenemos problemas de comunicación",
  "modalidad": "PRESENCIAL"
}
```

**Lógica Backend**:
1. Valida que el `perfilSocioId` existe
2. Crea registro en `solicitud_cita`
3. Estado inicial: **PENDIENTE**
4. El psicólogo recibe notificación (badge "Tienes 1 solicitud nueva")

**Resultado**: El lead queda en la "Bandeja de Entrada" del psicólogo

---

### FLUJO 2: Conversión y Registro de Pacientes (El Cruce)

**Escenario Real**:
- Ana llenó el formulario
- El psicólogo la contactó por WhatsApp (botón directo con celular)
- Ana dijo: *"Quiero venir con mi esposo Carlos"*
- El psicólogo agenda la sesión

**Endpoint**: `POST /api/clinica/citas-sistemicas/convertir-solicitud/{solicitudId}`

**Payload**:
```json
{
  "fkPerfilSocio": 5,
  "idTitular": 105,
  "participantes": [
    {"idPaciente": 105, "tipoParticipacion": "TITULAR"},
    {"idPaciente": 106, "tipoParticipacion": "PAREJA"}
  ],
  "fechaCita": "2026-02-20",
  "horaInicio": "16:00",
  "horaFin": "17:00",
  "tipoSesion": "PAREJA",
  "modalidad": "PRESENCIAL",
  "motivoBreve": "Terapia de pareja - Problemas de comunicación",
  "montoAcordado": 200.00
}
```

**Lógica Transaccional** (CRÍTICA):
```
1. Verificar si Ana existe en tabla 'paciente'
   - SI NO existe: Crearla con los datos de la solicitud
   - SI existe: Usar el registro existente

2. Verificar si Carlos existe en tabla 'paciente'
   - SI NO existe: Crearlo (el psicólogo lo registra manualmente antes)
   - SI existe: Usar el registro existente

3. Crear registro en 'cita':
   - fk_paciente = 105 (Ana, la titular - quien reservó)
   - fk_perfil_socio = 5 (El psicólogo)
   - fecha_cita = 2026-02-20
   - tipo_sesion = "PAREJA"

4. Crear registros en 'cita_participantes' (LOOP):
   INSERT INTO cita_participantes (fk_cita, fk_paciente, tipo_participacion)
   VALUES (X, 105, 'TITULAR')
   
   INSERT INTO cita_participantes (fk_cita, fk_paciente, tipo_participacion)
   VALUES (X, 106, 'PAREJA')

5. Actualizar solicitud:
   UPDATE solicitud_cita SET estado = 'CONVERTIDO'

6. Si algún paso falla → ROLLBACK completo
```

**Resultado**:
- ✅ Cita creada con 2 participantes
- ✅ Solicitud marcada como CONVERTIDO
- ✅ El psicólogo ve la cita en su agenda

---

### FLUJO 3: Registro de Historia Clínica (Las Notas)

**Escenario A: Nota Compartida (Terapia de Pareja)**

El psicólogo termina la sesión de Ana y Carlos.

**Endpoint**: `POST /api/historias-clinicas`

**Payload**:
```json
{
  "fkPaciente": null,  // ⚠️ NULL = Nota compartida
  "fkCita": 42,  // ID de la cita de pareja
  "fechaConsulta": "2026-02-20",
  "evolucion": "Se observan patrones de comunicación agresiva. Ana interrumpe constantemente. Carlos se cierra emocionalmente. Se trabajó técnica de escucha activa.",
  "diagnostico": "Patrón de comunicación disfuncional",
  "tratamientoPlan": "4 sesiones de terapia de pareja, enfoque en comunicación no violenta"
}
```

**Lógica**:
```sql
INSERT INTO historia_clinica (
  fk_paciente,  -- NULL (es nota de la cita, no de paciente específico)
  fk_cita,      -- 42
  evolucion,
  ...
)
```

**Consulta de Historial**:
```
GET /api/historias-clinicas/paciente/105/sistemico (Ana)
GET /api/historias-clinicas/paciente/106/sistemico (Carlos)

AMBOS ven la misma nota porque AMBOS participaron en la cita 42
```

---

**Escenario B: Nota Privada (Sesión Individual de Carlos)**

Semanas después, Carlos va solo.

**Payload**:
```json
{
  "fkPaciente": 106,  // ⚠️ Vinculado a Carlos específicamente
  "fkCita": 50,  // Nueva cita individual
  "fechaConsulta": "2026-03-05",
  "evolucion": "Carlos confiesa que tuvo una infidelidad hace 6 meses. Muestra culpa y arrepentimiento. Teme perder a Ana.",
  "notasInternas": "CONFIDENCIAL - No compartir con Ana"
}
```

**Lógica**:
```sql
INSERT INTO historia_clinica (
  fk_paciente,  -- 106 (Carlos)
  fk_cita,      -- 50
  evolucion,
  ...
)
```

**Consulta de Historial**:
```
GET /api/historias-clinicas/paciente/106/sistemico (Carlos)
✅ Ve: Nota de pareja + Nota privada

GET /api/historias-clinicas/paciente/105/sistemico (Ana)
✅ Ve: Solo nota de pareja
❌ NO VE: Nota privada de Carlos (privacidad)
```

---

### FLUJO 4: Query Sistémica (La Magia del Sistema)

**Endpoint**: `GET /api/historias-clinicas/paciente/{pacienteId}/sistemico`

**Query JPQL Interna** (Corazón del Sistema):
```sql
SELECT DISTINCT h FROM HistoriaClinicaEntity h
LEFT JOIN h.cita c
LEFT JOIN CitaParticipanteEntity cp ON cp.cita.id = c.id
WHERE h.paciente.id = :pacienteId        -- Notas directas (privadas)
   OR cp.paciente.id = :pacienteId       -- Notas de citas donde participó (compartidas)
ORDER BY h.fechaConsulta DESC
```

**Explicación**:
1. Busca historias donde `fk_paciente = pacienteId` (notas privadas)
2. Busca historias de citas donde el paciente está en `cita_participantes` (notas compartidas)
3. Hace `DISTINCT` para evitar duplicados
4. Ordena cronológicamente (más recientes primero)

**Ejemplo Visual**:

```
HISTORIAS EN BASE DE DATOS:
┌────┬──────────────┬─────────┬──────────────────────────────┐
│ ID │ fk_paciente  │ fk_cita │ evolucion                    │
├────┼──────────────┼─────────┼──────────────────────────────┤
│  1 │ NULL         │   42    │ "La pareja discutió..."      │ ← Compartida
│  2 │ 106 (Carlos) │   50    │ "Carlos confiesa..."         │ ← Privada
│  3 │ 105 (Ana)    │   55    │ "Ana llora al recordar..."   │ ← Privada
└────┴──────────────┴─────────┴──────────────────────────────┘

CITA_PARTICIPANTES:
┌─────────┬───────────────┐
│ fk_cita │ fk_paciente   │
├─────────┼───────────────┤
│   42    │ 105 (Ana)     │ ← Ana participó en cita 42
│   42    │ 106 (Carlos)  │ ← Carlos participó en cita 42
│   50    │ 106 (Carlos)  │ ← Solo Carlos en cita 50
│   55    │ 105 (Ana)     │ ← Solo Ana en cita 55
└─────────┴───────────────┘

CONSULTA: GET /paciente/105/sistemico (Ana)
RESULTADO:
  - Historia #1 (porque Ana está en participantes de cita 42)
  - Historia #3 (porque fk_paciente = 105)
  ❌ NO ve Historia #2 (privada de Carlos)

CONSULTA: GET /paciente/106/sistemico (Carlos)
RESULTADO:
  - Historia #1 (porque Carlos está en participantes de cita 42)
  - Historia #2 (porque fk_paciente = 106)
  ❌ NO ve Historia #3 (privada de Ana)
```

---

## 📡 API Reference Completa

### 1. Solicitudes de Cita (Leads)

#### Registrar Solicitud (PÚBLICO)
```http
POST /api/clinica/solicitudes-cita/public/{perfilSocioId}
Content-Type: application/json

{
  "nombrePaciente": "Ana Pérez",
  "celular": "77123456",
  "email": "ana@example.com",
  "motivoConsulta": "Busco terapia de pareja",
  "modalidad": "PRESENCIAL"
}
```

#### Obtener Bandeja de Entrada del Psicólogo
```http
GET /api/clinica/solicitudes-cita/psicologo/{perfilSocioId}
```

#### Filtrar por Estado
```http
GET /api/clinica/solicitudes-cita/psicologo/{perfilSocioId}/estado/PENDIENTE
```

#### Contar Pendientes (Badge)
```http
GET /api/clinica/solicitudes-cita/psicologo/{perfilSocioId}/pendientes/count
```

**Response**:
```json
{
  "pendientes": 3
}
```

#### Marcar como Contactado
```http
PATCH /api/clinica/solicitudes-cita/{solicitudId}/contactado
```

#### Actualizar Nota Interna
```http
PATCH /api/clinica/solicitudes-cita/{solicitudId}/nota
Content-Type: application/json

{
  "nota": "Le escribí por WhatsApp y quedamos en hablar el lunes a las 3pm"
}
```

---

### 2. Citas Sistémicas (Múltiples Participantes)

#### Crear Cita con Participantes
```http
POST /api/clinica/citas-sistemicas
Content-Type: application/json

{
  "fkPerfilSocio": 5,
  "idTitular": 105,
  "participantes": [
    {"idPaciente": 105, "tipoParticipacion": "TITULAR"},
    {"idPaciente": 106, "tipoParticipacion": "PAREJA"}
  ],
  "fechaCita": "2026-02-20",
  "horaInicio": "16:00",
  "horaFin": "17:00",
  "tipoSesion": "PAREJA",
  "modalidad": "PRESENCIAL",
  "motivoBreve": "Terapia de pareja",
  "montoAcordado": 200.00
}
```

#### Obtener Participantes de una Cita
```http
GET /api/clinica/citas-sistemicas/{citaId}/participantes
```

**Response**:
```json
[
  {
    "id": 1,
    "pacienteId": 105,
    "nombreCompleto": "Ana Pérez García",
    "tipoParticipacion": "TITULAR"
  },
  {
    "id": 2,
    "pacienteId": 106,
    "nombreCompleto": "Carlos Mendoza López",
    "tipoParticipacion": "PAREJA"
  }
]
```

#### Actualizar Participantes
```http
PUT /api/clinica/citas-sistemicas/{citaId}/participantes
Content-Type: application/json

[
  {"idPaciente": 105, "tipoParticipacion": "TITULAR"},
  {"idPaciente": 106, "tipoParticipacion": "PAREJA"},
  {"idPaciente": 107, "tipoParticipacion": "HIJO"}
]
```

#### Obtener Citas de un Paciente
```http
GET /api/clinica/citas-sistemicas/paciente/{pacienteId}
```

#### Convertir Solicitud en Cita
```http
POST /api/clinica/citas-sistemicas/convertir-solicitud/{solicitudId}
Content-Type: application/json

{
  "fkPerfilSocio": 5,
  "idTitular": 105,
  "participantes": [...],
  "fechaCita": "2026-02-20",
  ...
}
```

---

### 3. Historia Clínica Sistémica

#### Obtener Historial Sistémico de un Paciente (🔥 CRÍTICO)
```http
GET /api/historias-clinicas/paciente/{pacienteId}/sistemico
```

**Response**:
```json
[
  {
    "id": 1,
    "fkPaciente": null,
    "fkCita": 42,
    "fechaConsulta": "2026-02-20",
    "evolucion": "Se observan patrones de comunicación agresiva...",
    "diagnostico": "Patrón de comunicación disfuncional",
    "tratamientoPlan": "4 sesiones de terapia de pareja",
    "fechaCreacion": "2026-02-20T17:15:00"
  },
  {
    "id": 2,
    "fkPaciente": 106,
    "fkCita": 50,
    "fechaConsulta": "2026-03-05",
    "evolucion": "Carlos confiesa que tuvo una infidelidad...",
    "notasInternas": "CONFIDENCIAL",
    "fechaCreacion": "2026-03-05T18:00:00"
  }
]
```

#### Crear Historia Clínica Compartida
```http
POST /api/historias-clinicas
Content-Type: application/json

{
  "fkPaciente": null,  // NULL = Compartida
  "fkCita": 42,
  "fechaConsulta": "2026-02-20",
  "evolucion": "La pareja discutió sobre finanzas...",
  "diagnostico": "Conflicto de pareja - Comunicación",
  "tratamientoPlan": "Terapia de pareja semanal"
}
```

#### Crear Historia Clínica Privada
```http
POST /api/historias-clinicas
Content-Type: application/json

{
  "fkPaciente": 106,  // ID específico = Privada
  "fkCita": 50,
  "fechaConsulta": "2026-03-05",
  "evolucion": "Sesión individual confidencial...",
  "notasInternas": "No compartir con pareja"
}
```

---

## 🛡️ Validaciones y Reglas de Negocio

### 1. Transaccionalidad (@Transactional)
✅ **Creación de cita con participantes es atómica**
- Si falla algún participante → Rollback completo
- O se crea todo, o no se crea nada

### 2. Unicidad de Participantes
✅ **Un paciente no puede estar 2 veces en la misma cita**
- Constraint en BD: `UNIQUE (fk_cita, fk_paciente)`
- Validación en service: `existsByCita_IdAndPaciente_Id()`

### 3. Privacidad de Historias Clínicas
✅ **Las notas privadas NO se comparten**
- Si `fk_paciente ≠ NULL` → Solo ese paciente la ve
- Si `fk_paciente = NULL` → Todos los participantes de la cita la ven

### 4. Estados de Solicitud (Máquina de Estados)
```
PENDIENTE → CONTACTADO → CONVERTIDO
         ↘ DESCARTADO
```
- No se puede marcar como CONVERTIDO si sigue en PENDIENTE
- Cada cambio registra `fecha_actualizacion`

### 5. Tipos de Participación
```
Valores válidos:
- TITULAR: Quien reserva/paga
- PAREJA: En terapia de pareja
- HIJO: Menor de edad en terapia familiar
- PADRE/MADRE: Progenitores
- OBSERVADOR: Asiste pero no participa activamente
- PACIENTE: Rol genérico
```

---

## 📊 Casos de Uso Completos

### Caso 1: Terapia Individual (Flujo Clásico)

**Situación**: Ana solicita terapia individual por depresión

**Pasos**:
1. Ana llena formulario → `POST /solicitudes-cita/public/5`
2. Psicólogo la ve en bandeja → `GET /solicitudes-cita/psicologo/5`
3. Psicólogo la contacta → `PATCH /{id}/contactado`
4. Crean cita individual → `POST /citas-sistemicas` con 1 solo participante [Ana]
5. Psicólogo escribe nota → `POST /historias-clinicas` con `fkPaciente = 105`
6. Ana ve su historial → `GET /historias-clinicas/paciente/105/sistemico`

**Resultado**: Flujo tradicional sin cambios, todo funciona igual

---

### Caso 2: Terapia de Pareja (Sistema Sistémico)

**Situación**: Ana y Carlos tienen problemas de comunicación

**Pasos**:
1. Ana llena formulario → Menciona "quiero venir con mi esposo"
2. Psicólogo contacta → Registra a Carlos como paciente
3. Crean cita de pareja → Con 2 participantes [Ana, Carlos]
4. Sesión #1: Psicólogo escribe nota compartida → `fkPaciente = NULL, fkCita = 42`
5. Ana ve su historial → `GET /paciente/105/sistemico` ✅ Ve la nota
6. Carlos ve su historial → `GET /paciente/106/sistemico` ✅ Ve la MISMA nota

**Luego**: Carlos va solo (sesión individual)
7. Psicólogo escribe nota privada → `fkPaciente = 106, fkCita = 50`
8. Ana ve su historial → ❌ NO ve la nota privada de Carlos
9 Carlos ve su historial → ✅ Ve ambas notas (compartida + privada)

---

### Caso 3: Terapia Familiar (Múltiples Participantes)

**Situación**: Familia López (Padre, Madre, 2 Hijos)

**Participantes**:
```json
[
  {"idPaciente": 201, "tipoParticipacion": "PADRE"},
  {"idPaciente": 202, "tipoParticipacion": "MADRE"},
  {"idPaciente": 203, "tipoParticipacion": "HIJO"},
  {"idPaciente": 204, "tipoParticipacion": "HIJO"}
]
```

**Nota Compartida**:
```json
{
  "fkPaciente": null,
  "fkCita": 100,
  "evolucion": "Dinámica familiar disfuncional. El hijo mayor (203) muestra conductas de rebeldía. Los padres tienen estilos de crianza inconsistentes."
}
```

**Consultas**:
- Padre ve la nota ✅
- Madre ve la nota ✅
- Hijo 1 ve la nota ✅
- Hijo 2 ve la nota ✅

**Sesión Individual del Hijo Mayor**:
```json
{
  "fkPaciente": 203,
  "fkCita": 105,
  "evolucion": "El adolescente confiesa que consume marihuana. Pide confidencialidad."
}
```

**Resultado**:
- Hijo 1 ve su nota privada ✅
- Padres NO ven la nota privada del hijo ❌

---

## 📁 Archivos Implementados

### Entidades
- ✅ [PacienteEntity.java](cadet_backend/src/main/java/app/clinica/entity/PacienteEntity.java)
- ✅ [CitaEntity.java](cadet_backend/src/main/java/app/clinica/entity/CitaEntity.java) - Modificada (campo `monto_acordado`)
- ✅ [CitaParticipanteEntity.java](cadet_backend/src/main/java/app/clinica/entity/CitaParticipanteEntity.java) - **NUEVA TABLA**
- ✅ [HistoriaClinicaEntity.java](cadet_backend/src/main/java/app/clinica/entity/HistoriaClinicaEntity.java) - Modificada (`fk_paciente` nullable)
- ✅ [SolicitudCitaEntity.java](cadet_backend/src/main/java/app/clinica/entity/SolicitudCitaEntity.java) - **NUEVA**

### Repositorios
- ✅ [PacienteRepository.java](cadet_backend/src/main/java/app/clinica/repository/PacienteRepository.java)
- ✅ [CitaRepository.java](cadet_backend/src/main/java/app/clinica/repository/CitaRepository.java)
- ✅ [CitaParticipanteRepository.java](cadet_backend/src/main/java/app/clinica/repository/CitaParticipanteRepository.java) - **NUEVO**
- ✅ [HistoriaClinicaRepository.java](cadet_backend/src/main/java/app/clinica/repository/HistoriaClinicaRepository.java) - Con **Query Sistémica**
- ✅ [SolicitudCitaRepository.java](cadet_backend/src/main/java/app/clinica/repository/SolicitudCitaRepository.java) - **NUEVO**

### Servicios
- ✅ [CitaSistemicaService.java](cadet_backend/src/main/java/app/clinica/service/CitaSistemicaService.java) - **NUEVO**
- ✅ [CitaSistemicaServiceImpl.java](cadet_backend/src/main/java/app/clinica/service/impl/CitaSistemicaServiceImpl.java) - Implementación transaccional
- ✅ [SolicitudCitaService.java](cadet_backend/src/main/java/app/clinica/service/SolicitudCitaService.java) - **NUEVO**
- ✅ [SolicitudCitaServiceImpl.java](cadet_backend/src/main/java/app/clinica/service/impl/SolicitudCitaServiceImpl.java)
- ✅ [HistoriaClinicaService.java](cadet_backend/src/main/java/app/clinica/service/HistoriaClinicaService.java) - Añadido método sistémico
- ✅ [HistoriaClinicaServiceImpl.java](cadet_backend/src/main/java/app/clinica/service/impl/HistoriaClinicaServiceImpl.java) - Implementado

### Controllers
- ✅ [CitaSistemicaController.java](cadet_backend/src/main/java/app/clinica/controller/CitaSistemicaController.java) - **NUEVO**
- ✅ [SolicitudCitaController.java](cadet_backend/src/main/java/app/clinica/controller/SolicitudCitaController.java) - **NUEVO**
- ✅ [HistoriaClinicaController.java](cadet_backend/src/main/java/app/clinica/controller/HistoriaClinicaController.java) - Endpoint sistémico añadido

### DTOs
- ✅ [CitaConParticipantesCreateDTO.java](cadet_backend/src/main/java/app/clinica/dto/CitaConParticipantesCreateDTO.java) - **NUEVO**
- ✅ [SolicitudCitaCreateDTO.java](cadet_backend/src/main/java/app/clinica/dto/SolicitudCitaCreateDTO.java) - **NUEVO**
- ✅ [SolicitudCitaResponseDTO.java](cadet_backend/src/main/java/app/clinica/dto/SolicitudCitaResponseDTO.java) - **NUEVO**
- ✅ [ParticipanteSimpleDTO.java](cadet_backend/src/main/java/app/clinica/dto/ParticipanteSimpleDTO.java) - **NUEVO**

---

## 🎓 Conceptos Clave

### ¿Qué es el Modelo Sistémico?
Un enfoque terapéutico que estudia las relaciones e interacciones entre los miembros de un sistema (pareja, familia, grupo).

**Ejemplo**: En terapia de pareja, el problema NO es Ana ni Carlos individualmente, sino la **relación entre ambos**. Por eso, la nota terapéutica debe ser visible para ambos.

### ¿Por Qué `fk_paciente` es Nullable?
```
Antes (Modelo Individual):
  historia_clinica.fk_paciente = NOT NULL (Siempre vinculada a 1 paciente)

Ahora (Modelo Sistémico):
  historia_clinica.fk_paciente = NULLABLE
  
  - Si NULL: Vinculada a la CITA (visible para todos los participantes)
  - Si tiene ID: Vinculada al PACIENTE (privada)
```

### La Query Sistémica (SQL Conceptual)
```sql
-- Historial de Carlos (ID 106)
SELECT DISTINCT h.*
FROM historia_clinica h
LEFT JOIN cita c ON h.fk_cita = c.id
LEFT JOIN cita_participantes cp ON cp.fk_cita = c.id
WHERE h.fk_paciente = 106           -- Notas directas de Carlos
   OR (cp.fk_paciente = 106         -- Notas de citas donde Carlos participó
       AND h.fk_paciente IS NULL)   -- Y la nota es compartida
ORDER BY h.fecha_consulta DESC;
```

---

## ⚠️ Notas Importantes

1. **Estados de Solicitud**: No borrar solicitudes, solo cambiar estado
2. **Transaccionalidad**: Creación de cita con participantes es atómica
3. **Privacidad**: Las notas con `fk_paciente` específico NO se comparten
4. **Participantes**: No se pueden duplicar en la misma cita (constraint)
5. **Query Sistémica**: Usar siempre `/sistemico` para historial completo

---

## 🚀 Testing

### Test 1: Crear Terapia de Pareja
```bash
# 1. Registrar solicitud
curl -X POST "http://localhost:8080/api/clinica/solicitudes-cita/public/5" \
  -H "Content-Type: application/json" \
  -d '{
    "nombrePaciente": "Ana Pérez",
    "celular": "77123456",
    "email": "ana@example.com",
    "motivoConsulta": "Terapia de pareja",
    "modalidad": "PRESENCIAL"
  }'

# 2. Marcar como contactado
curl -X PATCH "http://localhost:8080/api/clinica/solicitudes-cita/1/contactado"

# 3. Crear cita con 2 participantes
curl -X POST "http://localhost:8080/api/clinica/citas-sistemicas" \
  -H "Content-Type: application/json" \
  -d '{
    "fkPerfilSocio": 5,
    "idTitular": 105,
    "participantes": [
      {"idPaciente": 105, "tipoParticipacion": "TITULAR"},
      {"idPaciente": 106, "tipoParticipacion": "PAREJA"}
    ],
    "fechaCita": "2026-02-20",
    "horaInicio": "16:00",
    "horaFin": "17:00",
    "tipoSesion": "PAREJA",
    "modalidad": "PRESENCIAL",
    "motivoBreve": "Terapia de pareja"
  }'
```

### Test 2: Crear Nota Compartida y Verificar Historial
```bash
# 1. Crear nota compartida
curl -X POST "http://localhost:8080/api/historias-clinicas" \
  -H "Content-Type: application/json" \
  -d '{
    "fkPaciente": null,
    "fkCita": 42,
    "fechaConsulta": "2026-02-20",
    "evolucion": "La pareja discutió sobre finanzas. Se trabajó comunicación asertiva."
  }'

# 2. Ver historial de Ana
curl "http://localhost:8080/api/historias-clinicas/paciente/105/sistemico"

# 3. Ver historial de Carlos
curl "http://localhost:8080/api/historias-clinicas/paciente/106/sistemico"

# Resultado: Ambos ven la misma nota ✅
```

### Test 3: Verificar Privacidad
```bash
# 1. Crear nota privada de Carlos
curl -X POST "http://localhost:8080/api/historias-clinicas" \
  -H "Content-Type: application/json" \
  -d '{
    "fkPaciente": 106,
    "fkCita": 50,
    "fechaConsulta": "2026-03-05",
    "evolucion": "Carlos confiesa una infidelidad. CONFIDENCIAL."
  }'

# 2. Ver historial de Carlos
curl "http://localhost:8080/api/historias-clinicas/paciente/106/sistemico"
# Resultado: Ve ambas notas (compartida + privada) ✅

# 3. Ver historial de Ana
curl "http://localhost:8080/api/historias-clinicas/paciente/105/sistemico"
# Resultado: Solo ve la nota compartida, NO la privada de Carlos ✅
```

---

¡Sistema Clínico Sistémico completo y documentado! 🎉

**Implementación completa con**:
- ✅ 5 Entidades nuevas/modificadas
- ✅ 5 Repositorios con queries especializadas
- ✅ 4 DTOs sistémicos
- ✅ 3 Servicios con lógica transaccional
- ✅ 3 Controladores REST
- ✅ Query sistémica con JOIN complejo
- ✅ Sin errores de compilación
