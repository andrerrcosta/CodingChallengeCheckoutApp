package com.nobblecrafts.challenge.foodordering.checkout.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.BasketItem;
import com.nobblecrafts.challenge.foodordering.checkout.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
public record BasketRequest(@NotNull Long customerId,
                            @NotNull BigDecimal price,
                            @NotNull List<BasketItem> items) {
}
