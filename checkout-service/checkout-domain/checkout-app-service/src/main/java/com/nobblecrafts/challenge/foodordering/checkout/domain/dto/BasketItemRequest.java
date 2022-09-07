package com.nobblecrafts.challenge.foodordering.checkout.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record BasketItemRequest(@NotNull String productId,
                                @NotNull Long customerId) {
}
