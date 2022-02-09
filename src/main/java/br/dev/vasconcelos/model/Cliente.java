package br.dev.vasconcelos.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
public @Data
class Cliente {

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
