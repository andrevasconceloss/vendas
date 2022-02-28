package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.exception.PedidoNaoEncontradoException;
import br.dev.vasconcelos.exception.RegraNegocioException;
import br.dev.vasconcelos.rest.ApiErrors;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        return new ApiErrors(ex.getMessage());
    }
}
