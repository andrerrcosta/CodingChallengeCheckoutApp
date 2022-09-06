package com.nobblecrafts.challenge.foodordering.checkout.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Builder
public record BasketResponse(@NotNull List<BasketItemResponse> items,
                             @NotNull BigDecimal total,
                             @NotNull BigDecimal totalPromos,
                             @NotNull BigDecimal totalPayable) {
}
