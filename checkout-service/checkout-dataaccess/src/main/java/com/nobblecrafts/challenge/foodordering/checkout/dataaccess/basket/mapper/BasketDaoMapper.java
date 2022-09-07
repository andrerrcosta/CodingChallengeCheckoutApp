package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketRedisEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductRedisEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.mapper.PromotionDaoMapper;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketItem;
import com.nobblecrafts.challenge.foodordering.checkout.domain.mapper.CheckoutDomainMapper;
import com.nobblecrats.challenge.foodordering.domain.mapper.ObjectValueMapper;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "default", uses = {ObjectValueMapper.class, CheckoutDomainMapper.class, PromotionDaoMapper.class})
public abstract class BasketDaoMapper {
    public static final BasketDaoMapper INSTANCE = Mappers.getMapper(BasketDaoMapper.class);


    @Mapping(source = "customerId", target = "id")
    @Mapping(source = "id.value", target = "basketId")
    public abstract BasketRedisEntity toBasketRedisEntity(Basket basket);

    @Mapping(source = "basketId", target = "id")
    @Mapping(source = "id", target = "customerId")
    public abstract Basket toBasket(BasketRedisEntity entity);

    public String toBasketRedisId(CustomerId id) {
        return "customer-basket-" + id.getValue();
    }

    public CustomerId toCustomerId(String id) {
        var number = id.replace("customer-basket-", "");
        return new CustomerId(Long.valueOf(number));
    }

    @Mapping(source = "id.value", target = "id")
    public abstract ProductRedisEntity toProductRedisEntity(BasketItem item);

    public abstract List<ProductRedisEntity> toProductRedisEntity(Iterable<BasketItem> items);

    public abstract BasketItem toBasketItem(ProductRedisEntity entity);


    public BasketEntity toBasketEntity(Basket basket) {
        var output = BasketEntity.builder()
                .id(basket.getId().getValue())
                .total(basket.getPrice().getAmount())
                .totalPayable(basket.getTotalPayable().getAmount())
                .totalPromos(basket.getPromo().getAmount())
                .build();
        basket.getItems()
                .forEach(item -> output.addItems(item.getProductId(), item.getQuantity()));
        return output;
    }



}
