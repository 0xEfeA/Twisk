package test.java;

import main.java.monde.Activite ;
import main.java.monde.Etape;
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
}
