package ua.nure.poliakov.SummaryTask4.user;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.UserValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.user.ValidateUser;

import static org.junit.Assert.assertEquals;

public class FullNameValidationTest {

    @Test
    public void testValidFullName() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.name("Fname Lname");
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidFullName() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.name("FnameLname");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidFullName1() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.name("Fname Lname hyhy");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidFullName2() throws ValidationException {
        UserValidate<String, Double> userValidate = new ValidateUser();
        boolean expected = userValidate.name("Fname Lname556");
        assertEquals(false, expected);
    }
}
