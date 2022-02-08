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
            Cliente cliente = new Cliente();
            cliente.setNome("André");
            clientes.save( cliente );

            Cliente cliente2 = new Cliente();
            cliente2.setNome("Rebeca");
            clientes.save( cliente2 );

            List<Cliente> todosClientes = clientes.searchAll();
            todosClientes.forEach(System.out::println);
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
