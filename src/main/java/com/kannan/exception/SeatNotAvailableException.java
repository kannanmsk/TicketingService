package com.kannan.exception;

/**
 * Exception to be thrown when Seats are not available
 * @author Kannan Kuttalam
 *
 */
public class SeatNotAvailableException extends Exception {

    public SeatNotAvailableException(String message) {
        super(message);
    }
}
