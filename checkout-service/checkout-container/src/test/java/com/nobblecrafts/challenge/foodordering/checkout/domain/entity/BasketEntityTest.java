package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.util.DtoSupplier;
import com.nobblecrafts.challenge.foodordering.checkout.domain.util.EntitySupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class BasketEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    private static final BasketEntity basket1 = EntitySupplier.basketEntity();
    private static final BasketEntity basket2 = EntitySupplier.basketEntity();
    private static final CustomerEntity customer1 = EntitySupplier.customerEntity(1L);
    private static final CustomerEntity customer2 = EntitySupplier.customerEntity(2L);
    private static final Set<BasketEntity> baskets = new HashSet<>();

    /**
     * The tests above were created to ensure the equality between the transient
     * entity of the set against the different state transitions
     */

    @BeforeClass
    public static void setUp() {
        baskets.add(basket1);
        log.info("\n\nBEFORE SHOULD BE DONE\n\n");
    }

    /**
     * @Part 1: This entity should be found by the hash
     * (id, customer) so, if the transient entity does not
     * contain any customer, after the customer including
     * all the states should have different hashes
     */

    @Test
    @DisplayName("Transient state on HashSet")
    public void A_assertTheEntityExists() {
        assertTrue(baskets.contains(basket1));
    }


    @Test
    @DisplayName("Managed Against transient shouldn't not be found")
    public void B_testTransientToManaged() {
        assertNotNull(basket1.getId());
        log.info("\nTransient entity: {}\n\n", basket1);
        var customer = entityManager
                .persistAndFlush(customer1);
        customer.addBasket(basket1);

        var x = entityManager.persist(customer);
        log.info("\n\nBefore Flush: {}\n\n", x.getBaskets().stream().findFirst());
        entityManager.flush();

        assertNotNull(basket1.getId());
        log.info("\n\nManaged entity: {}\n\n", basket1);
        assertFalse(baskets.contains(basket1));
        log.info("\n\nThis assert should be correct\n\n");
    }

    @Test
    @DisplayName("Managed Against transient shouldn't not be found (Merge)")
    // Find in Set the entity after merge() was called - SELECT and UPDATE statement
    // State transition at assertion point: MANAGED
    public void C_stillManagedAgainsTransient() {

        basket1.addItems(DtoSupplier.randomBasketItemRequest(1L).productId(), 1);
        var merged = entityManager.merge(basket1);
        entityManager.flush();

        assertFalse(baskets.contains(merged));
    }

    @Test
    // Find in Set the entity after find() was called - SELECT statement
    // State transition at assertion point: MANAGED
    @DisplayName("Managed Against transient shouldn't not be found again")
    public void D_stillManagedAgainsTransient() {
        var found = entityManager
                .find(BasketEntity.class, basket1.getId());
        entityManager.flush();

        assertFalse(baskets.contains(found));
    }

    @Test
    // Find in Set the entity after detach() was called
    // State transition at assertion point: DETACHED
    @DisplayName("Detached Against transient shouldn't not be found")
    public void E_detachedAgainstTransient() {
        var found = entityManager
                .find(BasketEntity.class, basket1.getId());
        entityManager.detach(found);

        assertFalse(baskets.contains(found));
    }

    @Test
    // Find in Set the entity after remove() was called - DELETE statement
    // State transition at assertion points: REMOVED
    @DisplayName("Removed Agains transient shouldn't not be found")
    public void F_removedAgainstTransient() {
        var found = entityManager
                .find(BasketEntity.class, basket1.getId());
        entityManager.remove(found);
        entityManager.flush();

        assertFalse(baskets.contains(found));

        baskets.remove(found);

        assertFalse(baskets.contains(found));
    }

    /**
     * @Part 2: This entity should be found by the hash
     * (id, customer) so, if the transient entity
     * contains the same customer (with the same id)
     * all the states should have the same hashes
     */

    @Test
    @DisplayName("Second basket, Transient state on HashSet")
    public void G_assertTheEntityExists() {
        customer2.addBasket(basket2);
        baskets.add(basket2);
        assertTrue(baskets.contains(basket2));
    }


    @Test
    @DisplayName("Second basket, Transient to Managed, shoud be found")
    public void H_testTransientToManaged() {
        assertNotNull(basket2.getId());
        log.info("\nTransient entity: {}\n\n", basket2);

        var x = entityManager.persist(customer2);
        log.info("\n\nBefore Flush: {}\n\n", x.getBaskets().stream().findFirst());
        entityManager.flush();

        assertNotNull(basket2.getId());
        log.info("\n\nManaged entity: {}\n\n", basket2);
        assertTrue(baskets.contains(basket2));
        log.info("\n\nThis assert should be correct\n\n");
    }

    @Test
    // Find in Set the entity after merge() was called - SELECT and UPDATE statement
    // State transition at assertion point: MANAGED
    @DisplayName("Second basket, Managed agains transient, shoud be found (Merged)")
    public void I_givenEntityWhenMergeThenSuccess() {

        basket2.addItems(DtoSupplier.randomBasketItemRequest(1L).productId(), 1);
        var merged = entityManager.merge(basket2);
        entityManager.flush();

        assertTrue(baskets.contains(merged));
    }

    @Test
    // Find in Set the entity after find() was called - SELECT statement
    // State transition at assertion point: MANAGED
    @DisplayName("Second basket, Managed agains transient, shoud be found 2")
    public void J_givenEntityWhenFindThenSuccess() {
        var found = entityManager
                .find(BasketEntity.class, basket2.getId());
        entityManager.flush();

        assertTrue(baskets.contains(found));
    }

    @Test
    // Find in Set the entity after detach() was called
    // State transition at assertion point: DETACHED
    @DisplayName("Second basket, Detached agains transient, shoud be found")
    public void K_givenEntityWhenFindAndDetachThenSuccess() {
        var found = entityManager
                .find(BasketEntity.class, basket2.getId());
        entityManager.detach(found);

        assertTrue(baskets.contains(found));
    }

    @Test
    // Find in Set the entity after remove() was called - DELETE statement
    // State transition at assertion points: REMOVED
    @DisplayName("Second basket, Removed agains transient, shoud be found")
    public void L_givenEntityWhenFindAndRemoveThenSuccess() {
        var found = entityManager
                .find(BasketEntity.class, basket2.getId());
        entityManager.remove(found);
        entityManager.flush();

        assertTrue(baskets.contains(found));

        baskets.remove(found);

        assertFalse(baskets.contains(found));
    }


}
