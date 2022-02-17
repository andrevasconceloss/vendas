package br.dev.vasconcelos.domain.repository;

import br.dev.vasconcelos.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
