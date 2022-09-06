package com.nobblecrafts.challenge.foodordering.checkout.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;


public record PromotionDto(@NotNull String id,
                           @NotNull String type,
// for BUY_X_GET_Y_FREE
                           @JsonProperty(value = "required_qty")
                           Integer requiredQuantity,
                           @JsonProperty(value = "free_qty")
                           Integer freeQuantity,
// for FLAT_PERCENT
                           Float amount,
// for QTY_BASED_PRICE_OVERRIDE
                           Integer price) {

}
