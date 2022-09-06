package com.nobblecrafts.challenge.foodordering.checkout.domain.entity;

public enum PromotionType {
    QTY_BASED_PRICE_OVERRIDE("QTY_BASED_PRICE_OVERRIDE"),
    BUY_X_GET_Y_FREE("BUY_X_GET_Y_FREE"),
    FLAT_PERCENT("FLAT_PERCENT");

    private final String text;
    /**
     * @param text
     */
    PromotionType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
