package app.finanza.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EstadoCuentaSocioDTO {
    
    private Integer id;
    private Integer socioId;
    private String socioNombre;
    private String tipoObligacion; // MATRICULA, MENSUALIDAD, MULTA
    private Integer gestion;
    private Integer mes;
    private BigDecimal montoOriginal;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private String estadoPago; // PENDIENTE, PAGADO, PARCIAL, VENCIDO
    private BigDecimal montoPagadoAcumulado;
    private BigDecimal saldoPendiente; // Calculado: montoOriginal - montoPagadoAcumulado
    
    // Constructores
    public EstadoCuentaSocioDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getSocioId() {
        return socioId;
    }
    
    public void setSocioId(Integer socioId) {
        this.socioId = socioId;
    }
    
    public String getSocioNombre() {
        return socioNombre;
    }
    
    public void setSocioNombre(String socioNombre) {
        this.socioNombre = socioNombre;
    }
    
    public String getTipoObligacion() {
        return tipoObligacion;
    }
    
    public void setTipoObligacion(String tipoObligacion) {
        this.tipoObligacion = tipoObligacion;
    }
    
    public Integer getGestion() {
        return gestion;
    }
    
    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }
    
    public Integer getMes() {
        return mes;
    }
    
    public void setMes(Integer mes) {
        this.mes = mes;
    }
    
    public BigDecimal getMontoOriginal() {
        return montoOriginal;
    }
    
    public void setMontoOriginal(BigDecimal montoOriginal) {
        this.montoOriginal = montoOriginal;
    }
    
    public LocalDate getFechaEmision() {
        return    fechaEmision;
    }
    
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getEstadoPago() {
        return estadoPago;
    }
    
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
    
    public BigDecimal getMontoPagadoAcumulado() {
        return montoPagadoAcumulado;
    }
    
    public void setMontoPagadoAcumulado(BigDecimal montoPagadoAcumulado) {
        this.montoPagadoAcumulado = montoPagadoAcumulado;
    }
    
    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }
    
    public void setSaldoPendiente(BigDecimal saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }
}
