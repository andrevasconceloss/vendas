package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.Usuario;
import br.dev.vasconcelos.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        String senhaCripto = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripto);
        return usuarioService.salvar(usuario);
    }

}
