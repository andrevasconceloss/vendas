package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.Pedido;
import br.dev.vasconcelos.domain.entity.Produto;
import br.dev.vasconcelos.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidos;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save(@RequestBody Pedido pedido){
        return pedidos.save(pedido);
    }

    @GetMapping("/id")
    public Pedido getPedidoById(@PathVariable Integer id) {
        return pedidos
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        pedidos.findById(id)
                .map(pedido -> {
                    pedidos.delete(pedido);
                    return ResponseEntity.noContent();
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Pedido pedido){
        pedidos.findById(id)
                .map(pedidoEncontrado -> {
                    pedido.setId(pedidoEncontrado.getId());
                    pedidos.save(pedido);
                    return pedidoEncontrado;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @GetMapping
    public List<Pedido> find(Produto filtro ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return pedidos.findAll(example);
    }
}
