
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

class FabriqueNumeroTest {
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
    void testNumerotationSemaphoreGuichet() {
        Guichet gtest1 = new Guichet("Guichet 1");
        Guichet gtest2 = new Guichet("Guichet 2");

        assertEquals(1, gtest1.getNumeroSemaphore(), "Le 1er guichet a pour sémaphore 1");
        assertEquals(2, gtest2.getNumeroSemaphore(), "Le 2èmre guichet a pour sémaphore 2");
    }
}