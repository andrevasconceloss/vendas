package br.dev.vasconcelos.model;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    public Cliente() {

    }

    public Cliente(String name) {
        this.nome = name;
    }
}
