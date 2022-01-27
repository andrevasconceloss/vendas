package br.dev.vasconcelos.service;

import br.dev.vasconcelos.model.Cliente;
import br.dev.vasconcelos.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientesService {

    @Autowired
    public ClientesRepository repository;

    public void salvar(Cliente cliente){
        validar(cliente);
        repository.salvar(cliente);
    }

    public void validar(Cliente cliente){
        // Validar dados do cliente
    }

}
