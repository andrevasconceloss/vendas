package br.dev.vasconcelos.rest.controller;

import br.dev.vasconcelos.domain.entity.ItemPedido;
import br.dev.vasconcelos.domain.entity.Pedido;
import br.dev.vasconcelos.domain.enums.StatusPedido;
import br.dev.vasconcelos.rest.dto.AtualizacaoStatusPedidoDTO;
import br.dev.vasconcelos.rest.dto.InformacaoItemPedidoDTO;
import br.dev.vasconcelos.rest.dto.InformacoesPedidoDTO;
import br.dev.vasconcelos.rest.dto.PedidoDTO;
import br.dev.vasconcelos.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service
                .obterPedidoCompleto(id)
                .map(pedido -> converter(pedido))
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        service.atualizarStatus(id, StatusPedido.valueOf(dto.getNovoStatus()));
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getData_pedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cfp(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream()
                .map(item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricao(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco_unitario())
                        .quantidade(item.getQuantidade())
                        .build())
                .collect(Collectors.toList());
    }
}
