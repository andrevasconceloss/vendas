package br.dev.vasconcelos.domain.repository;

import br.dev.vasconcelos.domain.entity.Cliente;
import br.dev.vasconcelos.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);
}
