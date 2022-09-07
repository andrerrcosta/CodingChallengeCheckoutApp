package com.nobblecrafts.challenge.foodordering.checkout.domain;

import com.nobblecrafts.challenge.foodordering.checkout.domain.config.CheckoutServiceConfigData;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketItemRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.ProductDto;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketItem;
import com.nobblecrafts.challenge.foodordering.checkout.domain.exception.CheckoutNotFoundException;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDataMapper;
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

    public BasketItem scanBasketItem(BasketItemRequest request) {
        String url = String.format("%s/products/%s", configData.getApiUrl(), request.productId());
        var product = restTemplate.getForEntity(url, ProductDto.class).getBody();
        if (product == null)
            throw new CheckoutNotFoundException("Product with id '" + request.productId() + "' not found");
        return mapper.toBasketItem(product);
    }


}
