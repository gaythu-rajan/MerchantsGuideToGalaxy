package com.galaxy.merchant.guide.exceptions;

/**
 * Exception class to catch invalid formats
 *
 * @author Gayathri Thiyagarajan
 */
public class InvalidInputFormatException extends Exception {

    private String errorMessage;

    public InvalidInputFormatException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
