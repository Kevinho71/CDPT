# Endpoints de Ubicaciones Geográficas

## Base URL
`/api/locations`

---

## 1. Obtener Todos los Datos (Recomendado)
**Endpoint:** `GET /api/locations/all`

Retorna países, departamentos y provincias en una sola respuesta.

**Respuesta:**
```json
{
  "paises": [
    {"id": 1, "nombre": "Bolivia", "estado": 1}
  ],
  "departamentos": [
    {"id": 1, "nombre": "La Paz", "abreviacion": "LP", "estado": 1, "paisId": 1}
  ],
  "provincias": [
    {"id": 1, "nombre": "Murillo", "estado": 1, "departamentoId": 1}
  ]
}
```

---

## 2. Solo Países
**Endpoint:** `GET /api/locations/countries`

Retorna lista de países activos.

---

## 3. Departamentos (con filtro opcional)
**Endpoint:** `GET /api/locations/departments?paisId=1`

- Sin parámetro: todos los departamentos activos
- Con `paisId`: departamentos de ese país

---

## 4. Provincias (con filtro opcional)
**Endpoint:** `GET /api/locations/provinces?departamentoId=1`

- Sin parámetro: todas las provincias activas
- Con `departamentoId`: provincias de ese departamento

---

## Uso para Formularios de Catálogo

**Campos en CatalogoDTO:**
- `departamento` (Integer) - ID del departamento
- `pais` (Integer) - ID del país
- `provincia` (Integer) - ID de la provincia

**Flujo recomendado:**
1. Al cargar formulario: `GET /api/locations/all`
2. Poblar los 3 selectores con la data recibida
3. Al cambiar país → filtrar departamentos localmente usando `paisId`
4. Al cambiar departamento → filtrar provincias localmente usando `departamentoId`
