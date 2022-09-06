package com.nobblecrafts.challenge.foodordering.checkout.domain.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.domain.dto.*;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketItem;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Promotion;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.PromotionType;
import com.nobblecrafts.challenge.foodordering.checkout.domain.objectvalue.BasketItemId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.BasketId;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.Money;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.PromotionId;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "default")
public abstract class CheckoutDataMapper {
    public static final CheckoutDataMapper INSTANCE = Mappers.getMapper(CheckoutDataMapper.class);

    @Mapping(source = "customerId", target = "customerId.value")
    public abstract Basket toBasket(BasketRequest request);

    @Mapping(source = "price.amount", target = "total")
    @Mapping(source = "promo.amount", target = "totalPromos")
    @Mapping(source = "totalPayable.amount", target = "totalPayable")
    public abstract BasketResponse toBasketResponse(Basket basket);

    @Mapping(source = "productName", target = "product")
    public abstract BasketItemResponse toBasketItemResponse(BasketItem item);

    public BasketItem toBasketItem(ProductDto dto) {
        return BasketItem.builder()
                .id(new BasketItemId(dto.id()))
                .productId(dto.id())
                .productName(dto.name())
                .price(Money.buildWithCents(dto.price()))
                .promotions(this.toPromotion(dto.promotions()))
                .build();
    }

    public Set<Promotion> toPromotion(PromotionDto[] promotions) {
        return Arrays.stream(promotions)
                .map(this::toPromotion)
                .collect(Collectors.toSet());
    }

    public Promotion toPromotion(PromotionDto dto) {
        return Promotion.builder()
                .type(PromotionType.valueOf(dto.type()))
                .id(new PromotionId(UUID.randomUUID()))
                .price(dto.price() != null ? Money.buildWithCents(dto.price()) : null)
                .requiredQuantity(dto.requiredQuantity())
                .freeQuantity(dto.freeQuantity())
                .amount(dto.amount() != null ? BigDecimal.valueOf(dto.amount()) : null)
                .build();
    }
}
