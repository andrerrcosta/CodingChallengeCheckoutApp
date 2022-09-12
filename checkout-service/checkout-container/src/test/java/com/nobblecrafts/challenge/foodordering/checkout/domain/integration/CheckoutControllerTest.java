package com.nobblecrafts.challenge.foodordering.checkout.domain.integration;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper.BasketDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository.BasketJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.repository.BasketRedisCrudRepository;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.repository.CustomerJpaRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.config.TestRedisConfiguration;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketItemRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.util.DtoSupplier;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestRedisConfiguration.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
@Rollback(value = false)
public class CheckoutControllerTest {

    /**
     * These tests cannot be run individually.
     * They must follow the name ascending order to work
     */

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private BasketRedisCrudRepository basketRedisCrudRepository;
    @Autowired
    private BasketJpaRepository basketJpaRepository;
    private BasketDaoMapper basketDaoMapper = BasketDaoMapper.INSTANCE;


    @Test
    @DisplayName("Testing adding a single product with wrong customer")
    public void A_testAddItemWrongCustomer() {
        var req = DtoSupplier.randomBasketItemRequest(1L);
        log.info("\n\nREQUESTING: {}\n\n", req);
        var res = addItem(req);
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Testing checkout a basket without products")
    public void A_testCheckoutWithoutProducts() {
        saveCustomer(CustomerEntity.builder()
                .id(1L)
                .name("Test Customer")
                .build());
        var res = checkout(1L);
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Testing adding a single product")
    public void B0_testAddACorrectProduct() {


        var req = DtoSupplier.basketItemRequest(1L, "Boring Fries");
        var res = addItem(req);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody().items().size()).isEqualTo(1);
        Assertions.assertThat(res.getBody().totalPayable()).isEqualTo(res.getBody().total());
    }

    @Test
    @DisplayName("Testing adding a single product with wrong id")
    public void B1_testAddAWrongProduct() {

        var req = BasketItemRequest.builder()
                .customerId(1L)
                .productId("gadsasgasgasgasg")
                .build();
        var res = addItem(req);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Checking dbs for persisted entities")
    @Transactional(readOnly = true)
    public void B2_checkCacheAndDatabase() {
        var allCached = basketRedisCrudRepository.findAll();
        var allPersisted = basketJpaRepository.findAll();
        log.info("\n\nAll Cached: {}", allCached);
        log.info("All Persisted: {}\n\n", allPersisted);
        var cachedBasket = basketRedisCrudRepository
                .findById(basketDaoMapper.toBasketRedisId(new CustomerId(1L)));
        Assertions.assertThat(cachedBasket.isPresent()).isEqualTo(true);
        var persistedBasket = basketJpaRepository.findById(cachedBasket.get().getBasketId());
        Assertions.assertThat(persistedBasket.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("Testing add enough products to ensure a promotion")
    public void C0_testAddEnoughProductsToEnsurePromotion() {
        var p1 = DtoSupplier.basketItemRequest(1L, "Amazing Pizza");
        var p2 = DtoSupplier.basketItemRequest(1L, "Amazing Pizza");
        var r1 = addItem(p1);
        var b1 = r1.getBody();
        Assertions.assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(b1.items().size()).isEqualTo(2);
        Assertions.assertThat(b1.totalPromos()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(b1.totalPayable()).isEqualTo(b1.total());
        var r2 = addItem(p2);
        var b2 = r2.getBody();
        log.info("\n\nR2: {}\n\n", r2);
        log.info("\n\nB2: {}\n\n", b2);
        Assertions.assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(b2.items().size()).isEqualTo(2);
        Assertions.assertThat(b2.items().stream()
                        .filter(i -> i.product().equals("Amazing Pizza!"))
                        .findAny()
                        .get()
                        .quantity())
                .isEqualTo(2);
        Assertions.assertThat(b2.totalPromos()).isEqualTo(BigDecimal.valueOf(3.99));
        Assertions.assertThat(b2.total()).isEqualTo(b2.totalPayable().add(b2.totalPromos()));
    }

    @Test
    @DisplayName("Testing add enough products to Ensure a promotion")
    public void C1_testPromotionsWithNonLinearAddings() {
        var p1 = DtoSupplier.basketItemRequest(1L, "Amazing Pizza");
        var p2 = DtoSupplier.basketItemRequest(1L, "Boring Fries");
        var p3 = DtoSupplier.basketItemRequest(1L, "Amazing Pizza");
        var r1 = addItem(p1);
        var b1 = r1.getBody();
        Assertions.assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(b1.items().size()).isEqualTo(2);
        Assertions.assertThat(b1.items().stream()
                        .filter(i -> i.product().equals("Amazing Pizza!"))
                        .findAny()
                        .get()
                        .quantity())
                .isEqualTo(3);
        log.info("Assert promotions still the same");
        Assertions.assertThat(b1.totalPromos()).isEqualTo(BigDecimal.valueOf(3.99));
        Assertions.assertThat(b1.total()).isEqualTo(b1.totalPayable().add(b1.totalPromos()));

        var r2 = addItem(p2);
        var b2 = r2.getBody();
        log.info("\n\nR2: {}\n\n", r2);
        log.info("\n\nB2: {}\n\n", b2);
        Assertions.assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(b2.items().size()).isEqualTo(2);
        Assertions.assertThat(b2.items().stream()
                        .filter(i -> i.product().equals("Boring Fries!"))
                        .findAny()
                        .get()
                        .quantity())
                .isEqualTo(2);
        log.info("Assert promotions still the same");
        Assertions.assertThat(b2.totalPromos()).isEqualTo(BigDecimal.valueOf(3.99));
        Assertions.assertThat(b2.total()).isEqualTo(b2.totalPayable().add(b2.totalPromos()));

        var r3 = addItem(p3);
        var b3 = r3.getBody();
        log.info("\n\nR3: {}\n\n", r3);
        log.info("\n\nB3: {}\n\n", b3);
        Assertions.assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(b3.items().size()).isEqualTo(2);
        Assertions.assertThat(b3.items().stream()
                        .filter(i -> i.product().equals("Amazing Pizza!"))
                        .findAny()
                        .get()
                        .quantity())
                .isEqualTo(4);
        log.info("Assert promotions was applied");
        Assertions.assertThat(b3.totalPromos()).isEqualTo(BigDecimal.valueOf(7.98));
        Assertions.assertThat(b3.total()).isEqualTo(b3.totalPayable().add(b3.totalPromos()));
    }

    @Test
    @DisplayName("Test add to new customer basket")
    public void D0_testAddItemToDifferentBasket() {
        saveCustomer(CustomerEntity.builder()
                .id(2L)
                .name("Test Customer 2")
                .build());

        var req = DtoSupplier.basketItemRequest(2L, "Boring Fries");
        var res = addItem(req);

        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(res.getBody().items().size()).isEqualTo(1);
        Assertions.assertThat(res.getBody().totalPayable()).isEqualTo(res.getBody().total());

        var previousBasket = basketRedisCrudRepository.findById(basketDaoMapper.toBasketRedisId(new CustomerId(1L)));
        Assertions.assertThat(previousBasket.isPresent()).isEqualTo(true);
        var b3 = previousBasket.get();
        Assertions.assertThat(b3.getItems().size()).isEqualTo(2);
        Assertions.assertThat(b3.getItems().stream()
                        .filter(i -> i.getProductName().equals("Amazing Pizza!"))
                        .findAny()
                        .get()
                        .getQuantity())
                .isEqualTo(4);
        log.info("Assert promotions still the same");
        Assertions.assertThat(b3.getPromo()).isEqualTo(BigDecimal.valueOf(7.98));
        Assertions.assertThat(b3.getPrice()).isEqualTo(b3.getTotalPayable().add(b3.getPromo()));
    }

    @Test
    @DisplayName("Test checkout 1")
    @Transactional(readOnly = true)
    public void E0_testCheckoutCustomer1() {
        var r1 = checkout(1L);
        Assertions.assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
        var b1 = r1.getBody();
        log.info("\n\nCHECKOUT RESPONSE: {}\n\n", b1);
        var redisEntities = basketRedisCrudRepository.findAll();
        var entities = basketJpaRepository.findAll();
        log.info("\n\nREDIS: {}\n\n", redisEntities);
        log.info("\n\nENTITIES: {}\n\n", entities);

        log.info("Asserting the only redis entity is the second one");
        Assertions.assertThat(redisEntities.size()).isEqualTo(1);
        var e = redisEntities.stream()
                .findAny().get();
        Assertions.assertThat(basketDaoMapper.toCustomerId(e.getId()).getValue())
                .isEqualTo(2L);

        log.info("Asserting the only entity persisted is the first one");
        Assertions.assertThat(entities.size()).isEqualTo(1);
        Assertions.assertThat(entities.get(0).getCustomer().getId()).isEqualTo(1L);
        Assertions.assertThat(entities.get(0).getCustomer().getName()).isEqualTo("Test Customer");
        Assertions.assertThat(entities.get(0).getProductIds().size()).isEqualTo(6);
        Assertions.assertThat(entities.get(0).getTotal()).isEqualTo(BigDecimal.valueOf(47.94));
        Assertions.assertThat(entities.get(0).getTotalPayable()).isEqualTo(BigDecimal.valueOf(39.96));
        Assertions.assertThat(entities.get(0).getTotalPromos()).isEqualTo(BigDecimal.valueOf(7.98));
    }

    @Test
    @DisplayName("Test checkout 2")
    @Transactional(readOnly = true)
    public void E1_testCheckoutCustomer2() {
        var r1 = checkout(2L);
        Assertions.assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.OK);
        var b1 = r1.getBody();
        log.info("\n\nCHECKOUT RESPONSE: {}\n\n", b1);
        var redisEntities = basketRedisCrudRepository.findAll();
        var entities = basketJpaRepository.findAll();
        log.info("\n\nREDIS: {}\n\n", redisEntities);
        log.info("\n\nENTITIES: {}\n\n", entities);

        log.info("Asserting theres no cached entities anymore");
        Assertions.assertThat(redisEntities.size()).isEqualTo(0);


        log.info("Asserting theres two entities persisted");
        Assertions.assertThat(entities.size()).isEqualTo(2);
        Assertions.assertThat(entities.get(0).getCustomer().getId()).isEqualTo(1L);
        Assertions.assertThat(entities.get(0).getCustomer().getName()).isEqualTo("Test Customer");
        Assertions.assertThat(entities.get(0).getProductIds().size()).isEqualTo(6);
        Assertions.assertThat(entities.get(0).getTotal()).isEqualTo(BigDecimal.valueOf(47.94));
        Assertions.assertThat(entities.get(0).getTotalPayable()).isEqualTo(BigDecimal.valueOf(39.96));
        Assertions.assertThat(entities.get(0).getTotalPromos()).isEqualTo(BigDecimal.valueOf(7.98));

        Assertions.assertThat(entities.get(1).getCustomer().getId()).isEqualTo(2L);
        Assertions.assertThat(entities.get(1).getCustomer().getName()).isEqualTo("Test Customer 2");
        Assertions.assertThat(entities.get(1).getProductIds().size()).isEqualTo(1);
        Assertions.assertThat(entities.get(1).getTotalPayable()).isEqualTo(entities.get(1).getTotal());
    }

    @Test
    @DisplayName("Clean Database")
    public void F0_cleaningDatabase() {
        customerJpaRepository.deleteAll();
        var entities = basketJpaRepository.findAll();
        Assertions.assertThat(entities.size()).isEqualTo(0);
    }

    private ResponseEntity<BasketResponse> addItem(BasketItemRequest request) {
        return template.exchange("/add-item",
                HttpMethod.POST,
                new HttpEntity<>(request),
                BasketResponse.class);
    }

    private ResponseEntity<BasketResponse> checkout(Long customerId) {
        return template.exchange("/checkout/" + customerId,
                HttpMethod.POST,
                null,
                BasketResponse.class);
    }

    @Transactional
    private void saveCustomer(CustomerEntity entity) {
        customerJpaRepository.save(entity);
    }
}
