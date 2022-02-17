package br.dev.vasconcelos.repository;

import br.dev.vasconcelos.model.Cliente;
import br.dev.vasconcelos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);
}
