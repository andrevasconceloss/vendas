package br.dev.vasconcelos;

import br.dev.vasconcelos.model.Cliente;
import br.dev.vasconcelos.model.Pedido;
import br.dev.vasconcelos.repository.ClientesRepository;
import br.dev.vasconcelos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clienteRepository, @Autowired PedidoRepository pedidoRepository) {
        return args -> {
            System.out.println("Criando Cliente");
            Cliente cliente = new Cliente("André");
            clienteRepository.save(cliente);

            System.out.println("Criando Pedido");
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setData_pedido( LocalDate.now() );
            pedido.setTotal( BigDecimal.valueOf(100.00) );
            pedidoRepository.save(pedido);

            Cliente cliente2 = clienteRepository.findClienteFetchPedidos(cliente.getId());
            System.out.println( cliente );
            System.out.println( cliente2.getPedidos() );

            pedidoRepository.findByCliente( cliente ).forEach(System.out::println);
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
