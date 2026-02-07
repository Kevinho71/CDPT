package app.finanza.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "estado_cuenta_socio")
public class EstadoCuentaSocioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @Column(name = "tipo_obligacion", nullable = false, length = 50)
    private String tipoObligacion; // MATRICULA, MENSUALIDAD, MULTA
    
    @Column(name = "gestion", nullable = false)
    private Integer gestion; // 2026
    
    @Column(name = "mes")
    private Integer mes; // 1 para Enero, NULL si es Matrícula
    
    @Column(name = "monto_original", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoOriginal;
    
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision = LocalDate.now();
    
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    
    @Column(name = "estado_pago", length = 20)
    private String estadoPago = "PENDIENTE"; // PENDIENTE, PAGADO, PARCIAL, VENCIDO
    
    @Column(name = "monto_pagado_acumulado", precision = 10, scale = 2)
    private BigDecimal montoPagadoAcumulado = BigDecimal.ZERO;
    
    // Constructores
    public EstadoCuentaSocioEntity() {}
    
    public EstadoCuentaSocioEntity(Integer id, SocioEntity socio, String tipoObligacion, 
                                  Integer gestion, Integer mes, BigDecimal montoOriginal,
                                  LocalDate fechaEmision, LocalDate fechaVencimiento,
                                  String estadoPago, BigDecimal montoPagadoAcumulado) {
        this.id = id;
        this.socio = socio;
        this.tipoObligacion = tipoObligacion;
        this.gestion = gestion;
        this.mes = mes;
        this.montoOriginal = montoOriginal;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoPago = estadoPago;
        this.montoPagadoAcumulado = montoPagadoAcumulado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
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
        return fechaEmision;
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
}
