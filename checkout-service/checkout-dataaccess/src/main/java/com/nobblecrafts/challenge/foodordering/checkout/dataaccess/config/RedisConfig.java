package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    private final CheckoutRedisConfigData configData;

    @Autowired
    public RedisConfig(CheckoutRedisConfigData configData) {
        this.configData = configData;
    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(configData.getHost());
        configuration.setPort(configData.getPort());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate() {
        final RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

}
