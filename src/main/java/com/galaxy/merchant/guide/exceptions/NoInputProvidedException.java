package com.galaxy.merchant.guide.exceptions;

/**
 * Exception class to catch operations when there are no inputs
 *
 * @author Gayathri Thiyagarajan
 */
public class NoInputProvidedException extends Exception {

    private String errorMessage;

    public NoInputProvidedException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
