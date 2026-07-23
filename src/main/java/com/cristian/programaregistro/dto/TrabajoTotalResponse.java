package com.cristian.programaregistro.dto;

import java.math.BigDecimal;

public class TrabajoTotalResponse {


    private Long trabajoId;
    private BigDecimal total;


    public TrabajoTotalResponse(Long trabajoId, BigDecimal total) {
        this.trabajoId = trabajoId;
        this.total = total;
    }

    public Long getTrabajoId() {
        return trabajoId;
    }

    public void setTrabajoId(Long trabajoId) {
        this.trabajoId = trabajoId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
