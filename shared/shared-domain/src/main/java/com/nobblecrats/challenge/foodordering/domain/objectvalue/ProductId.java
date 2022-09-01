package com.nobblecrats.challenge.foodordering.domain.objectvalue;

import java.util.UUID;

public class ProductId extends BaseId<UUID> {
    public ProductId(UUID value) {
        super(value);
    }
}