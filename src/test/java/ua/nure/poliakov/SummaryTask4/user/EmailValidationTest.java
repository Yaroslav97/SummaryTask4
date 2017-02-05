package ua.nure.poliakov.SummaryTask4.user;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.dao.entity.User;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.ValidateEdition;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.UserValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import static org.junit.Assert.assertEquals;

public class EmailValidationTest {

    @Test
    public void testValidEmail() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("name@gmail.com");
        assertEquals(true, expected);
    }

    @Test
    public void testValidEmail1() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("name1.name@nure.ua");
        assertEquals(true, expected);
    }
    @Test
    public void testValidEmail2() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("name5_name@nure.ua");
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidEmail() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("n?ame#.name@n56.ua");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidEmail1() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("name.gmail.com");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidEmail2() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("@name_@gmail.com");
        assertEquals(false, expected);
    }
}
