package ua.nure.poliakov.SummaryTask4.utils.exceptions;

/**
 * An exception that provides information on a validation error.
 */

public class ValidationException extends PeriodicalsException {

    public ValidationException(){}

    public ValidationException(String message){
        super(message);
    }

    public ValidationException(String message, Throwable clause){
        super(message, clause);
    }
}
