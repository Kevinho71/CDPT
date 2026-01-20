# Guía de Integración de Códigos QR con Cloudinary

## 📋 Descripción

Esta guía explica cómo integrar la generación y almacenamiento de códigos QR de socios en Cloudinary usando la carpeta **SOCIO_QR**.

## 🎯 Ventajas de Almacenar QR en Cloudinary

1. **Persistencia**: Los QR se mantienen disponibles sin depender del sistema de archivos local
2. **CDN Global**: Acceso rápido desde cualquier ubicación geográfica
3. **Gestión Automática**: Eliminación automática de QR antiguos al regenerar
4. **Transformaciones**: Posibilidad de aplicar transformaciones (tamaño, formato, etc.)
5. **URLs Estables**: URLs permanentes para los códigos QR

## 📁 Organización

Los códigos QR se almacenan en la carpeta: **`SOCIO_QR`**

Formato del public_id: `SOCIO_QR/socio_{id}_qr_{timestamp}`

Ejemplo: `SOCIO_QR/socio_123_qr_1642345678901`

## 🔧 Implementación

### Paso 1: Generar el Código QR

```java
@Autowired
private QRCodeGeneratorService qrCodeGeneratorService;

// Generar QR con información del socio (URL, JSON, texto, etc.)
String qrContent = "https://cadet.com/verificar-socio/" + socioId;
byte[] qrImageBytes = qrCodeGeneratorService.generateQRCodeImage(qrContent, 300, 300);
```

### Paso 2: Convertir a MultipartFile

```java
// Crear MultipartFile desde bytes
MultipartFile qrFile = new MultipartFileFromBytes(
    qrImageBytes, 
    "qr_socio_" + socioId + ".png",
    "image/png"
);
```

### Paso 3: Subir a Cloudinary

```java
@Autowired
private SocioImagenService socioImagenService;

// Subir QR a Cloudinary (elimina el anterior automáticamente)
String newPublicId = socioImagenService.actualizarQR(
    socioId,
    socio.getQr(),  // Public ID del QR anterior (puede ser null)
    qrFile
);
```

### Paso 4: Actualizar el Socio

```java
// Guardar el public_id en la base de datos
socio.setQr(newPublicId);

// Opcionalmente, guardar también la URL de Cloudinary
String cloudinaryUrl = "https://res.cloudinary.com/" + cloudName + "/image/upload/" + newPublicId + ".png";
socio.setLinkqr(cloudinaryUrl);

socioRepository.save(socio);
```

## 💡 Casos de Uso

### Caso 1: Generar QR al Crear Socio

```java
@Service
public class SocioServiceImpl {
    
    @Autowired
    private SocioImagenService socioImagenService;
    
    @Autowired
    private QRCodeGeneratorService qrCodeGeneratorService;
    
    @Transactional
    public SocioEntity crearSocio(SocioDTO dto) {
        // 1. Crear el socio
        SocioEntity socio = new SocioEntity();
        // ... setear campos del socio
        socio = socioRepository.save(socio);
        
        // 2. Generar QR con información del socio
        String qrContent = generarContenidoQR(socio);
        byte[] qrBytes = qrCodeGeneratorService.generateQRCodeImage(qrContent, 300, 300);
        
        // 3. Convertir a MultipartFile
        MultipartFile qrFile = convertirBytesAMultipartFile(qrBytes, socio.getId());
        
        // 4. Subir a Cloudinary
        String publicId = socioImagenService.actualizarQR(
            socio.getId(),
            null,  // No hay QR anterior
            qrFile
        );
        
        // 5. Actualizar socio con public_id
        socio.setQr(publicId);
        return socioRepository.save(socio);
    }
    
    private String generarContenidoQR(SocioEntity socio) {
        // Opción 1: URL de verificación
        return "https://cadet.com/verificar-socio/" + socio.getId();
        
        // Opción 2: JSON con datos del socio
        // return String.format("{\"id\":%d,\"matricula\":\"%s\"}", 
        //                     socio.getId(), socio.getMatricula());
    }
}
```

### Caso 2: Regenerar QR al Actualizar Datos del Socio

```java
@Transactional
public SocioEntity actualizarSocio(Integer id, SocioDTO dto) {
    SocioEntity socio = socioRepository.findById(id)
        .orElseThrow(() -> new Exception("Socio no encontrado"));
    
    // Actualizar campos del socio
    socio.setNombresocio(dto.getNombre());
    socio.setMatricula(dto.getMatricula());
    // ... otros campos
    
    // Si los datos cambiaron, regenerar el QR
    if (cambioDatosRelevantes(socio, dto)) {
        String qrContent = generarContenidoQR(socio);
        byte[] qrBytes = qrCodeGeneratorService.generateQRCodeImage(qrContent, 300, 300);
        MultipartFile qrFile = convertirBytesAMultipartFile(qrBytes, id);
        
        // Esto elimina el QR anterior automáticamente
        String newPublicId = socioImagenService.actualizarQR(id, socio.getQr(), qrFile);
        socio.setQr(newPublicId);
    }
    
    return socioRepository.save(socio);
}
```

### Caso 3: Eliminar QR al Desactivar/Eliminar Socio

```java
@Transactional
public void desactivarSocio(Integer id) {
    SocioEntity socio = socioRepository.findById(id)
        .orElseThrow(() -> new Exception("Socio no encontrado"));
    
    // Eliminar QR de Cloudinary
    if (socio.getQr() != null) {
        socioImagenService.eliminarQR(socio.getQr());
        socio.setQr(null);
        socio.setLinkqr(null);
    }
    
    socio.setEstado(0); // Desactivar
    socioRepository.save(socio);
}
```

### Caso 4: Endpoint REST para Regenerar QR

```java
@RestController
@RequestMapping("/api/socios")
public class SocioController {
    
    @Autowired
    private SocioImagenService socioImagenService;
    
    @Autowired
    private QRCodeGeneratorService qrCodeGeneratorService;
    
    @PostMapping("/{id}/regenerar-qr")
    public ResponseEntity<?> regenerarQR(@PathVariable Integer id) {
        try {
            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));
            
            // Generar nuevo QR
            String qrContent = "https://cadet.com/verificar-socio/" + id;
            byte[] qrBytes = qrCodeGeneratorService.generateQRCodeImage(qrContent, 300, 300);
            MultipartFile qrFile = new MultipartFileFromBytes(qrBytes, "qr_" + id + ".png", "image/png");
            
            // Subir a Cloudinary (elimina el anterior)
            String newPublicId = socioImagenService.actualizarQR(id, socio.getQr(), qrFile);
            
            // Actualizar socio
            socio.setQr(newPublicId);
            socioRepository.save(socio);
            
            return ResponseEntity.ok()
                .body("QR regenerado exitosamente. Public ID: " + newPublicId);
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al regenerar QR: " + e.getMessage());
        }
    }
}
```

## 🛠️ Clase Helper: MultipartFileFromBytes

```java
/**
 * Clase helper para convertir bytes a MultipartFile
 * Necesaria porque QRCodeGeneratorService devuelve byte[]
 */
public class MultipartFileFromBytes implements MultipartFile {
    private final byte[] content;
    private final String name;
    private final String contentType;

    public MultipartFileFromBytes(byte[] content, String name, String contentType) {
        this.content = content;
        this.name = name;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public java.io.InputStream getInputStream() {
        return new java.io.ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException {
        java.nio.file.Files.write(dest.toPath(), content);
    }
}
```

## 📊 Estructura de Datos

### Campo en SocioEntity

```java
@Entity
@Table(name = "socio")
public class SocioEntity {
    // ... otros campos
    
    @Column(name = "qr")
    private String qr;  // Almacena el public_id de Cloudinary: "SOCIO_QR/socio_123_qr_1234567890"
    
    @Column(name = "linkqr")
    private String linkqr;  // URL completa de Cloudinary (opcional)
    
    // ... getters y setters
}
```

### Migración de Base de Datos

Si necesitas agregar un campo adicional para almacenar el public_id separado:

```sql
-- Agregar campo para public_id del QR (si no existe)
ALTER TABLE socio ADD COLUMN qr_public_id VARCHAR(500);

-- Comentario
COMMENT ON COLUMN socio.qr_public_id IS 'Public ID de Cloudinary para el código QR del socio';
```

## 🎨 Transformaciones de Cloudinary (Opcional)

Puedes aplicar transformaciones al QR al obtener la URL:

```java
// QR con borde blanco
String url = "https://res.cloudinary.com/{cloud_name}/image/upload/b_white,bo_10px_solid_white/" + publicId;

// QR con tamaño específico
String url = "https://res.cloudinary.com/{cloud_name}/image/upload/w_200,h_200/" + publicId;

// QR optimizado para web
String url = "https://res.cloudinary.com/{cloud_name}/image/upload/q_auto,f_auto/" + publicId;
```

## ✅ Checklist de Implementación

- [ ] Agregar campo `qr` o `qr_public_id` en SocioEntity
- [ ] Implementar generación de QR en creación de socio
- [ ] Implementar regeneración de QR al actualizar datos
- [ ] Implementar eliminación de QR al desactivar socio
- [ ] Crear endpoint REST para regenerar QR manualmente
- [ ] Agregar clase helper MultipartFileFromBytes
- [ ] Probar upload de QR a Cloudinary
- [ ] Probar eliminación automática de QR antiguo
- [ ] Actualizar documentación de API
- [ ] Migrar QRs existentes a Cloudinary (si aplica)

## 🔍 Verificación

```java
// Verificar que el QR se guardó correctamente
SocioEntity socio = socioRepository.findById(id).get();
System.out.println("Public ID del QR: " + socio.getQr());
// Debería mostrar algo como: "SOCIO_QR/socio_123_qr_1642345678901"

// Construir URL de Cloudinary
String url = "https://res.cloudinary.com/" + cloudName + "/image/upload/" + socio.getQr() + ".png";
System.out.println("URL del QR: " + url);
```

## 🚀 Mejoras Futuras

1. **QR Personalizados**: Agregar logo del CADET en el centro del QR
2. **Versionado**: Mantener historial de QRs generados
3. **Analytics**: Trackear cuántas veces se escanea cada QR
4. **Formatos**: Generar QRs en múltiples formatos (PNG, SVG, PDF)
5. **Descarga Masiva**: Endpoint para descargar todos los QRs en un ZIP

## 📞 Soporte

- Ver archivo: [SocioQRCloudinaryExample.java](src/main/java/app/examples/SocioQRCloudinaryExample.java)
- Documentación general: [CLOUDINARY_GUIDE.md](CLOUDINARY_GUIDE.md)
- Documentación de Cloudinary: [https://cloudinary.com/documentation](https://cloudinary.com/documentation)
