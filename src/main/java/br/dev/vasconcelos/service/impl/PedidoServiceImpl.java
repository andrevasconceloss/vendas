package br.dev.vasconcelos.service.impl;

import br.dev.vasconcelos.domain.entity.Cliente;
import br.dev.vasconcelos.domain.entity.ItemPedido;
import br.dev.vasconcelos.domain.entity.Pedido;
import br.dev.vasconcelos.domain.entity.Produto;
import br.dev.vasconcelos.domain.enums.StatusPedido;
import br.dev.vasconcelos.domain.repository.ClientesRepository;
import br.dev.vasconcelos.domain.repository.ItensPedidoRepository;
import br.dev.vasconcelos.domain.repository.PedidosRepository;
import br.dev.vasconcelos.domain.repository.ProdutosRepository;
import br.dev.vasconcelos.exception.PedidoNaoEncontradoException;
import br.dev.vasconcelos.exception.RegraNegocioException;
import br.dev.vasconcelos.rest.dto.ItemPedidoDTO;
import br.dev.vasconcelos.rest.dto.PedidoDTO;
import br.dev.vasconcelos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository repository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItensPedidoRepository itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setData_pedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                })
                .orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if (itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar pedidos sem itens.");
        }

        return itens
                .stream()
                .map( itemPedidoDTO -> {
                    Integer idProduto = itemPedidoDTO.getProduto();
                    Produto produto =produtosRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());


    }

}
