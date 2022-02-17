package br.dev.vasconcelos.service;

import br.dev.vasconcelos.domain.entity.Cliente;
import br.dev.vasconcelos.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientesService {

    @Autowired
    public ClientesRepository repository;

    public void salvar(Cliente cliente){
        validar(cliente);
        repository.save(cliente);
    }

    public void validar(Cliente cliente){
        // Validar dados do cliente
    }

}
