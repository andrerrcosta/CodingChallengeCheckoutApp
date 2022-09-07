package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository.CustomerJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class CheckoutAppTests {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Test
    @Transactional
    public void A_createUserBeforeTests() {
        customerJpaRepository.save(CustomerEntity.builder()
                        .id(1L)
                        .name("Test Customer")
                .build());
    }


    @Test
    public void B_testApi() {
//        var req = BasketRequest.builder()
//                .itemIds(List.of("PWWe3w1SDU", "PWWe3w1SDU", "PWWe3w1SDU",
//                        "Dwt5F7KAhi", "Dwt5F7KAhi", "Dwt5F7KAhi",
//                        "4MB7UfpTQs", "4MB7UfpTQs", "C8GDyLrHJb"))
//                .customerId(1L)
//                .build();
//
//        var x = template.exchange("http://localhost:8081/products",
//                HttpMethod.GET,
//                new HttpEntity<>(req),
//                BasketResponse[].class);
//        Arrays.stream(x.getBody()).forEach(System.out::println);
//        log.info("products: {}", x.getBody());
    }

}
