package com.nobblecrafts.challenge.foodordering.checkout.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableJpaRepositories(basePackages = { "com.nobblecrafts.challenge.foodordering.checkout.dataaccess",
        "com.nobblecrafts.challenge.foodordering.dataaccess" })
@EntityScan(basePackages = { "com.nobblecrafts.challenge.foodordering.checkout.dataaccess",
        "com.nobblecrafts.challenge.foodordering.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.nobblecrafts.challenge.foodordering")
public class CheckoutServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckoutServiceApplication.class, args);
    }
}
