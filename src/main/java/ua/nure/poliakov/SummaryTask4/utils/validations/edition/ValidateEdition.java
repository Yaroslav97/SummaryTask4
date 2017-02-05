package ua.nure.poliakov.SummaryTask4.utils.validations.edition;

import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateEdition implements Validator<Edition>, EditionValidate<String, Double> {
    @Override
    public boolean validate(Edition edition) throws ValidationException {
        return name(edition.getName()) && subject(edition.getSubject()) && price(edition.getPrice());
    }

    @Override
    public boolean name(String name) throws ValidationException {
        Pattern pattern = Pattern.compile("[A-zА-я0-9 ]+");
        Matcher matcher = pattern.matcher(name);
        if (name == null || name.length() < 4 || !matcher.matches()) {
            throw new ValidationException("Name edition must be greater then 4 and contains only characters and digites");
        }
        return true;
    }

    @Override
    public boolean subject(String subject) throws ValidationException {
        Pattern pattern = Pattern.compile("[A-zА-я ]+");
        Matcher matcher = pattern.matcher(subject);
        if (subject == null || subject.length() < 4 || !matcher.matches()) {
            throw new ValidationException("Subject must be greater then 4 and contains only characters");
        }
        return true;
    }

    @Override
    public boolean price(Double price) throws ValidationException {
        if (price == null || price > 100 || price < 1) {
            throw new ValidationException("Price must be between 1 and 99");
        }
        return true;
    }
}
