package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Product;
import com.nobblecrafts.challenge.foodordering.dataaccess.mapper.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "default", uses = MapperUtils.class)
public abstract class ProductDaoMapper {

    public static final ProductDaoMapper INSTANCE = Mappers.getMapper(ProductDaoMapper.class);

    @Mapping(source = "id.value", target = "id")
    public abstract ProductEntity toProductEntity(Product product);

    @Mapping(target = "id.value", source = "id")
    public abstract Product toProduct(ProductEntity entity);
}
