package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketItem;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDomainMapper;
import com.nobblecrats.challenge.foodordering.domain.mapper.ObjectValueMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = {ObjectValueMapper.class, CheckoutDomainMapper.class})
public abstract class BasketDaoMapper {
    public static final BasketDaoMapper INSTANCE = Mappers.getMapper(BasketDaoMapper.class);

    public BasketEntity toBasketEntity(Basket basket) {
        var entity = BasketEntity.builder()
                .id(basket.getId().getValue())
                .price(basket.getPrice().getAmount())
                .build();
        basket.getItems().forEach(i -> {
            for(var j = 0; j < i.getQuantity(); j++) {
                entity.getItems().add(i.getProductId());
            }
        });
        return entity;
    }
}
