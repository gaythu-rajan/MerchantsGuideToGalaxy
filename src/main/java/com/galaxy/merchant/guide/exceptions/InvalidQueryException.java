package com.galaxy.merchant.guide.exceptions;

/**
 * Exception class to catch invalid queries
 *
 * @author Gayathri Thiyagarajan
 */
public class InvalidQueryException extends Throwable {
    String errorMessage;

    public InvalidQueryException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
