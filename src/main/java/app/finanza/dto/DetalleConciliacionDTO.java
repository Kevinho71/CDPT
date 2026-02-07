package app.finanza.dto;

import java.math.BigDecimal;

/**
 * DTO que muestra cómo se aplicó un pago a una deuda específica
 * Usado en la respuesta de transacciones para mostrar la conciliación
 */
public class DetalleConciliacionDTO {
    
    private Integer deudaId;
    private String tipoObligacion; // MATRICULA, MENSUALIDAD
    private Integer mes;
    private Integer gestion;
    private BigDecimal montoAplicado; // Cuánto de este pago se aplicó a esta deuda
    private String estadoDeudaDespues; // PAGADO, PARCIAL
    
    // Constructores
    public DetalleConciliacionDTO() {}
    
    public DetalleConciliacionDTO(Integer deudaId, String tipoObligacion, Integer mes, 
                                 Integer gestion, BigDecimal montoAplicado, String estadoDeudaDespues) {
        this.deudaId = deudaId;
        this.tipoObligacion = tipoObligacion;
        this.mes = mes;
        this.gestion = gestion;
        this.montoAplicado = montoAplicado;
        this.estadoDeudaDespues = estadoDeudaDespues;
    }
    
    // Getters y Setters
    public Integer getDeudaId() {
        return deudaId;
    }
    
    public void setDeudaId(Integer deudaId) {
        this.deudaId = deudaId;
    }
    
    public String getTipoObligacion() {
        return tipoObligacion;
    }
    
    public void setTipoObligacion(String tipoObligacion) {
        this.tipoObligacion = tipoObligacion;
    }
    
    public Integer getMes() {
        return mes;
    }
    
    public void setMes(Integer mes) {
        this.mes = mes;
    }
    
    public Integer getGestion() {
        return gestion;
    }
    
    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }
    
    public BigDecimal getMontoAplicado() {
        return montoAplicado;
    }
    
    public void setMontoAplicado(BigDecimal montoAplicado) {
        this.montoAplicado = montoAplicado;
    }
    
    public String getEstadoDeudaDespues() {
        return estadoDeudaDespues;
    }
    
    public void setEstadoDeudaDespues(String estadoDeudaDespues) {
        this.estadoDeudaDespues = estadoDeudaDespues;
    }
}
