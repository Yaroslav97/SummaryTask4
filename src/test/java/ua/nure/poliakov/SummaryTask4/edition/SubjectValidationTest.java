package ua.nure.poliakov.SummaryTask4.edition;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.utils.exceptions.ValidationException;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.EditionValidate;
import ua.nure.poliakov.SummaryTask4.utils.validations.edition.ValidateEdition;

import static org.junit.Assert.assertEquals;

public class SubjectValidationTest {

    @Test
    public void testValidSubject() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.subject("news");
        assertEquals(true, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidSubject() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.name("df");
        assertEquals(false, expected);
    }

    @Test(expected = ValidationException.class)
    public void testNoValidSubject1() throws ValidationException {
        EditionValidate<String, Double> editionValidate = new ValidateEdition();
        boolean expected = editionValidate.name("d/$%^**$&*9");
        assertEquals(false, expected);
    }
}
