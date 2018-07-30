package com.galaxy.merchant.guide.exceptions;

/**
 * Exception class to define invalid queries
 *
 * @author Gayathri Thiyagarajan
 */
public class InvalidQueryException extends Throwable {

    private String errorMessage;

    public InvalidQueryException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
