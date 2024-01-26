package com.bassmania.msstoreorders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@OpenAPIDefinition(info = @Info(
        title = "E-Bass Store Orders API",
        description = "API para la gestión de pedidos de la tienda de bajos eléctricos y accesorios.",
        version = "1.0.0"))
public class MsStoreOrdersApplication {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        // Retrieve execution profile from environment variable. If not present, default profile is selected.
        String profile = System.getenv("PROFILE");
        System.setProperty("spring.profiles.active", profile != null ? profile : "default");
        SpringApplication.run(MsStoreOrdersApplication.class, args);
    }
}
