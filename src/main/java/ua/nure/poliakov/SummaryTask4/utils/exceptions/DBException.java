package ua.nure.poliakov.SummaryTask4.utils.exceptions;

/**
 * An exception that provides information on a DB error
 */

public class DBException extends PeriodicalsException {

    public DBException(){}

    public DBException(String message){
        super(message);
    }

    public DBException(String message, Throwable clause){
        super(message, clause);
    }
}
