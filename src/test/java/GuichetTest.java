
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest extends EtapeTest {


    @Test
    void testNumerotationSemaphoreGuichet() {
        Guichet gtest1 = new Guichet("Guichet 1");
        Guichet gtest2 = new Guichet("Guichet 2");

        assertEquals(1, gtest1.getNumeroSemaphore(), "Le 1er guichet a pour sémaphore 1");
        assertEquals(2, gtest2.getNumeroSemaphore(), "Le 2èmre guichet a pour sémaphore 2");
    }
    @Test
    void estUnguichet() {
        Etape etape1 = new Activite("Zoo");
        Etape etape2 = new Guichet("guichet_zoo");
        assertFalse(etape1.estUnGuichet(), " Le zoo doit être activite ");
        assertTrue(etape2.estUnGuichet(), " Le zoo ne doit pas être activite mais un guichet");
    }
}
