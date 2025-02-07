package twisk;

import twisk.monde.*;

public class ClientTwisk {
    public static void main(String[] args){
        Monde world = new Monde();
        Etape activite1 = new Activite("zoo",6,3);
        Etape guichet = new Guichet("guichet_tobogan");
        Etape activite2 = new ActiviteRestreinte("tobogan",4,2);

        activite1.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(activite2);

        world.ajouter(activite1,guichet,activite2);

        world.aCommmeEntree(activite1);
        world.aCommmeSortie(activite2);

        for (Etape e: world.getLesEtapes()){
            System.out.println(e.toString());
        }
    }
}
