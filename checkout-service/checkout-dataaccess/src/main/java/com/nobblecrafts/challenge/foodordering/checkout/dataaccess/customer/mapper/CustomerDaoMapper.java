package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.mapper;

import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Customer;
import com.nobblecrats.challenge.foodordering.domain.objectvalue.CustomerId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Iterator;
import java.util.Set;

@Mapper(componentModel = "default")
public abstract class CustomerDaoMapper {
    public static final CustomerDaoMapper INSTANCE = Mappers.getMapper(CustomerDaoMapper.class);

    public abstract CustomerEntity toCustomerEntity(Customer customer);

    public abstract Customer toCustomer(CustomerEntity entity);

    public abstract Set<Customer> toCustomer(Iterable<CustomerEntity> entities);

    public Long fromCustomerIdToLong(CustomerId id) {
        return id.getValue();
    }

    public CustomerId fromLongToCustomerId(Long id) {
        return new CustomerId(id);
    }
}
