
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MondeTest {
    @Test void TestNbEtapes(){
        Etape etape = new Activite("Zoo");
        Etape etape2 = new Activite("Musée");
        Etape etape3 = new Activite("Piscine");
        Monde monde = new Monde();
        monde.ajouter(etape, etape2, etape3);
        assertEquals(3,monde.nbEtapes(),"Le monde doit avoir 3 étapes");

    }
    @Test
    void TestNbGuichet(){
        Etape etape = new Guichet("guichet zoo");
        Etape etape2 = new Guichet("guichet musée");
        Etape etape3 = new Guichet("guichet piscine");
        Monde monde = new Monde();
        monde.ajouter(etape, etape2, etape3);
        assertEquals(3,monde.nbGuichet(),"Le monde doit avoir 3 guichet");
    }
    @Test
    void TesttoString(){
        Etape etape = new Activite("Zoo");
        Etape etape2 = new Activite("Piscine");
        Etape etape3 = new Guichet("Guichet");
        Monde monde = new Monde();
        etape.ajouterSuccesseur(etape2, etape3);
        etape3.ajouterSuccesseur( etape2);

        monde.aCommmeEntree(etape);
        monde.aCommmeSortie(etape2);
        monde.ajouter(etape, etape2, etape3);
        System.out.println(monde.toString());


    }

}

