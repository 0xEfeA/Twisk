
import twisk.monde.Etape;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest extends EtapeTest {

    @Test
    void TestestUneActivite() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Guichet("guichet_zoo");
        assertTrue(etape1.estUneActivite(), " Le zoo doit être activite ");
        assertFalse(etape2.estUneActivite(), " Le zoo ne doit pas être activite mais un guichet");
    }
    @Test
    void TestGetTemps() {
        Activite etape1 = new Activite("Zoo",10,3);
        assertEquals(10,etape1.getTemps(), "Le temps de l'étape doit être 10");

    }
    @Test
    void TestGetEcartTemps() {
        Activite etape1 = new Activite("Zoo",10,3);
        assertEquals(3,etape1.getEcartTemps(),"L'écart doit être de 3 secondes");
    }
    @Test
    void TesttoC() {
        Activite etape1 = new Activite("Zoo",10,3);
        Activite etape2 = new Activite("cinéma",10,3);
        Activite etape3 = new Activite("Musée",10,3);
        etape1.ajouterSuccesseur(etape2,etape3);
        System.out.println(etape1.toC());
    }
}