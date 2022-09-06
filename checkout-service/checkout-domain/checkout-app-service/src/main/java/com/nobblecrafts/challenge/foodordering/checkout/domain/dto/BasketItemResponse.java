package com.nobblecrafts.challenge.foodordering.checkout.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record BasketItemResponse(@NotNull String product,
                                 @NotNull Integer quantity) {
}
