package ua.nure.poliakov.SummaryTask4.edition;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.EditionValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.ValidateEdition;

import static org.junit.Assert.assertEquals;

public class NameValidationTest {

    @Test
    public void testValidName() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.name("The NewYork times");
        assertEquals(true, expected);
    }

    @Test
    public void testValidName1() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.name("The NewYork times 1");
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidName() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.name("The NewYork times$/");
        assertEquals(false, expected);
    }
}
