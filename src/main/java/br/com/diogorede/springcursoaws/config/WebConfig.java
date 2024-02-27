package br.com.diogorede.springcursoaws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${cors.originPatters:default}")
    private String cors = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = cors.split(",");
        registry.addMapping("/**")
            // .allowedMethods("GET","POST","PUT", "DELETE")
            .allowedMethods("*")
            .allowedOrigins(allowedOrigins)
            .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /* VIA QUERY PARAM (não recomendado) */
        /*    configurer.favorParameter(true)
                .parameterName("mediaType").ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json", MediaType.APPLICATION_JSON)
                    .mediaType("xml", MediaType.APPLICATION_XML);
        */

        /* VIA HEADER */
        configurer.favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);

    }
    
}