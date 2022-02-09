package br.dev.vasconcelos.repository;

import br.dev.vasconcelos.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ClientesRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente save(Cliente cliente) {
        entityManager.persist( cliente );
        return cliente;
    }

    @Transactional
    public Cliente update(Cliente cliente) {
        entityManager.merge( cliente );
        return cliente;
    }

    @Transactional
    public void delete(Cliente cliente) {
        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge( cliente );
        }
        entityManager.remove( cliente );
    }

    @Transactional
    public void delete(Integer id) {
        Cliente cliente = entityManager.find( Cliente.class, id );
        delete( cliente );
    }

    @Transactional(readOnly = true)
    public List<Cliente> searchForName(String nome) {
        String jpql = " select c from Cliente c where upper(c.nome) like :nome ";
        TypedQuery<Cliente> query = entityManager.createQuery( jpql, Cliente.class );
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> searchAll() {
        return entityManager
                .createQuery( "from Cliente", Cliente.class )
                .getResultList();
    }
}
