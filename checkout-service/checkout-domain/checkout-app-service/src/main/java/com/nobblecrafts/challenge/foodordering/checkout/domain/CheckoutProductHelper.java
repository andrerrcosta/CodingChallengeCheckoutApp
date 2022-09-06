package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.config.CheckoutServiceConfigData;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.ProductDto;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDataMapper;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckoutProductHelper {
    private final CheckoutServiceConfigData configData;
    private final RestTemplate restTemplate;
    private final CheckoutDataMapper mapper = CheckoutDataMapper.INSTANCE;

    public Basket buildBasket(BasketRequest request) {
        var basket = Basket.builder()
                .customerId(new CustomerId(request.customerId()))
                .build();

        request.itemIds().forEach(id -> {
            String url = String.format("%s/products/%s", configData.getApiUrl(), id);
            var product = restTemplate.getForEntity(url, ProductDto.class).getBody();
            basket.addItem(mapper.toBasketItem(product));
        });
        return basket;
    }

}
