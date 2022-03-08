package br.dev.vasconcelos.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false)
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @JsonIgnore
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

    public Cliente(String name) {
        this.nome = name;
    }
}
