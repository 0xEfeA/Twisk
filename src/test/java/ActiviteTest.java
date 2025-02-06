package test.java;

import main.java.twisk.monde.Etape;
import main.java.twisk.monde.Activite;
import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest extends EtapeTest {
    @Test
    void testEstActivite() {
        Etape activite = new Activite("Zoo");
        Etape guichet = new Guichet("Guichet");
        assertTrue(activite.estUneActivite(),"L'activité doit être une activité et pas autre chose");
        assertFalse(guichet.estUneActivite(),"Le guichet ne doit pas être une activité ");
    }

}