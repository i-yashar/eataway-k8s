package com.example.basketservice;

import com.example.basketservice.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BasketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasketServiceApplication.class, args);
    }

}
