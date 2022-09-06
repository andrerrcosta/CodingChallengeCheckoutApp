package com.nobblecrafts.challenge.foodordering.checkout.domain.dto;

import lombok.ToString;

import javax.validation.constraints.NotNull;


public record ProductDto(@NotNull String id,
                         @NotNull String name,
                         @NotNull int price,
                         PromotionDto[] promotions) {
}
