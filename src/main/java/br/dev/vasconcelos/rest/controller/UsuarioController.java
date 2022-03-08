package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.Usuario;
import br.dev.vasconcelos.exception.SenhaInvalidaException;
import br.dev.vasconcelos.rest.dto.CredenciaisDTO;
import br.dev.vasconcelos.rest.dto.TokenDTO;
import br.dev.vasconcelos.security.jwt.JwtService;
import br.dev.vasconcelos.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        String senhaCripto = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripto);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {

            Usuario usuario = Usuario
                                .builder()
                                .login(credenciais.getLogin())
                                .senha(credenciais.getSenha())
                                .build();

            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);

            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        }
    }

}
