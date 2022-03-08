package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.Cliente;
import br.dev.vasconcelos.domain.repository.ClientesRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clientes")
@Api("API Clientes")
public class ClienteController {

    @Autowired
    private ClientesRepository repository;

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public Cliente getClienteById( @PathVariable @ApiParam(value = "ID do cliente", example = "1") Integer id ) {
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente save( @RequestBody @Valid Cliente cliente ) {
        return repository.save( cliente );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete( @PathVariable Integer id ) {
        repository.findById(id)
                .map(cliente -> {
                    repository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid Cliente cliente ) {
        repository
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    repository.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }
}
