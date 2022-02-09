package br.dev.vasconcelos.repository;

import br.dev.vasconcelos.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientesRepository {

    private static String INSERT = " INSERT INTO CLIENTE (NOME) VALUES (?) ";
    private static String UPDATE = " UPDATE CLIENTE SET NOME= ? WHERE ID= ? ";
    private static String DELETE = " DELETE FROM CLIENTE WHERE ID= ? ";
    private static String SELECT = " SELECT * FROM CLIENTE ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente save(Cliente cliente) {
        jdbcTemplate.update( INSERT, new Object[]{cliente.getNome()} );
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void delete(Cliente cliente) {
        jdbcTemplate.update(DELETE, new Object[]{cliente.getId()});
    }

    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, id);
    }

    public List<Cliente> searchForName(String name) {
        return jdbcTemplate.query(
                SELECT.concat(" WHERE UPPER(NOME) LIKE ? "),
                new Object[]{"%" + name.toUpperCase() + "%"},
                getClienteMapper());
    }

    public List<Cliente> searchAll() {
        return jdbcTemplate.query(SELECT, getClienteMapper());
    }

    private RowMapper<Cliente> getClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Cliente cliente = new Cliente();
                cliente.setNome(resultSet.getString("nome"));
                cliente.setId(resultSet.getInt("id"));
                return cliente;
            }
        };
    }
}
