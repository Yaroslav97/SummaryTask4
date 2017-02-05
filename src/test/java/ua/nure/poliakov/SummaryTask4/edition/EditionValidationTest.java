package ua.nure.poliakov.SummaryTask4.edition;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.Validator;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.ValidateEdition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EditionValidationTest {

    @Test
    public void testValidUser() throws ValidationException {
        Validator<Edition> validator = new ValidateEdition();
        boolean expected = validator.validate(new Edition(
                "Edition 1st",
                "some subject",
                4.5
        ));
        assertTrue(expected);
    }

    @Test(expected = ValidationException.class)
    public void testValidUser1() throws ValidationException {
        Validator<Edition> validator = new ValidateEdition();
        boolean expected = validator.validate(new Edition(
                "Edition# 1st",
                "some subject",
                4.5
        ));
        assertFalse( expected);
    }

    @Test(expected = ValidationException.class)
    public void testValidUser2() throws ValidationException {
        Validator<Edition> validator = new ValidateEdition();
        boolean expected = validator.validate(new Edition(
                "",
                "",
                0.0
        ));
        assertEquals(false, expected);
    }

}
