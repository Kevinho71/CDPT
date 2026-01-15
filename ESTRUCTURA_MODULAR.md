# Estructura Modular del Proyecto CADET

## Arquitectura General

El proyecto está organizado en **módulos funcionales**, cada uno siguiendo la **arquitectura por capas**:

```
app/
├── modules/                    # Módulos funcionales del sistema
│   ├── core/                   # Módulo base (geografía, instituciones)
│   │   ├── entity/            # Entidades JPA
│   │   ├── repository/        # Repositorios Spring Data
│   │   ├── service/           # Lógica de negocio
│   │   ├── dto/               # Objetos de transferencia
│   │   └── controller/        # Controladores web
│   │
│   ├── auth/                   # Autenticación y autorización
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   │
│   ├── socio/                  # Gestión de socios
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   │
│   ├── perfil/                 # Perfiles públicos de socios
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   │
│   ├── catalogo/               # Catálogo de empresas
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   │
│   ├── publicacion/            # CMS (Noticias/Eventos)
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   │
│   ├── documento/              # Gestión documental
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   │
│   └── finanza/                # Pagos y finanzas
│       ├── entity/
│       ├── repository/
│       ├── service/
│       ├── dto/
│       └── controller/
│
├── common/                     # Código compartido
│   ├── config/                # Configuraciones globales
│   ├── util/                  # Utilidades
│   ├── exception/             # Excepciones personalizadas
│   └── security/              # Configuración de seguridad
│
└── CadetappApplication.java   # Clase principal

```

## Módulos del Sistema

### 1. **core** - Módulo Base
Entidades fundamentales del sistema:
- **Geografía**: PaisEntity, DepartamentoEntity, ProvinciaEntity
- **Instituciones**: InstitucionEntity, AnioEntity
- **Personas**: PersonaEntity
- **Profesiones**: ProfesionEntity

### 2. **auth** - Autenticación
Gestión de usuarios y roles:
- UsuarioEntity
- RolEntity
- UsuarioSocialEntity (OAuth2)
- Relación usuarios_roles

### 3. **socio** - Gestión de Socios
Funcionalidades principales:
- SocioEntity
- Registro y actualización de socios
- Generación de credenciales
- Códigos QR

### 4. **perfil** - Perfiles Públicos
Información pública de socios:
- PerfilSocioEntity
- EspecialidadEntity
- ServicioEntity
- SectorEntity
- FormacionEntity
- CertificacionEntity
- IdiomaEntity
- ConsultaContactoEntity

### 5. **catalogo** - Empresas Asociadas
Catálogo de empresas:
- CatalogoEntity
- ImagenesCatalogoEntity
- Relación socio_catalogos

### 6. **publicacion** - CMS
Noticias y eventos:
- PublicacionEntity
- PublicacionImagenEntity

### 7. **documento** - Gestión Documental
Archivos de socios:
- DocumentoEntity

### 8. **finanza** - Pagos
Control financiero:
- PagoEntity

### 9. **common** - Compartido
Código reutilizable:
- Configuraciones (SecurityConfig, etc.)
- Utilidades (QRCodeGenerator, ArchivoService, etc.)
- Excepciones personalizadas
- DTOs genéricos

## Arquitectura por Capas

Cada módulo implementa estas capas:

### 1. **Entity** (Capa de Persistencia)
- Entidades JPA mapeadas a tablas de base de datos
- Anotaciones: `@Entity`, `@Table`, `@Column`, etc.
- Relaciones: `@OneToMany`, `@ManyToOne`, `@ManyToMany`

### 2. **Repository** (Capa de Acceso a Datos)
- Interfaces que extienden `JpaRepository` o custom repositories
- Consultas personalizadas con `@Query`
- Métodos de búsqueda derivados

### 3. **Service** (Capa de Lógica de Negocio)
- Interfaces de servicio y sus implementaciones
- Lógica de negocio, validaciones, transformaciones
- Anotación: `@Service`
- Transacciones: `@Transactional`

### 4. **DTO** (Data Transfer Objects)
- Objetos para transferencia de datos
- Evitan exponer entidades directamente
- Validaciones: `@Valid`, `@NotNull`, etc.

### 5. **Controller** (Capa de Presentación)
- **RestController**: APIs REST (`@RestController`)
- **Controller**: Vistas Thymeleaf (`@Controller`)
- Mapeo de rutas: `@GetMapping`, `@PostMapping`, etc.

## Ventajas de esta Estructura

1. **Modularidad**: Cada módulo es independiente y cohesivo
2. **Mantenibilidad**: Fácil localización y modificación de código
3. **Escalabilidad**: Agregar nuevos módulos sin afectar existentes
4. **Reutilización**: Código común en el módulo `common`
5. **Testeo**: Pruebas aisladas por módulo y capa
6. **Separación de responsabilidades**: Cada capa tiene su propósito específico

## Próximos Pasos

- [ ] Completar entidades faltantes (Especialidad, Servicio, Sector, etc.)
- [ ] Crear repositorios para todas las entidades
- [ ] Implementar servicios con lógica de negocio
- [ ] Desarrollar controladores REST para cada módulo
- [ ] Agregar DTOs para operaciones CRUD
- [ ] Implementar pruebas unitarias por módulo
