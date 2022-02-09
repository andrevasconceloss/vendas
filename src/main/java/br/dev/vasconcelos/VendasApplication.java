package br.dev.vasconcelos;

import br.dev.vasconcelos.model.Cliente;
import br.dev.vasconcelos.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clientes) {
        return args -> {
            System.out.println("Criando registros");

            clientes.save( new Cliente("André") );
            clientes.save( new Cliente("Rebeca") );
            clientes.save( new Cliente("Teste da Silva") );


            System.out.println("Pesquisando clientes");
            System.out.println("Imprimindo clientes");

            List<Cliente> todosClientes = clientes.searchAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes");
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado.");
                clientes.update(c);
            });

            System.out.println("Pesquisando cliente");
            System.out.println("Imprimindo cliente");

            clientes.searchForName("sil").forEach(System.out::println);

            System.out.println("Pesquisando clientes");
            System.out.println("Imprimindo clientes");
            todosClientes = clientes.searchAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Deletando clientes");
            todosClientes = clientes.searchAll();
            todosClientes.forEach(c -> {
                clientes.delete(c);
            });

            System.out.println("Pesquisando clientes");
            System.out.println("Imprimindo clientes");
            todosClientes = clientes.searchAll();
            if (todosClientes.isEmpty()){
                System.out.println("Nenhum registro localizado.");
            } else {
                todosClientes.forEach(System.out::println);
            }
        };
    }

    @Value("${application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!!";
    }

    @GetMapping("/application")
    public String application() {
        return applicationName;
    }
}
