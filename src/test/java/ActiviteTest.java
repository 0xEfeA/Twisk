
import twisk.monde.Etape;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest extends EtapeTest {

    @Test
    void estUneActivite() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Guichet("guichet_zoo");
        assertTrue(etape1.estUneActivite(), " Le zoo doit être activite ");
        assertFalse(etape2.estUneActivite(), " Le zoo ne doit pas être activite mais un guichet");
    }

}