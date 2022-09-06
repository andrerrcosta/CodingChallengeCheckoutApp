package com.nobblecrafts.challenge.foodordering.checkout.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "checkout-service")
public class CheckoutServiceConfigData {
    private String apiUrl;
}
