-- ============================================================================
-- ESQUEMA COMPLETO DE BASE DE DATOS - SISTEMA CADET
-- Versión: 3.0 (Final)
-- Fecha: 2026-01-14
-- Descripción: Incluye Core, Perfiles, OAuth2, CMS (Noticias/Eventos) y Documentos
-- ============================================================================

-- ============================================================================
-- 1. TABLAS BASE GEOGRÁFICAS Y CATÁLOGOS GLOBALES
-- ============================================================================

CREATE TABLE pais (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE departamento (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL,
    abreviacion VARCHAR(10),
    fk_pais INTEGER,
    CONSTRAINT fk_departamento_pais FOREIGN KEY (fk_pais) REFERENCES pais(id) ON DELETE SET NULL
);

CREATE TABLE provincia (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL,
    fk_departamento INTEGER,
    CONSTRAINT fk_provincia_departamento FOREIGN KEY (fk_departamento) REFERENCES departamento(id) ON DELETE SET NULL
);

CREATE TABLE profesion (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL
);

-- ============================================================================
-- 2. INSTITUCIONES Y AÑOS
-- ============================================================================

CREATE TABLE institucion (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    compania VARCHAR(255),
    correo VARCHAR(255),
    direccion VARCHAR(500),
    fax VARCHAR(50),
    host VARCHAR(255),
    nit VARCHAR(50),
    port VARCHAR(10),
    representante VARCHAR(255),
    telefono VARCHAR(50),
    fk_provincia INTEGER,
    CONSTRAINT fk_institucion_provincia FOREIGN KEY (fk_provincia) REFERENCES provincia(id) ON DELETE SET NULL
);

CREATE TABLE anio (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(50),
    fk_institucion INTEGER,
    CONSTRAINT fk_anio_institucion FOREIGN KEY (fk_institucion) REFERENCES institucion(id) ON DELETE SET NULL
);

-- ============================================================================
-- 3. NÚCLEO DE USUARIOS Y SOCIOS
-- ============================================================================

CREATE TABLE persona (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    celular VARCHAR(50),
    ci VARCHAR(50),
    email VARCHAR(255),
    nombrecompleto VARCHAR(255),
    fk_departamento INTEGER,
    CONSTRAINT fk_persona_departamento FOREIGN KEY (fk_departamento) REFERENCES departamento(id) ON DELETE SET NULL
);

CREATE TABLE rol (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    password VARCHAR(500),
    username VARCHAR(100) UNIQUE,
    fk_persona INTEGER,
    CONSTRAINT fk_usuario_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE
);

CREATE TABLE usuarios_roles (
    usuario_id INTEGER NOT NULL,
    rol_id INTEGER NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_usuarios_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuarios_roles_rol FOREIGN KEY (rol_id) REFERENCES rol(id) ON DELETE CASCADE
);

CREATE TABLE socio (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    codigo VARCHAR(100),
    fechaemision DATE,
    fechaexpiracion DATE,
    imagen VARCHAR(500), -- Foto carnet para credencial
    lejendario VARCHAR(100),
    linkqr VARCHAR(500),
    matricula VARCHAR(100),
    ntodocumento VARCHAR(100),
    nombresocio VARCHAR(255),
    qr VARCHAR(500),
    fk_institucion INTEGER,
    fk_persona INTEGER,
    fk_profesion INTEGER,
    CONSTRAINT fk_socio_institucion FOREIGN KEY (fk_institucion) REFERENCES institucion(id) ON DELETE SET NULL,
    CONSTRAINT fk_socio_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_profesion FOREIGN KEY (fk_profesion) REFERENCES profesion(id) ON DELETE SET NULL
);

-- ============================================================================
-- 4. CATÁLOGOS EXTERNOS (EMPRESAS)
-- ============================================================================

CREATE TABLE catalogo (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    codigo VARCHAR(100),
    descripcion TEXT,
    direccion VARCHAR(500),
    dominio VARCHAR(255),
    latitud NUMERIC(10, 8),
    longitud NUMERIC(11, 8),
    nit VARCHAR(50),
    nombre VARCHAR(255),
    nombrelogo VARCHAR(255),
    tipo VARCHAR(100),
    fk_departamento INTEGER,
    fk_pais INTEGER,
    fk_provincia INTEGER,
    CONSTRAINT fk_catalogo_departamento FOREIGN KEY (fk_departamento) REFERENCES departamento(id) ON DELETE SET NULL,
    CONSTRAINT fk_catalogo_pais FOREIGN KEY (fk_pais) REFERENCES pais(id) ON DELETE SET NULL,
    CONSTRAINT fk_catalogo_provincia FOREIGN KEY (fk_provincia) REFERENCES provincia(id) ON DELETE SET NULL
);

CREATE TABLE imagencatalogo (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    codigo VARCHAR(100),
    imagen VARCHAR(500),
    fk_catalogo INTEGER,
    CONSTRAINT fk_imagencatalogo_catalogo FOREIGN KEY (fk_catalogo) REFERENCES catalogo(id) ON DELETE CASCADE
);

CREATE TABLE socio_catalogos (
    socio_id INTEGER NOT NULL,
    catalogo_id INTEGER NOT NULL,
    PRIMARY KEY (socio_id, catalogo_id),
    CONSTRAINT fk_socio_catalogos_socio FOREIGN KEY (socio_id) REFERENCES socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_catalogos_catalogo FOREIGN KEY (catalogo_id) REFERENCES catalogo(id) ON DELETE CASCADE
);

-- ============================================================================
-- 5. PERFILES PÚBLICOS
-- ============================================================================

CREATE TABLE perfil_socio (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL UNIQUE,
    titulo_profesional VARCHAR(100),
    especialidad VARCHAR(200),
    anos_experiencia INTEGER,
    resumen_profesional TEXT,
    modalidad_trabajo VARCHAR(20),
    ciudad VARCHAR(100),
    zona VARCHAR(200),
    foto_perfil VARCHAR(500),
    foto_banner VARCHAR(500),
    linkedin_url VARCHAR(300),
    facebook_url VARCHAR(300),
    twitter_url VARCHAR(300),
    instagram_url VARCHAR(300),
    whatsapp VARCHAR(20),
    sitio_web VARCHAR(300),
    perfil_publico BOOLEAN DEFAULT true,
    permite_contacto BOOLEAN DEFAULT true,
    visualizaciones INTEGER DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado INTEGER DEFAULT 1,
    CONSTRAINT fk_perfil_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);

-- ============================================================================
-- 6. CATÁLOGOS EXTENSIBLES (Estrategia 1: SISTEMA/USUARIO)
-- ============================================================================

CREATE TABLE especialidades (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    descripcion TEXT,
    origen VARCHAR(20) DEFAULT 'SISTEMA',
    orden INTEGER DEFAULT 0,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE socio_especialidades (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_especialidad INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_socio_esp_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_esp_especialidad FOREIGN KEY (fk_especialidad) REFERENCES especialidades(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_especialidad UNIQUE (fk_perfil_socio, fk_especialidad)
);

CREATE TABLE servicios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(100),
    origen VARCHAR(20) DEFAULT 'SISTEMA',
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE socio_servicios (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_servicio INTEGER NOT NULL,
    destacado BOOLEAN DEFAULT false,
    orden INTEGER DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_socio_serv_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_serv_servicio FOREIGN KEY (fk_servicio) REFERENCES servicios(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_servicio UNIQUE (fk_perfil_socio, fk_servicio)
);

CREATE TABLE sectores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    descripcion TEXT,
    icono VARCHAR(100),
    origen VARCHAR(20) DEFAULT 'SISTEMA',
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE socio_sectores (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_sector INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_socio_sec_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_sec_sector FOREIGN KEY (fk_sector) REFERENCES sectores(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_sector UNIQUE (fk_perfil_socio, fk_sector)
);

-- ============================================================================
-- 7. DETALLES DE FORMACIÓN (Hijos de Perfil)
-- ============================================================================

CREATE TABLE formacion (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    institucion VARCHAR(300),
    pais VARCHAR(100),
    fecha_inicio DATE,
    fecha_fin DATE,
    en_curso BOOLEAN DEFAULT false,
    descripcion TEXT,
    orden INTEGER DEFAULT 0,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_formacion_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
);

CREATE TABLE certificaciones (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    nombre VARCHAR(300) NOT NULL,
    institucion_emisora VARCHAR(300),
    url_verificacion VARCHAR(500),
    orden INTEGER DEFAULT 0,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_certificacion_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
);

CREATE TABLE idiomas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE socio_idiomas (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_idioma INTEGER NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    orden INTEGER DEFAULT 0,
    CONSTRAINT fk_socio_idioma_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_idioma_idioma FOREIGN KEY (fk_idioma) REFERENCES idiomas(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_idioma UNIQUE (fk_perfil_socio, fk_idioma)
);

-- ============================================================================
-- 8. GESTIÓN DOCUMENTAL (NUEVO)
-- ============================================================================

CREATE TABLE documentos (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL,
    
    nombre VARCHAR(255) NOT NULL,       -- Ej: "Título Profesional", "Factura Enero"
    tipo VARCHAR(50),                   -- 'requisito', 'certificado', 'factura', 'otro'
    
    archivo_url VARCHAR(500) NOT NULL,  -- URL del PDF/Imagen
    mime_type VARCHAR(100),             -- 'application/pdf', 'image/jpeg'
    
    estado INTEGER DEFAULT 1,           -- 1: Activo, 2: En revisión
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_documentos_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);
CREATE INDEX idx_documentos_socio ON documentos(fk_socio);

-- ============================================================================
-- 9. MÓDULO DE PUBLICACIONES / CMS (NUEVO)
-- ============================================================================

CREATE TABLE publicaciones (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    slug VARCHAR(300) NOT NULL UNIQUE,  -- URL amigable
    resumen TEXT,
    contenido TEXT,                     -- Texto largo HTML/Markdown
    
    tipo VARCHAR(50) DEFAULT 'noticia', -- 'noticia', 'evento'
    fecha_evento TIMESTAMP,             -- Solo si es evento, fecha y hora del mismo
    
    publicado BOOLEAN DEFAULT true,
    fk_autor INTEGER,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_publicacion_autor FOREIGN KEY (fk_autor) REFERENCES usuario(id) ON DELETE SET NULL
);
CREATE INDEX idx_publicaciones_tipo ON publicaciones(tipo);

-- Tabla para múltiples imágenes por publicación (Galería)
CREATE TABLE publicacion_imagenes (
    id SERIAL PRIMARY KEY,
    fk_publicacion INTEGER NOT NULL,
    
    imagen_url VARCHAR(500) NOT NULL,
    
    es_portada BOOLEAN DEFAULT false,   -- true para la imagen principal
    orden INTEGER DEFAULT 0,            -- Para ordenar la galería
    
    CONSTRAINT fk_pub_img_publicacion FOREIGN KEY (fk_publicacion) REFERENCES publicaciones(id) ON DELETE CASCADE
);
CREATE INDEX idx_pub_img_publicacion ON publicacion_imagenes(fk_publicacion);


-- ============================================================================
-- 10. FINANZAS (NUEVO - PARA DASHBOARD)
-- ============================================================================

CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL,
    monto NUMERIC(10, 2) NOT NULL,
    moneda VARCHAR(10) DEFAULT 'BOB',
    concepto VARCHAR(255),
    metodo_pago VARCHAR(50),
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'aprobado',
    factura_url VARCHAR(500),
    CONSTRAINT fk_pagos_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);

-- ============================================================================
-- 11. AUTENTICACIÓN Y LANDING
-- ============================================================================

CREATE TABLE usuario_social (
    id SERIAL PRIMARY KEY,
    provider VARCHAR(20) NOT NULL DEFAULT 'GOOGLE',
    provider_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(255),
    foto_perfil_url VARCHAR(500),
    fk_persona INTEGER NOT NULL,
    access_token TEXT,
    refresh_token TEXT,
    token_expires_at TIMESTAMP,
    email_verificado BOOLEAN DEFAULT true,
    fecha_vinculacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_autenticacion TIMESTAMP,
    estado INTEGER DEFAULT 1,
    CONSTRAINT fk_usuario_social_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE,
    CONSTRAINT uk_provider_id UNIQUE (provider, provider_id)
);

CREATE TABLE consultas_contacto (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    telefono VARCHAR(50),
    empresa VARCHAR(255),
    modalidad_preferida VARCHAR(20),
    horario_preferido VARCHAR(100),
    motivo_reunion TEXT NOT NULL,
    detalles_adicionales TEXT,
    estado VARCHAR(20) DEFAULT 'pendiente',
    fecha_contacto TIMESTAMP,
    notas_internas TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_consulta_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
);

CREATE TABLE estadisticas_publicas (
    id SERIAL PRIMARY KEY,
    clave VARCHAR(100) NOT NULL UNIQUE,
    titulo VARCHAR(100) NOT NULL, -- Agregado título visual
    valor VARCHAR(500),
    icono VARCHAR(100),           -- Agregado icono
    descripcion TEXT,
    orden INTEGER DEFAULT 0,
    visible BOOLEAN DEFAULT true,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado INTEGER DEFAULT 1
);