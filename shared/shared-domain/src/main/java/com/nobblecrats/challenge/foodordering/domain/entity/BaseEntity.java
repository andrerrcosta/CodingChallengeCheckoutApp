package com.nobblecrats.challenge.foodordering.domain.entity;

import com.nobblecrats.challenge.foodordering.domain.objectvalue.PromotionId;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@SuperBuilder
public abstract class BaseEntity<ID> {
    private ID id;

    public BaseEntity(PromotionId promotionId) {
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

