package test.java;

import main.java.twisk.monde.Guichet;
import main.java.twisk.monde.Activite;
import main.java.twisk.monde.Etape;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GuichetTest extends EtapeTest {
    @Test
    void testEstGuichet() {
        Etape guichet = new Guichet("Guichet");
        Etape activite = new Activite("Zoo");
        assertFalse(activite.estUnGuichet(),"L'activité ne doit pas être un guichet");
        assertTrue(guichet.estUnGuichet(),"Le guichet ne doit pas être autre chose que guichet");
    }
}