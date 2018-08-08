package com.valhala.universidade.alunos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlunosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlunosServiceApplication.class, args);
    }

    /*
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("POST", "GET", "PUT", "DELETE", "PATCH");
            }
        };
    }
    */
}
