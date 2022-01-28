package br.dev.vasconcelos.model;

import lombok.Data;

public @Data class ItemPedido {
    private Integer id;
    private Integer pedido_id;
    private Integer produto_id;
    private Integer quantidade;
}
