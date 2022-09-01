package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Basket;
import com.nobblecrafts.challenge.foodordering.dataaccess.mapper.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = MapperUtils.class)
public abstract class BasketDaoMapper {
    public static final BasketDaoMapper INSTANCE = Mappers.getMapper(BasketDaoMapper.class);

    @Mapping(source = "id.value", target = "id")
    public abstract BasketEntity toBasketEntity(Basket basket);

    @Mapping(target = "id.value", source = "id")
    public abstract Basket toBasket(BasketEntity entity);


}
