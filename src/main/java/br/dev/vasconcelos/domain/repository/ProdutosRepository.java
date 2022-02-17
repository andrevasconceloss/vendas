package br.dev.vasconcelos.domain.repository;

import br.dev.vasconcelos.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
