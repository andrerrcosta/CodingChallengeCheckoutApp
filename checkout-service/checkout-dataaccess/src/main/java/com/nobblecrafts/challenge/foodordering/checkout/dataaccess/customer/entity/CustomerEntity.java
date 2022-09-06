package com.nobblecrafts.challenge.foodordering.checkout.dataaccess.customer.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nobblecrafts.challenge.foodordering.checkout.dataaccess.basket.entity.BasketEntity;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Entity
@ToString
public class CustomerEntity {
    @Id
    private Long id;
    private String name;
    @Builder.Default
    @JsonManagedReference(value = "customer-baskets")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BasketEntity> baskets = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addBasket(BasketEntity basket) {
        this.baskets.add(basket);
        basket.setCustomer(this);
    }

    public void removeBasket(BasketEntity basket) {
        basket.setCustomer(null);
        this.baskets.remove(basket);
    }

    public void removeAllBaskets() {
        var iterator = this.baskets.iterator();
        while (iterator.hasNext()) {
            var basket = iterator.next();
            basket.setCustomer(null);
            iterator.remove();
        }
    }
}
