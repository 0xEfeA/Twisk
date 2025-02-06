package main.java.twisk;

import main.java.twisk.monde.*;
import main.java.twisk.simulation.*;

import java.util.Scanner;

public class ClientTwisk {

    /**
     * Fonction pour creer un monde
     */
    public static Monde initMonde() {
        Scanner scanner = new Scanner(System.in);

        /**
         * Extraction des Infos au client Concernant son monde
         */
        System.out.println("BIENVENUE DANS TWISK \nVoici un Questionnaire pour la création de votre monde \nVoulez-vous Creer un Monde (O/N)?");
        String reponse = scanner.next();

        scanner.nextLine();

        System.out.println("Quel nom donnez-vous à ce monde?");
        String nomDuMonde = scanner.nextLine();

        System.out.println("Combien d'Activités voulez-vous dans " + nomDuMonde + "?");
        int nombreActivities = scanner.nextInt();

        //Creer le monde
        Monde world = new Monde();

        /**
         * Creation du monde à partir d'infos donné par le Client
         */
        if (reponse.equals("O") && nomDuMonde != null && nombreActivities >= 0) {
            //Ajout d'activités normal et Restreinte(Guichet effectivement)
            for (int i = 0; i < nombreActivities; i++) {
                System.out.println("Quel nom donnez vous à l'activité " + (i+1) + " ?");
                String nom = scanner.next();
                System.out.println("Voulez vous que " + nom + " soit Restreinte(O/N)?");
                String choixRes = scanner.next();

                //Cas d'activité restreinte
                if (choixRes.equals("O")) {
                    System.out.println("Combien de temps reste un participant dans " + nom + " (en secondes)?");
                    int time = scanner.nextInt();
                    System.out.println("Avec l'écart(en secondes)? ");
                    int ecart = scanner.nextInt();
                    System.out.println("Limit de nombre de personnes dans " + nom + " ?");
                    int limit = scanner.nextInt();
                    System.out.println("Quel nom donnez vous au guichet avant " + nom + " (ex:guichet_activité)?");
                    String nomGuichet = scanner.next();

                    //Ask the Prof of this implantation
                    System.out.println("Voulez vous que " + nom + " soit une Sortie(O/N)?");
                    String activSoitSortie = scanner.next();
                    System.out.println("Voulez vous que " + nomGuichet + " soit Une Entrée(O/N)?");
                    String GuichetSoitEntree = scanner.next();

                    //Ajout d'avtivité dans le monde
                    Etape activ = new ActiviteRestreinte(nom, time, ecart);
                    Etape guichet = new Guichet(nomGuichet, limit);
                    guichet.ajouterSuccesseur(activ);

                    //definir un predecesseur s'il y'avait un Etape deja dans monde
                    if (world.getLesEtapes().nbEtapes() > 0) {
                        world.getLesEtapes().lastEtape().ajouterSuccesseur(guichet);
                    }
                    world.ajouter(guichet, activ);
                }


                else {
                    //Cas d'activité normal
                    if (choixRes.equals("N")) {

                    }
                }

            }
        }
        scanner.close();
        return world;
    }



    /**
     * Fonction Principal
     * @param args
     */
    public static void main(String[] args){
        Monde testworld = initMonde();
        Simulation simulation = new Simulation();
        simulation.simuler(testworld);
    }


}
