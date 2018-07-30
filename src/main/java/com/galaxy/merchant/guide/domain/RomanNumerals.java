package com.galaxy.merchant.guide.domain;

/**
 * Enumeration of Roman Numerals
 *
 * @author Gayathri Thiyagarajan
 */
public enum RomanNumerals {
    Z(0),
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    private Integer numericValue;

    RomanNumerals(Integer numericValue) {
        this.numericValue = numericValue;
    }

    public Integer numericValue() {
        return numericValue;
    }

    //True if this less than nextPos; False if greater than nextPos
    public boolean compare(RomanNumerals letterAtNextPos) {
        return this.numericValue < letterAtNextPos.numericValue;
    }

}
