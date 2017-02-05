package ua.nure.poliakov.SummaryTask4.utils.validations.user;

import ua.nure.poliakov.SummaryTask4.dao.entity.User;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUser implements Validator<User>, UserValidate<String, Double> {

    @Override
    public boolean validate(User user) throws ValidationException {
        return name(user.getFullName()) && login(user.getLogin()) &&
                email(user.getEmail()) && password(user.getPassword());
    }

    @Override
    public boolean name(String name) throws ValidationException {
        Pattern pattern = Pattern.compile("[A-zА-я]+ [A-zА-я]+");
        Matcher matcher = pattern.matcher(name);
        if (name.length() < 4 || !matcher.matches()) {
            throw new ValidationException("User name must be longer then 4 cand contains only characters");
        }
        return true;
    }

    @Override
    public boolean login(String login) throws ValidationException {
        Pattern pattern = Pattern.compile("[A-zА-я0-9]+");
        Matcher matcher = pattern.matcher(login);
        if (login == null || login.length() < 4 || !matcher.matches()) {
            throw new ValidationException("Login must be longer then 4 and contains only characters and digitals");
        }
        return true;
    }

    @Override
    public boolean email(String email) throws ValidationException {
        Pattern pattern = Pattern.compile("[a-z0-9]+\\.?_?[a-z0-9]+@.{2,9}\\..{2,3}");
        Matcher matcher = pattern.matcher(email);
        if (email == null || email.length() < 5 || !matcher.matches()) {
            throw new ValidationException("Email must be contains only characters, digitals, . _");
        }
        return true;
    }

    @Override
    public boolean password(String password) throws ValidationException {
        if (password == null || password.length() < 4) {
            throw new ValidationException("Password must be greater than 4");
        }
        return true;
    }

    @Override
    public boolean score(Double score) throws ValidationException {
        if (score == null || score < 0.1 || score > 999) {
            throw new ValidationException("RefillScore must be gt 0 lt 1000");
        }
        return true;
    }
}