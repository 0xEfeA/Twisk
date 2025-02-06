package test.java;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.Etape;
import org.junit.jupiter.api.Test;

class EtapeTest {
    @Test
    void testToSring() {
        Etape e1 = new Activite("Zoo");
        Etape e2 = new Activite("Toboggan");
        e1.ajouterSuccesseur(e2);
        e1.ajouterSuccesseur(e1);
        System.out.println(e1.toString());
        System.out.println(e2.toString());
    }


}