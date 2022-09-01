package com.nobblecrafts.challenge.foodordering.checkout.domain.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketRequest;
import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.BasketResponse;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default")
public abstract class CheckoutDataMapper {
    public static final CheckoutDataMapper INSTANCE = Mappers.getMapper(CheckoutDataMapper.class);

    @Mapping(source = "id", target = "customerId.value")
    @Mapping(source = "price", target = "price.amount")
    public abstract Basket toBasket(BasketRequest request);

    @Mapping(source = "id", target = "customerId.value")
    @Mapping(source = "price", target = "price.amount")
    public abstract BasketResponse toBasketResponse(Basket basket);

}
