

import twisk.monde.Etape;
import twisk.monde.Activite;
import twisk.monde.GestionnaireEtapes;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {
        @Test
    void testAjouterEtape() {
            GestionnaireEtapes gestionnaire = new GestionnaireEtapes();
            Etape etape1 = new Activite("Musée");
            Etape etape2 = new Activite("Zoo");
            Etape etape3 = new Guichet("Guichet");
            gestionnaire.ajouterEtape(etape1,etape2,etape3);
            assertEquals(3, gestionnaire.nbEtapes(), "Le gestionnaire doit avoir 3 étapes");
        }
    @Test
    void testGetlastEtape() {
            GestionnaireEtapes gestionnaire = new GestionnaireEtapes();
            Etape etape = new Guichet("Guichet");
            Etape etape1 = new Activite("Zoo");
            gestionnaire.ajouterEtape(etape,etape1);
            assertEquals(etape1, gestionnaire.lastEtape(), "La dernière étape doit être le zoo");
    }


}