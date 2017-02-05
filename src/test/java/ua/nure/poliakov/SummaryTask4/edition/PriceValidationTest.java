package ua.nure.poliakov.SummaryTask4.edition;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.EditionValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.ValidateEdition;

import static org.junit.Assert.assertEquals;

public class PriceValidationTest {

    @Test
    public void testValidSubject() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.price(5.5);
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidSubject() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.price(0.011111111);
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidSubject1() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.price(1000000000000.1);
        assertEquals(false, expected);
    }
}
