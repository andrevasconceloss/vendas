package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.Produto;
import br.dev.vasconcelos.domain.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutosRepository repository;

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id ) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete( @PathVariable Integer id ) {
        repository.findById(id)
                .map(produto -> {
                    repository.delete(produto);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody Produto produto) {
        repository
                .findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    repository.save(produto);
                    return produtoExistente;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }
}
