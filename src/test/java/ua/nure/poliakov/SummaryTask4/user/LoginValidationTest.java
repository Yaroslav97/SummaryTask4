package ua.nure.poliakov.SummaryTask4.user;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.UserValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import static org.junit.Assert.assertEquals;

public class LoginValidationTest {

    @Test
    public void testValidLogin() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.login("login");
        assertEquals(true, expected);
    }

    @Test
    public void testValidLogin1() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.login("login1");
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidLogin() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.login("log");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidLogin1() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.login("login aaaa bb hb");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidLogin2() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.login("$$$%&&896login");
        assertEquals(false, expected);
    }
}
