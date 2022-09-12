package com.nobblecrafts.challenge.foodordering.checkout.domain.jpa;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository.BasketRedisCrudRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.config.TestRedisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@Rollback(false)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        classes = TestRedisConfiguration.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@Slf4j
public class BasketRedisCrudRepositoryTest {

    @Autowired
    private BasketRedisCrudRepository basketRedisCrudRepository;
    @Autowired
    private BasketRedisCrudRepository crudRepository;

    /**
     * I will create this tests tomorrow
     */
}
