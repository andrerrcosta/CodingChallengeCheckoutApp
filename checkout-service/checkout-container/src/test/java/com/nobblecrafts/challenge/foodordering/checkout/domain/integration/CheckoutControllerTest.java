package com.nobblecrafts.challenge.foodordering.checkout.domain.integration;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository.BasketJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository.CustomerJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
@Rollback(value = false)
public class CheckoutControllerTest {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private BasketJpaRepository basketJpaRepository;




    @Test(expected = RestClientException.class)
    @Transactional
    public void A_testCheckoutWithWrongCustomer() {
        var req = BasketRequest.builder()
                .itemIds(List.of("PWWe3w1SDU", "PWWe3w1SDU", "PWWe3w1SDU",
                        "Dwt5F7KAhi", "Dwt5F7KAhi", "Dwt5F7KAhi",
                        "4MB7UfpTQs", "4MB7UfpTQs", "C8GDyLrHJb"))
                .customerId(1L)
                .build();
        var res = template.exchange("/checkout",
                HttpMethod.POST,
                new HttpEntity<>(req),
                BasketResponse[].class);
    }

    @Test
    public void B0_testCheckoutWithManyProducts() {
        customerJpaRepository.save(CustomerEntity.builder()
                .id(1L)
                .name("Test Customer")
                .build());

        var req = BasketRequest.builder()
                .itemIds(List.of("PWWe3w1SDU", "PWWe3w1SDU", "PWWe3w1SDU",
                        "Dwt5F7KAhi", "Dwt5F7KAhi", "Dwt5F7KAhi",
                        "4MB7UfpTQs", "4MB7UfpTQs", "C8GDyLrHJb"))
                .customerId(1L)
                .build();
        var res = template.exchange("/checkout",
                HttpMethod.POST,
                new HttpEntity<>(req),
                BasketResponse.class);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody().items().size()).isEqualTo(4);
        Assertions.assertThat(res.getBody().totalPayable()).isLessThan(res.getBody().total());
    }

    @Test
    @Transactional
    public void B1_testCheckoutWithoutPromotion() {
        var baskets = basketJpaRepository.findAll();
        baskets.forEach(basket -> log.info("\n\nBASKET: {}\n\n", basket));
//        log.info("\n\nAll Baskets {}\n\n", baskets);
    }
}
