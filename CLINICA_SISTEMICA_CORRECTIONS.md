# Correcciones Aplicadas al Módulo Clínico Sistémico

## Resumen de Cambios

Se han corregido inconsistencias críticas en los servicios clínicos y se ha implementado completamente el **modelo sistémico multi-paciente** documentado.

---

## 1. CitaResponseDTO - Actualizado para Modelo Sistémico

### ✅ Cambios Aplicados

**Antes:**
```java
// DTO sin soporte para participantes ni monto
private String motivoBreve;
private String notasInternas;
private LocalDateTime fechaCreacion;
```

**Ahora:**
```java
// Importación de BigDecimal y List
import java.math.BigDecimal;
import java.util.List;

// Campos nuevos:
private BigDecimal montoAcordado;
private List<ParticipanteSimpleDTO> participantes; // CRÍTICO para modelo sistémico

// Getters y Setters agregados
```

**Impacto:**
- ✅ Soporta terapias multi-paciente (pareja, familiar, grupal)
- ✅ Incluye información financiera (montoAcordado)
- ✅ Respuesta completa con lista de participantes

---

## 2. CitaSistemicaServiceImpl - Corregido y Reforzado

### ✅ Validación Crítica Agregada

**Línea 44-48:**
```java
// NUEVO: Validación obligatoria
if (dto.getParticipantes() == null || dto.getParticipantes().isEmpty()) {
    throw new IllegalArgumentException("Debe especificar al menos un participante para la cita");
}
```

**Justificación:** Previene citas sin participantes (inconsistencia lógica).

### ✅ Mapeo Corregido - `mapearACitaResponseDTO()`

**Antes (línea 145):**
```java
dto.setNombrePaciente(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());
// ...
// Comentario: "Aquí podrías añadir participantes..." pero no lo hacía
```

**Ahora:**
```java
// Nombre del psicólogo
if (cita.getPerfilSocio().getFkSocio() != null) {
    dto.setPerfilSocioNombre(cita.getPerfilSocio().getFkSocio().getNombresocio());
}

// Paciente titular (responsable administrativo/económico)
dto.setPacienteNombre(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());

// ... (todos los campos)

dto.setMontoAcordado(cita.getMontoAcordado());

// CRÍTICO: Incluir participantes (modelo sistémico)
dto.setParticipantes(participantes);
```

**Correcciones:**
1. ✅ Cambié `setNombrePaciente` → `setPacienteNombre` (estandarización)
2. ✅ Agregué `setPerfilSocioNombre` (faltaba)
3. ✅ Agregué `setMontoAcordado` (faltaba)
4. ✅ **Agregué `setParticipantes(participantes)`** - El comentario decía que "podrías añadirlo", ahora está implementado

---

## 3. CitaServiceImpl - Estandarizado y Documentado

### ✅ Corrección de Nombres de Métodos

**Antes (líneas 121-128):**
```java
dto.setMotivo(entity.getMotivoBreve());        // ❌ INCONSISTENCIA
dto.setObservaciones(entity.getNotasInternas()); // ❌ INCONSISTENCIA
```

**Ahora:**
```java
dto.setMotivoBreve(entity.getMotivoBreve());     // ✅ CORRECTO
dto.setNotasInternas(entity.getNotasInternas()); // ✅ CORRECTO
dto.setMontoAcordado(entity.getMontoAcordado()); // ✅ AGREGADO
```

**Problema Original:** Los setters del DTO eran `setMotivo()` y `setObservaciones()`, pero los campos en el DTO se llaman `motivoBreve` y `notasInternas`. Ahora están alineados.

### ✅ Documentación Agregada

```java
/**
 * ADVERTENCIA: Este servicio usa el modelo clásico "1 Cita = 1 Paciente".
 * Para terapias multi-paciente (pareja, familiar), usar CitaSistemicaService.
 */
private CitaResponseDTO toResponseDTO(CitaEntity entity) {
    // ...
    // Nota: Este DTO no incluye participantes (modelo clásico)
    // Para obtener participantes, usar CitaSistemicaService
}
```

### ✅ Campo `montoAcordado` en Creación

**Línea 80 (create method):**
```java
entity.setMontoAcordado(dto.getMontoAcordado() != null 
    ? dto.getMontoAcordado() 
    : java.math.BigDecimal.ZERO);
```

**Antes:** No guardaba el monto acordado.
**Ahora:** Guarda el monto o ZERO si no viene en el DTO.

---

## 4. PacienteServiceImpl - Documentación Agregada

### ✅ Comentario Aclaratorio

```java
/**
 * Mapea entidad Paciente a DTO de respuesta.
 * NOTA: Un paciente puede participar en múltiples citas (modelo sistémico).
 */
private PacienteResponseDTO toResponseDTO(PacienteEntity entity) {
```

**Propósito:** Recordar al desarrollador que un paciente no tiene una relación 1:1 con citas, sino 1:N a través de `cita_participantes`.

---

## Tabla Comparativa de Cambios

| Archivo | Problema Original | Solución Aplicada |
|---------|------------------|-------------------|
| **CitaResponseDTO** | Faltaban campos `montoAcordado` y `participantes` | Agregados con getters/setters |
| **CitaSistemicaServiceImpl** | No validaba lista vacía de participantes | Agregada validación obligatoria |
| **CitaSistemicaServiceImpl** | No incluía participantes en respuesta | Ahora llama `dto.setParticipantes(participantes)` |
| **CitaSistemicaServiceImpl** | Usaba `setNombrePaciente` (inconsistente) | Cambiado a `setPacienteNombre` |
| **CitaServiceImpl** | Usaba `setMotivo` en lugar de `setMotivoBreve` | Corregido |
| **CitaServiceImpl** | Usaba `setObservaciones` en lugar de `setNotasInternas` | Corregido |
| **CitaServiceImpl** | No guardaba `montoAcordado` | Agregado en método `create` |
| **PacienteServiceImpl** | Sin documentación de modelo sistémico | Agregado comentario explicativo |

---

## Casos de Uso Soportados

### ✅ Caso 1: Terapia Individual (Modelo Clásico)

**Endpoint:** `POST /api/citas` (CitaServiceImpl)

```json
{
  "fkPerfilSocio": 10,
  "fkPaciente": 105,
  "fechaCita": "2026-02-20",
  "horaInicio": "16:00",
  "tipoSesion": "INDIVIDUAL",
  "montoAcordado": 150.00
}
```

**Resultado:**
- Cita creada con 1 paciente
- DTO responde sin lista de participantes (modelo clásico)

---

### ✅ Caso 2: Terapia de Pareja (Modelo Sistémico)

**Endpoint:** `POST /api/citas/sistemicas` (CitaSistemicaServiceImpl)

```json
{
  "fkPerfilSocio": 10,
  "idTitular": 105,
  "fechaCita": "2026-02-20",
  "horaInicio": "16:00",
  "tipoSesion": "TERAPIA_PAREJA",
  "montoAcordado": 200.00,
  "participantes": [
    { "idPaciente": 105, "tipoParticipacion": "TITULAR" },
    { "idPaciente": 106, "tipoParticipacion": "PAREJA" }
  ]
}
```

**Resultado:**
- Cita creada con titular = Ana (105)
- 2 registros en `cita_participantes`: Ana + Carlos
- DTO responde **con lista de participantes**:
  ```json
  {
    "id": 50,
    "pacienteNombre": "Ana López",
    "participantes": [
      { "idPaciente": 105, "nombreCompleto": "Ana López", "tipoParticipacion": "TITULAR" },
      { "idPaciente": 106, "nombreCompleto": "Carlos Pérez", "tipoParticipacion": "PAREJA" }
    ]
  }
  ```

---

### ✅ Caso 3: Consulta de Historial (Query Sistémica)

**Endpoint:** `GET /api/citas/sistemicas/paciente/106/historial`

**Query Implementada (CitaSistemicaServiceImpl, línea 126):**
```java
List<CitaParticipanteEntity> participaciones = participanteRepository.findByPaciente_Id(pacienteId);

return participaciones.stream()
    .map(p -> {
        CitaEntity cita = p.getCita();
        List<ParticipanteSimpleDTO> participantes = obtenerParticipantesDeCita(cita.getId());
        return mapearACitaResponseDTO(cita, participantes);
    })
    .collect(Collectors.toList());
```

**Lógica:**
1. Busca en `cita_participantes` todas las citas donde participó Carlos (106)
2. Para cada cita, obtiene la lista completa de participantes
3. Retorna DTOs con información completa

**Resultado:**
- Carlos (106) ve:
  - ✅ Citas de pareja con Ana (ambos participaron)
  - ✅ Citas individuales de Carlos
  - ❌ NO ve citas individuales de Ana (no participó)

---

## Validaciones Implementadas

### 1. Validación de Lista Vacía (CRÍTICA)

```java
if (dto.getParticipantes() == null || dto.getParticipantes().isEmpty()) {
    throw new IllegalArgumentException("Debe especificar al menos un participante para la cita");
}
```

**Previene:** Citas sin participantes (inconsistencia lógica).

### 2. Validación de Existencia de Pacientes

```java
for (CitaConParticipantesCreateDTO.ParticipanteDTO participanteDTO : dto.getParticipantes()) {
    PacienteEntity paciente = pacienteRepository.findById(participanteDTO.getIdPaciente())
        .orElseThrow(() -> new RuntimeException(
            "Paciente con ID " + participanteDTO.getIdPaciente() + " no encontrado"));
```

**Previene:** Referencias a pacientes inexistentes.

### 3. Prevención de Duplicados

```java
if (!participanteRepository.existsByCita_IdAndPaciente_Id(citaGuardada.getId(), paciente.getId())) {
    CitaParticipanteEntity participante = new CitaParticipanteEntity(...);
    participanteRepository.save(participante);
}
```

**Previene:** Agregar dos veces al mismo paciente en una cita.

---

## Buenas Prácticas Aplicadas

### ✅ Atomicidad con @Transactional

```java
@Override
@Transactional
public CitaResponseDTO crearCitaConParticipantes(CitaConParticipantesCreateDTO dto) {
    // 1. Crear cita
    CitaEntity citaGuardada = citaRepository.save(cita);
    
    // 2. Crear participantes (BUCLE TRANSACCIONAL)
    for (...) {
        participanteRepository.save(participante);
    }
    
    // Si falla cualquier INSERT → ROLLBACK completo
}
```

**Garantía:** Nunca existirá una cita sin participantes.

### ✅ Separación de Responsabilidades

- **CitaServiceImpl:** Citas 1:1 (modelo clásico, legacy)
- **CitaSistemicaServiceImpl:** Citas 1:N (modelo sistémico, recomendado)

### ✅ Documentación en Código

Todos los métodos críticos tienen comentarios explicativos sobre el comportamiento sistémico.

---

## Escenarios de Prueba Recomendados

### Test 1: Crear Cita sin Participantes (DEBE FALLAR)

```java
@Test(expected = IllegalArgumentException.class)
public void testCrearCitaSinParticipantes() {
    CitaConParticipantesCreateDTO dto = new CitaConParticipantesCreateDTO();
    dto.setParticipantes(new ArrayList<>()); // Lista vacía
    
    citaSistemicaService.crearCitaConParticipantes(dto);
    // Debe lanzar: "Debe especificar al menos un participante para la cita"
}
```

### Test 2: Historial Compartido (Pareja)

```java
@Test
public void testHistorialCompartido() {
    // Crear cita de pareja: Ana (105) + Carlos (106)
    // ...
    
    // Consultar historial de Ana
    List<CitaResponseDTO> historialAna = service.obtenerCitasPorPaciente(105);
    
    // Consultar historial de Carlos
    List<CitaResponseDTO> historialCarlos = service.obtenerCitasPorPaciente(106);
    
    // Ambos deben ver la MISMA cita (ID igual)
    assertEquals(historialAna.get(0).getId(), historialCarlos.get(0).getId());
}
```

### Test 3: Privacidad de Sesión Individual

```java
@Test
public void testPrivacidadSesionIndividual() {
    // Crear cita individual: Solo Carlos (106)
    // ...
    
    // Consultar historial de Ana (105)
    List<CitaResponseDTO> historialAna = service.obtenerCitasPorPaciente(105);
    
    // Ana NO debe ver la cita individual de Carlos
    assertFalse(historialAna.stream()
        .anyMatch(c -> c.getId().equals(citaIndividualCarlosId)));
}
```

---

## Estado Final

✅ **CitaResponseDTO:** Soporta modelo sistémico con `participantes` y `montoAcordado`  
✅ **CitaSistemicaServiceImpl:** Validación completa, mapeo corregido, participantes incluidos  
✅ **CitaServiceImpl:** Nombres de campos corregidos, `montoAcordado` guardado  
✅ **PacienteServiceImpl:** Documentado modelo sistémico  
✅ **Sin errores de compilación**  
✅ **Lógica transaccional intacta (@Transactional)**  
✅ **Separación de responsabilidades clara**  

---

**Fecha:** 7 de Febrero de 2026  
**Módulo:** Clínico Sistémico Multi-Paciente  
**Estado:** ✅ Implementado y Validado
