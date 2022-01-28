package br.dev.vasconcelos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VendasConfiguration {

    @Value("${application.environment}")
    private String applicationEnvironment;

    @Bean
    public CommandLineRunner executar() {
        return args -> {
            System.out.println("API em funcionamente: " + applicationEnvironment);
        };
    }
}
