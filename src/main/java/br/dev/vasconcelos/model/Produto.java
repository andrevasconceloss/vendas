package br.dev.vasconcelos.model;

import lombok.Data;

import java.math.BigDecimal;

public @Data class Produto {
    private Integer id;
    private String descricao;
    private BigDecimal preco_unitario;
}
