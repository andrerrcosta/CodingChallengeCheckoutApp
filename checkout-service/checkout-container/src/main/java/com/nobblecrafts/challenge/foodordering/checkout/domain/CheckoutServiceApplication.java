package com.nobblecrafts.challenge.foodordering.checkout.domain;


import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

@EnableJpaRepositories(basePackages = {"com.nobblecrafts.challenge.foodordering.checkout.dataaccess",
        "com.nobblecrafts.challenge.foodordering.dataaccess"})
@EntityScan(basePackages = {"com.nobblecrafts.challenge.foodordering.checkout.dataaccess",
        "com.nobblecrafts.challenge.foodordering.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.nobblecrafts.challenge.foodordering")
@RequiredArgsConstructor
public class CheckoutServiceApplication {

    private final CustomerJpaRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CheckoutServiceApplication.class, args);
    }

    @Bean
    @Transactional
    @Profile("!test")
    public CommandLineRunner run() {
        return args -> {
            repository.save(CustomerEntity.builder()
                    .name("Test Customer")
                    .id(1L)
                    .build());
        };
    }
}
