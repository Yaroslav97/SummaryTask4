package ua.nure.poliakov.SummaryTask4.moc;

import org.junit.Test;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAO;
import ua.nure.poliakov.SummaryTask4.dao.edition_dao.EditionDAOImplement;
import ua.nure.poliakov.SummaryTask4.dao.entity.Edition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoTest {

    @Test
    public void test() {
        EditionDAO editionDAO = mock(EditionDAOImplement.class);
        when(editionDAO.getEdition(anyInt())).thenReturn(new Edition("name", "subject", 12.2));
        Edition edition = editionDAO.getEdition(10);
        assertEquals("not correct name", "name", edition.getName());
        assertEquals("not correct subject", "subject", edition.getSubject());
        assertEquals(12.2, edition.getPrice(), 0.002);
    }
}
