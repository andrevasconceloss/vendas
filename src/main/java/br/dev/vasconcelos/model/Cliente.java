package br.dev.vasconcelos.model;

import lombok.Data;

public @Data class Cliente {
    private Integer id;
    private String nome;

    public Cliente() {

    }
    public Cliente(String name) {
        this.nome = name;
    }
}
