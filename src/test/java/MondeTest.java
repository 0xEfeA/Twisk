
import twisk.monde.*;
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

        monde.aCommeEntree(etape);
        monde.aCommeSortie(etape2);
        monde.ajouter(etape, etape2, etape3);
        System.out.println(monde.toString());


    }
    @Test
    void TesttoC(){
        Monde monde = new Monde();

        Etape etape = new Activite("Zoo",10,2);
        Etape guichet = new Guichet("Guichet",4);

        Etape etape2 = new ActiviteRestreinte("Piscine",7,4);
        etape.ajouterSuccesseur( guichet);
        guichet.ajouterSuccesseur( etape2);

        monde.aCommeEntree(etape);
        monde.aCommeSortie(etape2);
        //l'ordre d'ajout dans monde influt sur l'ordre de génération du code ! Il faut donc ajouter le guichet avant son activité restreinte
        monde.ajouter(etape, guichet, etape2);
        System.out.println(monde.toC());
    }
}

