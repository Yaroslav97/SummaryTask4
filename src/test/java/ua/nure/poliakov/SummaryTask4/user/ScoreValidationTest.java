package ua.nure.poliakov.SummaryTask4.user;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.UserValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import static org.junit.Assert.assertEquals;

public class ScoreValidationTest {

    @Test
    public void testValidEmail() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.score(5.5);
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidEmail() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.score(0.0);
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidEmail1() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.score(111111111111111.);
        assertEquals(false, expected);
    }
}
