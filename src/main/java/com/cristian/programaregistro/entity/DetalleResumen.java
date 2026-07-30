package com.cristian.programaregistro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class DetalleResumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotalTrabajo;

    private String observaciones;

    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "resumen_facturacion_id", nullable = false)
    @NotNull(message = "El resumen de facturacion es obligatorio")
    private ResumenFacturacion resumenFacturacion;

    @ManyToOne
    @JoinColumn(name = "trabajo_id", nullable = false)
    @NotNull(message = "El trabajo es obligatorio")
    private Trabajo trabajo;

    public DetalleResumen() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSubtotalTrabajo() {
        return subtotalTrabajo;
    }

    public void setSubtotalTrabajo(BigDecimal subtotalTrabajo) {
        this.subtotalTrabajo = subtotalTrabajo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public ResumenFacturacion getResumenFacturacion() {
        return resumenFacturacion;
    }

    public void setResumenFacturacion(ResumenFacturacion resumenFacturacion) {
        this.resumenFacturacion = resumenFacturacion;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }
}