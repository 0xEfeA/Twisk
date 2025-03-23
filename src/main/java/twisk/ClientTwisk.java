package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {
    /**
     * Un monde à trois Etapes
     */
    private static Monde MondeTroisEtapes(){
        Monde world = new Monde();
        Etape activite1 = new Activite("zoo",6,3);
        Etape guichet = new Guichet("guichet_tobogan",7);
        Etape activite2 = new ActiviteRestreinte("tobogan",4,2);
        Etape activite3 = new Activite("piscine",5,2);

        activite1.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(activite2);
        activite2.ajouterSuccesseur(activite3);

        world.ajouter(activite1,guichet,activite2,activite3);

        world.aCommeEntree(activite1);
        world.aCommeSortie(activite3);

        return world;
    }

    /**
     * Un monde à six Etapes
     * Monde avec les activités avec plusieurs successeurs
     */
    private static Monde MondeSixEtapes(){
        Monde world = new Monde();

        Etape activite1 = new Activite("swing",3,1);
        Etape activite2 = new Activite("ladder",4,1);
        Etape guichet1 = new Guichet("guichet_sandhouse",1);
        Etape activite3 = new ActiviteRestreinte("sandhouse",4,2);
        Etape guichet2 = new Guichet("guichet_bouncy",3);
        Etape activite4 = new ActiviteRestreinte("bouncy",3,1);

        activite1.ajouterSuccesseur(guichet1,guichet2,activite2);
        guichet1.ajouterSuccesseur(activite3);
        guichet2.ajouterSuccesseur(activite4);
        activite4.ajouterSuccesseur(activite2);
        activite2.ajouterSuccesseur(guichet1);

        world.ajouter(activite1,activite2,activite3,activite4,guichet1,guichet2);

        world.aCommeEntree(activite1,guichet1);
        world.aCommeSortie(activite3,activite2);

        return world;
    }
    private static Monde Mondetest(){
        Monde monde = new Monde();

        Activite zoo = new Activite("balade au zoo", 3, 1);
        Guichet guichet = new Guichet("accès au toboggan", 2);
        Activite tob = new ActiviteRestreinte("toboggan", 2, 1);

        zoo.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(tob);

        monde.ajouter(zoo, tob, guichet);

        monde.aCommeEntree(zoo);
        monde.aCommeSortie(tob);

        return  monde;
    }
    /**
     * Fonction principal
     * @param args l'argument
     */
    public static void main(String[] args){
        Simulation simulation = new Simulation();

        Monde world1 = MondeTroisEtapes();
        Monde world2 = MondeSixEtapes();

        simulation.simuler(world1);
        //System.out.println(" ");
        //simulation.simuler(world2);

    }
}
