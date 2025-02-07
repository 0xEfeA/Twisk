package main.java.twisk;

import main.java.twisk.monde.*;
import main.java.twisk.simulation.*;

import java.util.Scanner;

public class ClientTwisk {
    /**
     * Function pour ajouter un successeur au dernier etape du monde
     */
    private static void addLastSuccesseur(Monde world, Etape etape){
        //Adjust this if the last Entree (predecessor) was an E/S (add the condition)
        if (world.getLesEtapes().nbEtapes() > 0) {
            world.getLesEtapes().lastEtape().ajouterSuccesseur(etape);
        }
    }

    /**
     * Fonction pour creer un monde
     */
    private static Monde initMonde() {
        Scanner scanner = new Scanner(System.in);

        /**
         * Extraction des Infos au client Concernant son monde
         */
        System.out.println("BIENVENUE DANS TWISK \nVoici un Questionnaire pour la création de votre monde \nVoulez-vous Creer un Monde (O/N)?");
        String reponse = scanner.next();

        scanner.nextLine();

        System.out.println("Quel nom donnez-vous à ce monde?");
        String nomDuMonde = scanner.nextLine();

        System.out.println("Combien d'Activités voulez-vous dans " + nomDuMonde + " (Activités restreintes inclus)?");
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
                    System.out.println("Voulez vous que " + nom + " soit une Entrée(O/N)?");
                    String activSoitSortie = scanner.next();
                    System.out.println("Voulez vous que " + nomGuichet + " soit Une Sortie(O/N)?");
                    String GuichetSoitEntree = scanner.next();

                    //Ajout d'avtivité dans le monde
                    Etape activ = new ActiviteRestreinte(nom, time, ecart);
                    Etape guichet = new Guichet(nomGuichet, limit);
                    guichet.ajouterSuccesseur(activ);


                    //cas ou un activité à plusieurs successeur
                    if(world.nbEtapes() >= 2){
                        System.out.println("Voulez vous que " + nom + " soit liée à un activité déja avec un successeur(O/N)?");
                        String birfucation = scanner.next();
                        if(birfucation.equals("O")){
                            System.out.println("Quel est l'index de cette activité? (Les activités sont de 1-index) ");
                            int indexactivite = scanner.nextInt();
                            for(Etape e: world.getLesEtapes()){
                                if(e.getNumeroEtape() == (indexactivite -1) && !e.estUnGuichet()){  //LEMME 4
                                    e.ajouterSuccesseur(guichet);
                                }
                            }
                        }
                        else{
                            if (birfucation.equals("N")){
                                //ajouter au dernier element du monde
                                addLastSuccesseur(world,guichet);
                            }
                        }
                    }
                    else{
                        //ajouter au dernier element du monde
                        addLastSuccesseur(world,guichet);
                    }

                    world.ajouter(guichet, activ);
                }

                //Cas d'activité normal
                else {
                    if (choixRes.equals("N")) {
                        System.out.println("Combien de temps reste un participant dans " + nom + " (en secondes)?");
                        int time = scanner.nextInt();
                        System.out.println("Avec l'écart(en secondes)? ");
                        int ecart = scanner.nextInt();

                        //Ask the Prof of this implantation
                        System.out.println("Voulez vous que " + nom + " soit une Entrée(O/N)?");
                        String activSoitSortie = scanner.next();
                        System.out.println("Voulez vous que " + nom + " soit Une Sortie(O/N)?");
                        String activSoitEntree = scanner.next();

                        //Ajout d'activité dans le monde
                        Etape activ = new Activite(nom);

                        //cas ou un activité à plusieurs successeur
                        if(world.nbEtapes() >= 2){
                            System.out.println("Voulez vous que " + nom + " soit liée à un activité déja avec un successeur(O/N)?");
                            String birfucation = scanner.next();
                            if(birfucation.equals("0")){
                                System.out.println("Quel est l'index de cette activité? (Les activités sont de 1-index)");
                                int indexactivite = scanner.nextInt();
                                for(Etape e: world.getLesEtapes()){
                                    if(e.getNumeroEtape() == (indexactivite - 1) && !e.estUnGuichet()){  //LEMME 4
                                        e.ajouterSuccesseur(activ);
                                    }
                                }
                            }
                            else{
                                if(birfucation.equals("N")){
                                    //ajouter au dernier element du monde
                                    addLastSuccesseur(world,activ);
                                }
                            }
                        }
                        else{
                            //ajouter au dernier element du monde
                            addLastSuccesseur(world,activ);
                        }
                        world.ajouter(activ);
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
