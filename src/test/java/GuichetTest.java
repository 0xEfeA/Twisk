
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest extends EtapeTest {

    @Test
    void estUnguichet() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Guichet("guichet_zoo");
        assertFalse(etape1.estUnGuichet(), " Le zoo doit être activite ");
        assertTrue(etape2.estUnGuichet(), " Le zoo ne doit pas être activite mais un guichet");
    }
}
