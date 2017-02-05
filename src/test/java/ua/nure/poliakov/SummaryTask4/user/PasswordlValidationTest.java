package ua.nure.poliakov.SummaryTask4.user;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.UserValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import static org.junit.Assert.assertEquals;

public class PasswordlValidationTest {

    @Test
    public void testValidPassword() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.password("pass1234");
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidPassword() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.email("p");
        assertEquals(false, expected);
    }
}
