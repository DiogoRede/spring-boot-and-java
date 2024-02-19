package br.com.diogorede.springcursoaws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customSwagger(){
        return new OpenAPI()
                    .info(new Info()
                            .title("RESTful API with Java and Spring Boot 3")
                            .description("Seguindo o curso na Udemy")
                            .version("v1")
                            .termsOfService("https://portfolio-diogo-rede.vercel.app/")
                            .license(new License()
                                            .name("Apache 2.0")
                                            .url("https://portfolio-diogo-rede.vercel.app/")
                                            ));
    }
}
