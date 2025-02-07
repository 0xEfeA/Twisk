
import org.junit.jupiter.api.BeforeEach;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtapeTest {
    @BeforeEach
    void setUp() {
        FabriqueNumero.getInstance().reset();
    }

    @Test
    void testNumeroEtape() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Activite("Toboggan");
        Etape etape3 = new Guichet("Guichet");
        assertEquals(0, etape1.getNumeroEtape());
        assertEquals(1, etape2.getNumeroEtape());
        assertEquals(2, etape3.getNumeroEtape());

    }
    @Test
    void testToStringEtape() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Activite("Toboggan");
        Etape etape3 = new Guichet("Musée");
        etape1.ajouterSuccesseur(etape2);
        etape1.ajouterSuccesseur(etape3);
        assertEquals("Zoo: 2 successeurs - Toboggan, Musée, ", etape1.toString());
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

}