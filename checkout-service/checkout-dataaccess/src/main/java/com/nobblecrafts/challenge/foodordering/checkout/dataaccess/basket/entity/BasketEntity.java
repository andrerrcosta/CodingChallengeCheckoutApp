package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity.CustomerEntity;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.product.entity.ProductEntity;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "basket")
@Entity
public class BasketEntity {
    @Id
    private UUID id;
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "basket-products")
    @JoinTable(
            name = "basket_products",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> items = new LinkedList<>();

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "customer-baskets")
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketEntity that = (BasketEntity) o;
        return id.equals(that.id) && customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer);
    }

    public void addProduct(ProductEntity product) {
        this.items.add(product);
        product.getBaskets().add(this);
    }

    public void removeProduct(ProductEntity product) {
        this.items.remove(product);
        product.getBaskets().remove(this);
    }

    public void removeAllProducts() {
        Iterator<ProductEntity> iterator = this.items.iterator();
        while(iterator.hasNext()) {
            ProductEntity product = iterator.next();
            product.getBaskets().remove(this);
            iterator.remove();
        }
    }

}
