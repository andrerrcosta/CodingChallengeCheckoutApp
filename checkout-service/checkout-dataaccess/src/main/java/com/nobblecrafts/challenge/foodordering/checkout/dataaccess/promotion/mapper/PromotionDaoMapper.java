package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.promotion.entity.PromotionRedisEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Promotion;
import com.nobblecrats.challenge.foodordering.domain.mapper.ObjectValueMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "default", uses = ObjectValueMapper.class)
public abstract class PromotionDaoMapper {
    public static PromotionDaoMapper INSTANCE = Mappers.getMapper(PromotionDaoMapper.class);

    @Mapping(source = "id", target = "id.value")
    public abstract Promotion toPromotion(PromotionRedisEntity entity);

    public abstract Set<Promotion> toPromotion(Iterable<PromotionRedisEntity> entity);

    @Mapping(target = "id", source = "id.value")
    public abstract PromotionRedisEntity toPromotionRedisEntity(Promotion promotion);

    public abstract Set<PromotionRedisEntity> toPromotionRedisEntity(Iterable<Promotion> promotion);
}
