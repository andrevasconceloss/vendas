package br.dev.vasconcelos.repository;

import br.dev.vasconcelos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
