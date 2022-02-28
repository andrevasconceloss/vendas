package br.dev.vasconcelos.domain.repository;

import br.dev.vasconcelos.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
