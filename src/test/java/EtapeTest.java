package test.java;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.Etape;
import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtapeTest {
    @Test
    void testAjouterSuccesseur() {
        Etape e1 = new Activite("Zoo");
        Etape e2 = new Activite("Toboggan");

        e1.ajouterSuccesseur(e2);
        assertEquals(1, e1.nbSuccesseurs());
        assertEquals(e2, e1.iterator().next());

    }
    @Test
    void testToSring() {
        Etape e1 = new Activite("Zoo");
        Etape e2 = new Activite("Toboggan");
        e1.ajouterSuccesseur(e2);
        e1.ajouterSuccesseur(e1);
        System.out.println(e1.toString());
        System.out.println(e2.toString());
    }
    @Test
    void testEstActivite() {
        Etape activite = new Activite("Zoo");
        Etape guichet = new Guichet("Guichet");
        assertTrue(activite.estUneActivite(),"L'activité doit être une activité et pas autre chose");
        assertFalse(guichet.estUneActivite(),"Le guichet ne doit pas être une activité ");
    }
    @Test
    void testEstGuichet() {
        Etape guichet = new Guichet("Guichet");
        Etape activite = new Activite("Zoo");
        assertFalse(activite.estUnGuichet(),"L'activité ne doit pas être un guichet");
        assertTrue(guichet.estUnGuichet(),"Le guichet ne doit pas être autre chose que guichet");
    }

}