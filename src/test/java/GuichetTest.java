package test.java;

import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest {


    @Test
    void testNumerotationSemaphoreGuichet() {
        Guichet gtest1 = new Guichet("Guichet 1");
        Guichet gtest2 = new Guichet("Guichet 2");

        assertEquals(1, gtest1.getNumeroSemaphore(), "Le 1er guichet a pour sémaphore 1");
        assertEquals(2, gtest2.getNumeroSemaphore(), "Le 2èmre guichet a pour sémaphore 2");
    }
}
