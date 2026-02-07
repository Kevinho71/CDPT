package app.finanza.service.impl;

import app.common.exception.ResourceNotFoundException;
import app.common.util.ArchivoService;
import app.core.entity.UsuarioEntity;
import app.core.repository.UsuarioRepository;
import app.finanza.dto.DetalleConciliacionDTO;
import app.finanza.dto.TransaccionPagoCreateDTO;
import app.finanza.dto.TransaccionPagoResponseDTO;
import app.finanza.dto.TransaccionPagoUpdateDTO;
import app.finanza.entity.*;
import app.finanza.repository.DetallePagoDeudaRepository;
import app.finanza.repository.EstadoCuentaSocioRepository;
import app.finanza.repository.TransaccionPagoRepository;
import app.finanza.service.TransaccionPagoService;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransaccionPagoServiceImpl implements TransaccionPagoService {
    
    @Autowired
    private TransaccionPagoRepository repository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ArchivoService archivoService;
    
    @Autowired
    private EstadoCuentaSocioRepository estadoCuentaRepository;
    
    @Autowired
    private DetallePagoDeudaRepository detallePagoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<TransaccionPagoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransaccionPagoResponseDTO> findBySocio(Integer socioId) {
        return repository.findByFkSocio_Id(socioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransaccionPagoResponseDTO> findByEstado(String estadoPago) {
        return repository.findByEstadoPago(estadoPago).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TransaccionPagoResponseDTO> findByFechaRango(LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.findByFechaTransaccionBetween(fechaInicio, fechaFin).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public TransaccionPagoResponseDTO findById(Integer id) {
        TransaccionPagoEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción de pago no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    public TransaccionPagoResponseDTO create(TransaccionPagoCreateDTO dto, MultipartFile comprobante) {
        TransaccionPagoEntity entity = new TransaccionPagoEntity();
        
        SocioEntity socio = socioRepository.findById(dto.getFkSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + dto.getFkSocio()));
        entity.setSocio(socio);
        
        if (dto.getFkUsuarioAdmin() != null) {
            UsuarioEntity usuario = usuarioRepository.findById(dto.getFkUsuarioAdmin())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getFkUsuarioAdmin()));
            entity.setUsuarioAdmin(usuario);
        }
        
        entity.setMontoTotal(dto.getMontoTotal());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setReferenciaBancaria(dto.getReferenciaBancaria());
        entity.setObservaciones(dto.getObservaciones());
        
        // Subir comprobante o usar URL proporcionada
        if (comprobante != null && !comprobante.isEmpty()) {
            entity.setComprobanteUrl(archivoService.uploadFile(comprobante, "comprobantes"));
        } else if (dto.getComprobanteUrl() != null) {
            entity.setComprobanteUrl(dto.getComprobanteUrl());
        }
        
        entity.setEstado("APROBADO"); // Estado por defecto
        
        TransaccionPagoEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public TransaccionPagoResponseDTO update(Integer id, TransaccionPagoUpdateDTO dto, MultipartFile comprobante) {
        TransaccionPagoEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción de pago no encontrada con ID: " + id));
        
        if (dto.getMontoTotal() != null) entity.setMontoTotal(dto.getMontoTotal());
        if (dto.getMetodoPago() != null) entity.setMetodoPago(dto.getMetodoPago());
        if (dto.getReferenciaBancaria() != null) entity.setReferenciaBancaria(dto.getReferenciaBancaria());
        if (dto.getObservaciones() != null) entity.setObservaciones(dto.getObservaciones());
        if (dto.getEstadoPago() != null) entity.setEstado(dto.getEstadoPago());
        
        // Actualizar comprobante si se proporciona uno nuevo
        if (comprobante != null && !comprobante.isEmpty()) {
            entity.setComprobanteUrl(archivoService.uploadFile(comprobante, "comprobantes"));
        } else if (dto.getComprobanteUrl() != null) {
            entity.setComprobanteUrl(dto.getComprobanteUrl());
        }
        
        TransaccionPagoEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Transacción de pago no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private TransaccionPagoResponseDTO toResponseDTO(TransaccionPagoEntity entity) {
        TransaccionPagoResponseDTO dto = new TransaccionPagoResponseDTO();
        dto.setId(entity.getId());
        dto.setFkSocio(entity.getSocio() != null ? entity.getSocio().getId() : null);
        dto.setSocioNombre(entity.getSocio() != null ? entity.getSocio().getNombresocio() : null);
        dto.setFkUsuarioAdmin(entity.getUsuarioAdmin() != null ? entity.getUsuarioAdmin().getId() : null);
        dto.setUsuarioAdminNombre(entity.getUsuarioAdmin() != null ? entity.getUsuarioAdmin().getNombre() : null);
        dto.setMontoTotal(entity.getMontoTotal());
        dto.setMetodoPago(entity.getMetodoPago());
        dto.setReferenciaBancaria(entity.getReferenciaBancaria());
        dto.setComprobanteUrl(entity.getComprobanteUrl());
        dto.setFechaTransaccion(entity.getFechaPago());
        dto.setEstadoPago(entity.getEstado());
        dto.setObservaciones(entity.getObservaciones());
        return dto;
    }
    
    // ========== NUEVOS MÉTODOS: SISTEMA DE CUENTA CORRIENTE ==========
    
    /**
     * FLUJO 2: Registro de pago por el socio
     * Crea una transacción en estado EN_REVISION (pendiente de aprobación)
     */
    @Override
    public TransaccionPagoResponseDTO registrarPagoSocio(Integer socioId, TransaccionPagoCreateDTO dto, MultipartFile comprobante) {
        SocioEntity socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + socioId));
        
        if (comprobante == null || comprobante.isEmpty()) {
            throw new IllegalArgumentException("El comprobante de pago es obligatorio");
        }
        
        TransaccionPagoEntity entity = new TransaccionPagoEntity();
        entity.setSocio(socio);
        entity.setMontoTotal(dto.getMontoTotal());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setReferenciaBancaria(dto.getReferenciaBancaria());
        entity.setObservaciones(dto.getObservaciones());
        entity.setComprobanteUrl(archivoService.uploadFile(comprobante, "comprobantes"));
        entity.setEstado("EN_REVISION"); // Estado inicial
        
        TransaccionPagoEntity saved = repository.save(entity);
        
        System.out.println("Pago registrado por socio " + socioId + " - Monto: " + dto.getMontoTotal() + " Bs - Estado: EN_REVISION");
        
        return toResponseDTO(saved);
    }
    
    /**
     * FLUJO 3: ALGORITMO FIFO - Aprobación y Conciliación Automática
     * ESTE ES EL CORAZÓN DEL SISTEMA
     * 
     * Lógica:
     * 1. Verifica que el pago esté EN_REVISION
     * 2. Obtiene el monto disponible
     * 3. Busca las deudas pendientes ordenadas por fecha (FIFO)
     * 4. Aplica el dinero iterando sobre cada deuda
     * 5. Actualiza estados: PAGADO, PARCIAL
     * 6. Crea registros en detalle_pago_deuda
     * 7. Marca la transacción como APROBADO
     */
    @Override
    public TransaccionPagoResponseDTO aprobarYConciliar(Integer transaccionId, Integer adminId) {
        // 1. Validar que la transacción exista y esté EN_REVISION
        TransaccionPagoEntity transaccion = repository.findById(transaccionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con ID: " + transaccionId));
        
        if (!"EN_REVISION".equals(transaccion.getEstado())) {
            throw new IllegalStateException("La transacción ya fue procesada previamente. Estado actual: " + transaccion.getEstado());
        }
        
        // 2. Asignar el admin que aprueba
        if (adminId != null) {
            UsuarioEntity admin = usuarioRepository.findById(adminId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario admin no encontrado con ID: " + adminId));
            transaccion.setUsuarioAdmin(admin);
        }
        
        // 3. Recuperar el monto disponible
        BigDecimal montoDisponible = transaccion.getMontoTotal();
        Integer socioId = transaccion.getSocio().getId();
        
        System.out.println("=== INICIO ALGORITMO FIFO ===");
        System.out.println("Transacción ID: " + transaccionId);
        System.out.println("Socio ID: " + socioId);
        System.out.println("Monto disponible: " + montoDisponible + " Bs");
        
        // 4. Obtener deudas pendientes ordenadas por fecha (FIFO)
        List<EstadoCuentaSocioEntity> deudasPendientes = estadoCuentaRepository.findDeudasPendientesFIFO(socioId);
        
        System.out.println("Deudas pendientes encontradas: " + deudasPendientes.size());
        
        // 5. Lista para almacenar los detalles de conciliación
        List<DetalleConciliacionDTO> detallesConciliacion = new ArrayList<>();
        
        // 6. Iterar sobre cada deuda y aplicar el dinero (Bucket Logic)
        for (EstadoCuentaSocioEntity deuda : deudasPendientes) {
            if (montoDisponible.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Se acabó el dinero disponible. Deteniendo iteración.");
                break; // Se acabó el dinero
            }
            
            // Calcular saldo pendiente de esta deuda
            BigDecimal saldoDeuda = deuda.getMontoOriginal().subtract(deuda.getMontoPagadoAcumulado());
            
            System.out.println("\n--- Procesando deuda ID: " + deuda.getId() + " ---");
            System.out.println("Tipo: " + deuda.getTipoObligacion() + " - Mes: " + deuda.getMes() + " - Gestión: " + deuda.getGestion());
            System.out.println("Saldo pendiente: " + saldoDeuda + " Bs");
            System.out.println("Monto disponible: " + montoDisponible + " Bs");
            
            // Determinar cuánto aplicar a esta deuda
            BigDecimal montoAplicar;
            String nuevoEstado;
            
            if (montoDisponible.compareTo(saldoDeuda) >= 0) {
                // Tengo suficiente dinero para cubrir toda la deuda
                montoAplicar = saldo Deuda;
                nuevoEstado = "PAGADO";
                System.out.println("Cubriendo TODA la deuda: " + montoAplicar + " Bs");
            } else {
                // Solo puedo cubrir parte de la deuda
                montoAplicar = montoDisponible;
                nuevoEstado = "PARCIAL";
                System.out.println("Cubriendo PARTE de la deuda: " + montoAplicar + " Bs");
            }
            
            // 7. Crear registro en detalle_pago_deuda (Conciliación)
            DetallePagoDeudaEntity detalle = new DetallePagoDeudaEntity();
            detalle.setTransaccion(transaccion);
            detalle.setEstadoCuenta(deuda);
            detalle.setMontoAplicado(montoAplicar);
            detallePagoRepository.save(detalle);
            
            // 8. Actualizar el monto acumulado y estado de la deuda
            BigDecimal nuevoAcumulado = deuda.getMontoPagadoAcumulado().add(montoAplicar);
            deuda.setMontoPagadoAcumulado(nuevoAcumulado);
            deuda.setEstadoPago(nuevoEstado);
            estadoCuentaRepository.save(deuda);
            
            System.out.println("Nuevo acumulado: " + nuevoAcumulado + " Bs");
            System.out.println("Nuevo estado: " + nuevoEstado);
            
            // 9. Guardar detalle para respuesta
            DetalleConciliacionDTO detalleDTO = new DetalleConciliacionDTO(
                deuda.getId(),
                deuda.getTipoObligacion(),
                deuda.getMes(),
                deuda.getGestion(),
                montoAplicar,
                nuevoEstado
            );
            detallesConciliacion.add(detalleDTO);
            
            // 10. Reducir el monto disponible
            montoDisponible = montoDisponible.subtract(montoAplicar);
            System.out.println("Dinero restante: " + montoDisponible + " Bs");
        }
        
        // 11. Marcar la transacción como APROBADO
        transaccion.setEstado("APROBADO");
        TransaccionPagoEntity transaccionActualizada = repository.save(transaccion);
        
        System.out.println("\n=== FIN ALGORITMO FIFO ===");
        System.out.println("Deudas procesadas: " + detallesConciliacion.size());
        System.out.println("Dinero sobrante: " + montoDisponible + " Bs");
        
        // 12. Construir respuesta con detalles de conciliación
        TransaccionPagoResponseDTO response = toResponseDTO(transaccionActualizada);
        response.setDetallesConciliacion(detallesConciliacion);
        
        return response;
    }
    
    /**
     * Rechazar un pago
     */
    @Override
    public TransaccionPagoResponseDTO rechazarPago(Integer transaccionId, Integer adminId, String motivo) {
        TransaccionPagoEntity transaccion = repository.findById(transaccionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con ID: " + transaccionId));
        
        if (!"EN_REVISION".equals(transaccion.getEstado())) {
            throw new IllegalStateException("Solo se pueden rechazar pagos EN_REVISION");
        }
        
        if (adminId != null) {
            UsuarioEntity admin = usuarioRepository.findById(adminId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario admin no encontrado con ID: " + adminId));
            transaccion.setUsuarioAdmin(admin);
        }
        
        transaccion.setEstado("RECHAZADO");
        transaccion.setObservaciones(motivo);
        
        TransaccionPagoEntity updated = repository.save(transaccion);
        return toResponseDTO(updated);
    }
    
    /**
     * Obtener pagos pendientes de revisión
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransaccionPagoResponseDTO> obtenerPagosPendientesRevision() {
        return repository.findByEstadoPago("EN_REVISION").stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}

