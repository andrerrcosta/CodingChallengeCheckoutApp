//package com.nobblecrafts.challenge.foodordering.checkout.domain.jpa;

import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketEntityTest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.util.EntitySupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@DataJpaTest
//@ActiveProfiles("test")
//@Rollback(false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@Slf4j
//public class BasketEntityJpaRepository {
//
//    @Autowired
//    private BasketEntityTest repository;
//    @Autowired
//    private static TestEntityManager entityManager;
//
//    @BeforeClass
//    public static void setup() {
//        entityManager.persistAndFlush(EntitySupplier.customerEntity(1L));
//    }

//    @Test
//    public void A0_
//}
