package br.dev.vasconcelos.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

public @Data class Pedido {
    private Integer id;
    private Integer cliente_id;
    private Timestamp data_pedido;
    private BigDecimal total;
}
