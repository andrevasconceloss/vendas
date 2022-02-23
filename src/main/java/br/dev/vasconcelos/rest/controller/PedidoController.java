package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.Pedido;
import br.dev.vasconcelos.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository repository;

    @PostMapping
    @ResponseStatus(CREATED)
    public Pedido save(@RequestBody Pedido pedido){
        return repository.save(pedido);
    }

    @GetMapping("/id")
    public Pedido getPedidoById(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository.findById(id)
                .map(pedido -> {
                    repository.delete(pedido);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Pedido pedido){
        repository.findById(id)
                .map(pedidoEncontrado -> {
                    pedido.setId(pedidoEncontrado.getId());
                    repository.save(pedido);
                    return pedidoEncontrado;
                })
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @GetMapping
    public List<Pedido> find(Pedido filtro ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }
}
