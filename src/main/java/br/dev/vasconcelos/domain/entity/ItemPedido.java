package br.dev.vasconcelos.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
