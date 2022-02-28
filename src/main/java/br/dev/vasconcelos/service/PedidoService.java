package br.dev.vasconcelos.service;

import br.dev.vasconcelos.domain.entity.Pedido;
import br.dev.vasconcelos.domain.enums.StatusPedido;
import br.dev.vasconcelos.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizarStatus(Integer id, StatusPedido statusPedido);
}
