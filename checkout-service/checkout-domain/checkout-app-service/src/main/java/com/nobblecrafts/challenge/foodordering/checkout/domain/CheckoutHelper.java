package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.BasketRepository;
import com.nobblecrafts.challenge.foodordering.checkout.domain.port.output.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckoutHelper {
    private final BasketRepository basketRepository;
    private final PromotionRepository promotionRepository;

    @Transactional
    public void persistBasket(BasketRequest request) {
        Basket basket = applyPromotionsIfPossible(request);
    }

    private Basket applyPromotionsIfPossible(BasketRequest request) {
        return null;
    }
}
