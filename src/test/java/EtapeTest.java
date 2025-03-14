
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EtapeTest {

    @Test
    void testToStringEtape() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Activite("Toboggan");
        Etape etape3 = new Guichet("Musée");
        etape1.ajouterSuccesseur(etape2);
        etape1.ajouterSuccesseur(etape3);
        assertEquals("Zoo: 2 successeurs - Toboggan, Musée", etape1.toString());
    }
    @Test
    void testNbSuccesseur() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Activite("Toboggan");
        Etape etape3 = new Guichet("Musée");
        etape1.ajouterSuccesseur(etape2);
        etape1.ajouterSuccesseur(etape3);
        assertEquals(2,etape1.nbSuccesseurs(), " Le zoo doit avoir 2 successeurs ");
    }
    @Test
    void testGetSuccesseur() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Activite("Toboggan");
        etape1.ajouterSuccesseur(etape2);
        assertTrue(etape1.getSuccesseurs().contains(etape2)," Le zoo doit avoir 1 successeur qui est le toboggan ");
    }

}