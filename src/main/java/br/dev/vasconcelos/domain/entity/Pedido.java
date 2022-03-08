package br.dev.vasconcelos.domain.entity;

import br.dev.vasconcelos.domain.enums.StatusPedido;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JoinColumn(name="cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate data_pedido;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<ItemPedido> itens;
}
