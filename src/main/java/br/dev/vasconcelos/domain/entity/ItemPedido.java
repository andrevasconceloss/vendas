package br.dev.vasconcelos.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
public @Data class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column
    private Integer quantidade;
}
