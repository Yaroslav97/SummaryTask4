package ua.nure.poliakov.SummaryTask4.utils.validations.user;

import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;

/**
 *  Interface for validation User.
 * @param <T>
 * @param <D>
 */

public interface UserValidate<T, D> {

    boolean name(T name) throws ValidationException;

    boolean login(T login) throws ValidationException;

    boolean email(T email) throws ValidationException;

    boolean password(T password) throws ValidationException;

    boolean score(D score) throws ValidationException;
}
