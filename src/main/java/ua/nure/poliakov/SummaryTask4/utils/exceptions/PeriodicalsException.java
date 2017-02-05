package ua.nure.poliakov.SummaryTask4.utils.exceptions;

/**
 * An exception that provides information on an application error.
 */

public class PeriodicalsException extends Exception {

    public PeriodicalsException() {
    }

    public PeriodicalsException(String message) {
        super(message);
    }

    public PeriodicalsException(String message, Throwable clause) {
        super(message, clause);
    }
}
