package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "checkout-redis")
public class CheckoutRedisConfigData {
    private int port;
    private String host;
}
