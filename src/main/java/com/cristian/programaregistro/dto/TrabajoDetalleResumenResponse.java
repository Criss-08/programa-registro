package com.cristian.programaregistro.dto;

import java.math.BigDecimal;

public class TrabajoDetalleResumenResponse {

    private Long trabajoId;
    private Long cantidadDetalles;
    private BigDecimal total;

    public TrabajoDetalleResumenResponse(Long trabajoId, Long cantidadDetalles, BigDecimal total) {
        this.trabajoId = trabajoId;
        this.cantidadDetalles = cantidadDetalles;
        this.total = total;
    }

    public Long getTrabajoId() {
        return trabajoId;
    }

    public void setTrabajoId(Long trabajoId) {
        this.trabajoId = trabajoId;
    }

    public Long getCantidadDetalles() {
        return cantidadDetalles;
    }

    public void setCantidadDetalles(Long cantidadDetalles) {
        this.cantidadDetalles = cantidadDetalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}

