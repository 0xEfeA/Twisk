package twisk;

import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClientTwisk {

    private static Monde monde1(){
        Monde world = new Monde();
        Etape activite1 = new Activite("zoo",6,3);
        Etape guichet = new Guichet("guichet_tobogan",1);
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

    private static Monde monde2(){
        Monde world = new Monde();
        Etape activite1 = new Activite("zoo",6,3);
        Etape guichet = new Guichet("guichet_tobogan",1);
        Etape activite2 = new ActiviteRestreinte("tobogan",4,2);
        Etape activite3 = new Activite("piscine",5,2);
        Etape activite4 = new ActiviteRestreinte("Sauna",4,2);

        activite1.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(activite2);
        activite2.ajouterSuccesseur(activite3);
        activite3.ajouterSuccesseur(activite4);

        world.ajouter(activite1,guichet,activite2,activite3,activite4);

        world.aCommeEntree(activite1);
        world.aCommeSortie(activite4);

        return world;
    }
    public static void main(String[] args){
        ClientTwisk client = new ClientTwisk();
        Monde monde1 = monde1();
        client.lancerSimu(monde1,5);
        // Appel garbage collector
        System.gc();
        Monde monde2 = monde2();
        client.lancerSimu(monde2,5);

    }

    private void lancerSimu(Monde monde, int nbClients){
        //Instancie le classloader perso pour charger les classes de simulation
        ClassLoaderPerso perso = new ClassLoaderPerso(this.getClass().getClassLoader());
        try{
            //Chargement de la classe de simulation
            Class<?> simulation = perso.loadClass("twisk.simulation.Simulation");
            //Recuperation du constructeur par defaut de la classe de simulation
            Constructor<?> constructor = simulation.getConstructor();
            //Instanciation de la classe de simulation avec le constructeur par defaut
            Object instanceSimu = constructor.newInstance();
            //Appel fonction simuler et setNbClients
            simulation.getMethod("setNbClients",int.class).invoke(instanceSimu,nbClients);

            simulation.getMethod("simuler",Monde.class).invoke(instanceSimu,monde);

        }
        //Gestion de toutes les exceptions possibles
        catch (ClassNotFoundException e){
            System.err.println("Classe de simulation introuvable");
        } catch (NoSuchMethodException e) {
            System.err.println("Fonction ou constructeur introuvable");
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("Erreur lors de l'instanciation de Simulation");
        }catch (InvocationTargetException e){
            System.err.println("Erreur lors de l'appel de la fonction simuler");
        }
    }

}

















   //Ancien client
    /**
     * Un monde à trois Etapes

    private static Monde MondeTroisEtapes(){
        Monde world = new Monde();
        Etape activite1 = new Activite("zoo",6,3);
        Etape guichet = new Guichet("guichet_tobogan",1);
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

    private static Monde MondeSixEtapes(){
        Monde world = new Monde();

        Etape activite1 = new Activite("swing",3,1);
        Etape activite2 = new Activite("ladder",4,1);
        Etape guichet1 = new Guichet("guichet_sandhouse",4);
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

        Simulation s = new Simulation();
        s.setNbClients(10);
        return monde;
    }
    /**
     * Fonction principal
     * @param args l'argument

    public static void main(String[] args){
        Simulation simulation = new Simulation();

        Monde world1 = MondeTroisEtapes();
        Monde world2 = MondeSixEtapes();
        Monde world3 = Mondetest();

        //simulation.setNbClients(5);
        simulation.simuler(world1);
        simulation.simuler(world2);
        //System.out.println(" ");
        //simulation.simuler(world2);

    }
}
**/

