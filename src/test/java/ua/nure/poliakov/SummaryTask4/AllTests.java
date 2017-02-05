package ua.nure.poliakov.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.nure.poliakov.SummaryTask4.edition.EditionValidationTest;
import ua.nure.poliakov.SummaryTask4.edition.NameValidationTest;
import ua.nure.poliakov.SummaryTask4.edition.PriceValidationTest;
import ua.nure.poliakov.SummaryTask4.edition.SubjectValidationTest;
import ua.nure.poliakov.SummaryTask4.moc.MockitoTest;
import ua.nure.poliakov.SummaryTask4.user.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({EditionValidationTest.class, NameValidationTest.class, PriceValidationTest.class, SubjectValidationTest.class,
        EditionValidationTest.class, FullNameValidationTest.class, LoginValidationTest.class, PasswordlValidationTest.class,
        ScoreValidationTest.class, UserValidationTest.class, MockitoTest.class})

public class AllTests {
}