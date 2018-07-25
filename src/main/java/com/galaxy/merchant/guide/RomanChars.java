package com.galaxy.merchant.guide;

/**
 * Roman Characters Enumeration
 *
 * @author Gayathri Thiyagarajan
 */
public enum RomanChars {
    Z(0),
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    private Integer numericValue;

    RomanChars(Integer numericValue) {
        this.numericValue = numericValue;
    }

    public Integer numericValue() {
        return numericValue;
    }

    //True if this less than nextPos; False if greater than nextPos
    public boolean compare(RomanChars letterAtNextPos) {
        return this.numericValue < letterAtNextPos.numericValue;
    }

}
