package app.finanza.service;

import app.finanza.dto.EstadoCuentaSocioDTO;
import app.finanza.entity.EstadoCuentaSocioEntity;

import java.util.List;

public interface EstadoCuentaSocioService {
    
    /**
     * Crea una nueva obligación (deuda) para un socio
     */
    EstadoCuentaSocioDTO crearObligacion(EstadoCuentaSocioDTO dto);
    
    /**
     * Obtiene el estado de cuenta completo de un socio
     */
    List<EstadoCuentaSocioDTO> obtenerEstadoCuenta(Integer socioId);
    
    /**
     * Obtiene solo las deudas pendientes de un socio
     */
    List<EstadoCuentaSocioDTO> obtenerDeudasPendientes(Integer socioId);
    
    /**
     * Verifica si un socio está moroso
     * @return true si tiene deudas vencidas
     */
    boolean esMoroso(Integer socioId);
    
    /**
     * Cuenta cuántas deudas vencidas tiene un socio
     */
    Long contarDeudasVencidas(Integer socioId);
    
    /**
     * Obtiene las deudas pendientes ordenadas por FIFO (para conciliación)
     */
    List<EstadoCuentaSocioEntity> obtenerDeudasParaConciliacion(Integer socioId);
    
    /**
     * Genera la deuda de matrícula para un socio recién afiliado
     * @param socioId ID del socio
     */
    void generarDeudaMatricula(Integer socioId);
    
    /**
     * Genera las deudas mensuales para todos los socios activos
     * Llamado por el cron job
     */
    void generarMensualidadesAutomaticas();
    
    /**
     * Marca como VENCIDO las deudas cuya fecha_vencimiento ya pasó y siguen PENDIENTE/PARCIAL.
     * Ejecutado diariamente por el cron job.
     */
    void marcarDeudasVencidas();
}
