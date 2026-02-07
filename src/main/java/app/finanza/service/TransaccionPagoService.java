package app.finanza.service;

import app.finanza.dto.TransaccionPagoCreateDTO;
import app.finanza.dto.TransaccionPagoResponseDTO;
import app.finanza.dto.TransaccionPagoUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface TransaccionPagoService {
    
    List<TransaccionPagoResponseDTO> findAll();
    
    List<TransaccionPagoResponseDTO> findBySocio(Integer socioId);
    
    List<TransaccionPagoResponseDTO> findByEstado(String estadoPago);
    
    List<TransaccionPagoResponseDTO> findByFechaRango(LocalDate fechaInicio, LocalDate fechaFin);
    
    TransaccionPagoResponseDTO findById(Integer id);
    
    TransaccionPagoResponseDTO create(TransaccionPagoCreateDTO dto, MultipartFile comprobante) throws IOException;
    
    TransaccionPagoResponseDTO update(Integer id, TransaccionPagoUpdateDTO dto, MultipartFile comprobante) throws IOException;
    
    void delete(Integer id);
    
    /**
     * FLUJO 2: Registro de pago por el socio (sube comprobante)
     * Crea una transacción en estado EN_REVISION
     */
    TransaccionPagoResponseDTO registrarPagoSocio(Integer socioId, TransaccionPagoCreateDTO dto, MultipartFile comprobante);
    
    /**
     * FLUJO 3: Algoritmo FIFO - Aprobación y conciliación de pago
     * Este es el corazón del sistema
     * Aplica el pago a las deudas más antiguas primero
     */
    TransaccionPagoResponseDTO aprobarYConciliar(Integer transaccionId, Integer adminId);
    
    /**
     * Rechazar un pago
     */
    TransaccionPagoResponseDTO rechazarPago(Integer transaccionId, Integer adminId, String motivo);
    
    /**
     * Obtener pagos pendientes de revisión
     */
    List<TransaccionPagoResponseDTO> obtenerPagosPendientesRevision();
}
