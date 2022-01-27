package br.dev.vasconcelos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VendasConfiguration {

    @Bean(name = "applicationName")
    public String apllicationName(){
        return "Sistema de vendas";
    }
}
