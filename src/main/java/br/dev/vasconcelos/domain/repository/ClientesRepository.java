package br.dev.vasconcelos.domain.repository;

import br.dev.vasconcelos.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);

    @Query(value = " select * from cliente where upper(nome) ilike %:nome% ", nativeQuery = true)
    //@Query(value = " select c from Cliente c where upper(c.nome) like %:nome% ")
    List<Cliente> findByNomeIlike(@Param("nome") String nome);

    @Query(value = " delete from cliente where id = :id", nativeQuery = true)
    @Modifying
    void deleteById(@Param("id") Integer id);

    Cliente findOneById(Integer id);

    List<Cliente> findByNomeOrIdOrderByNome(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedidos( @Param("id") Integer id );

}
