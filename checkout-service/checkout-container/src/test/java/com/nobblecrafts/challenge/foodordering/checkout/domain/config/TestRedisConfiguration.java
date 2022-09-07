package com.nobblecrafts.challenge.foodordering.checkout.domain.config;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.config.CheckoutRedisConfigData;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
@EnableRedisRepositories
//@ActiveProfiles("test")
public class TestRedisConfiguration {

    private RedisServer redisServer;

    public TestRedisConfiguration(CheckoutRedisConfigData configData) {
        this.redisServer = new RedisServer(configData.getPort());
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
