package app.finanza.service.impl;

import app.common.exception.ResourceNotFoundException;
import app.finanza.dto.EstadoCuentaSocioDTO;
import app.finanza.entity.ConfiguracionCuotasEntity;
import app.finanza.entity.EstadoCuentaSocioEntity;
import app.finanza.repository.ConfiguracionCuotasRepository;
import app.finanza.repository.EstadoCuentaSocioRepository;
import app.finanza.service.EstadoCuentaSocioService;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadoCuentaSocioServiceImpl implements EstadoCuentaSocioService {
    
    @Autowired
    private EstadoCuentaSocioRepository estadoCuentaRepository;
    
    @Autowired
    private ConfiguracionCuotasRepository configuracionRepository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Override
    public EstadoCuentaSocioDTO crearObligacion(EstadoCuentaSocioDTO dto) {
        SocioEntity socio = socioRepository.findById(dto.getSocioId())
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado"));
        
        EstadoCuentaSocioEntity entity = new EstadoCuentaSocioEntity();
        entity.setSocio(socio);
        entity.setTipoObligacion(dto.getTipoObligacion());
        entity.setGestion(dto.getGestion());
        entity.setMes(dto.getMes());
        entity.setMontoOriginal(dto.getMontoOriginal());
        entity.setFechaEmision(dto.getFechaEmision() != null ? dto.getFechaEmision() : LocalDate.now());
        entity.setFechaVencimiento(dto.getFechaVencimiento());
        entity.setEstadoPago("PENDIENTE");
        entity.setMontoPagadoAcumulado(BigDecimal.ZERO);
        
        EstadoCuentaSocioEntity saved = estadoCuentaRepository.save(entity);
        return toDTO(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadoCuentaSocioDTO> obtenerEstadoCuenta(Integer socioId) {
        List<EstadoCuentaSocioEntity> estados = estadoCuentaRepository.findEstadoCuentaOrdenado(socioId);
        return estados.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadoCuentaSocioDTO> obtenerDeudasPendientes(Integer socioId) {
        List<EstadoCuentaSocioEntity> deudas = estadoCuentaRepository.findDeudasPendientesFIFO(socioId);
        return deudas.stream().map(this::toDTO).collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean esMoroso(Integer socioId) {
        Long deudasVencidas = contarDeudasVencidas(socioId);
        return deudasVencidas > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long contarDeudasVencidas(Integer socioId) {
        return estadoCuentaRepository.contarDeudasVencidas(socioId, LocalDate.now());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadoCuentaSocioEntity> obtenerDeudasParaConciliacion(Integer socioId) {
        return estadoCuentaRepository.findDeudasPendientesFIFO(socioId);
    }
    
    /**
     * FLUJO 1: Generación de deuda de matrícula
     * Se ejecuta cuando se aprueba una afiliación
     */
    @Override
    public void generarDeudaMatricula(Integer socioId) {
        SocioEntity socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con ID: " + socioId));
        
        int gestionActual = LocalDate.now().getYear();
        
        // Verificar que no exista ya una matrícula para esta gestión
        boolean yaExiste = estadoCuentaRepository.existeObligacion(
            socioId, gestionActual, null, "MATRICULA");
        
        if (yaExiste) {
            System.out.println("Ya existe una deuda de matrícula para el socio " + socioId + " en la gestión " + gestionActual);
            return;
        }
        
        // Obtener configuración de cuotas de la gestión actual
        ConfiguracionCuotasEntity config = configuracionRepository.findByGestion(gestionActual)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No existe configuración de cuotas para la gestión " + gestionActual));
        
        // Crear la deuda de matrícula
        EstadoCuentaSocioEntity matricula = new EstadoCuentaSocioEntity();
        matricula.setSocio(socio);
        matricula.setTipoObligacion("MATRICULA");
        matricula.setGestion(gestionActual);
        matricula.setMes(null); // Matrícula no tiene mes
        matricula.setMontoOriginal(config.getMontoMatricula());
        matricula.setFechaEmision(LocalDate.now());
        
        // Fecha de vencimiento: 30 días desde la aprobación
        matricula.setFechaVencimiento(LocalDate.now().plusDays(30));
        matricula.setEstadoPago("PENDIENTE");
        matricula.setMontoPagadoAcumulado(BigDecimal.ZERO);
        
        estadoCuentaRepository.save(matricula);
        
        System.out.println("Deuda de matrícula generada para socio " + socioId + 
                         ": " + config.getMontoMatricula() + " Bs");
    }
    
    /**
     * FLUJO 1: Generación de mensualidades automáticas
     * Se ejecuta el día 1 de cada mes mediante un cron job
     */
    @Override
    public void generarMensualidadesAutomaticas() {
        LocalDate fechaActual = LocalDate.now();
        int gestionActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonthValue();
        
        System.out.println("=== Iniciando generación de mensualidades ===");
        System.out.println("Gestión: " + gestionActual + ", Mes: " + mesActual);
        
        // Obtener configuración de cuotas
        ConfiguracionCuotasEntity config = configuracionRepository.findByGestion(gestionActual)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No existe configuración de cuotas para la gestión " + gestionActual));
        
        // Obtener todos los socios activos (estado = 1)
        List<SocioEntity> sociosActivos = socioRepository.findByEstado(1);
        
        int generados = 0;
        int omitidos = 0;
        
        for (SocioEntity socio : sociosActivos) {
            // Verificar que no exista ya una mensualidad para este mes/gestión
            boolean yaExiste = estadoCuentaRepository.existeObligacion(
                socio.getId(), gestionActual, mesActual, "MENSUALIDAD");
            
            if (yaExiste) {
                omitidos++;
                continue;
            }
            
            // Crear la mensualidad
            EstadoCuentaSocioEntity mensualidad = new EstadoCuentaSocioEntity();
            mensualidad.setSocio(socio);
            mensualidad.setTipoObligacion("MEN SUALIDAD");
            mensualidad.setGestion(gestionActual);
            mensualidad.setMes(mesActual);
            mensualidad.setMontoOriginal(config.getMontoMensualidad());
            mensualidad.setFechaEmision(fechaActual);
            
            // Fecha de vencimiento: día límite del mes actual
            int diaLimite = config.getDiaLimitePago() != null ? config.getDiaLimitePago() : 10;
            mensualidad.setFechaVencimiento(LocalDate.of(gestionActual, mesActual, diaLimite));
            
            mensualidad.setEstadoPago("PENDIENTE");
            mensualidad.setMontoPagadoAcumulado(BigDecimal.ZERO);
            
            estadoCuentaRepository.save(mensualidad);
            generados++;
        }
        
        System.out.println("=== Generación completada ===");
        System.out.println("   Mensualidades generadas: " + generados);
        System.out.println("   Mensualidades omitidas (ya existían): " + omitidos);
        System.out.println("   Total socios procesados: " + sociosActivos.size());
    }
    
    /**
     * Convierte una entidad a DTO
     */
    private EstadoCuentaSocioDTO toDTO(EstadoCuentaSocioEntity entity) {
        EstadoCuentaSocioDTO dto = new EstadoCuentaSocioDTO();
        dto.setId(entity.getId());
        dto.setSocioId(entity.getSocio().getId());
        dto.setSocioNombre(entity.getSocio().getNombresocio());
        dto.setTipoObligacion(entity.getTipoObligacion());
        dto.setGestion(entity.getGestion());
        dto.setMes(entity.getMes());
        dto.setMontoOriginal(entity.getMontoOriginal());
        dto.setFechaEmision(entity.getFechaEmision());
        dto.setFechaVencimiento(entity.getFechaVencimiento());
        dto.setEstadoPago(entity.getEstadoPago());
        dto.setMontoPagadoAcumulado(entity.getMontoPagadoAcumulado());
        
        // Calcular saldo pendiente
        BigDecimal saldo = entity.getMontoOriginal().subtract(entity.getMontoPagadoAcumulado());
        dto.setSaldoPendiente(saldo);
        
        return dto;
    }
}
