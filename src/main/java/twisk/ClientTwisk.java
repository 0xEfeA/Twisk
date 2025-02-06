package main.java.twisk;

import main.java.twisk.monde.*;
import main.java.twisk.simulation.*;
import java.util.Scanner;

public class ClientTwisk {
    //Instancier Simulation et demande l'execution de simuler




    //Faut aussi ecrire différentes fonctions d'un monde
    private Monde CreateMonde(){
        Monde newWorld = new Monde();
        return newWorld;
    }




    //Propose la fonction main pour creer un monde
    public static void main(String[] args){
        Scanner scanner= new Scanner(System.in);

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

        System.out.println("Combien d'Activités Restreintes voulez-vous dans " + nomDuMonde + "?");
        int nombreActRestreinte = scanner.nextInt();


        scanner.close();



        /**
         * Creation du monde à partir d'infos donné par le Client
         */
        if (reponse.equals("O")){
            System.out.println("Le monde " + nomDuMonde + " est creé");
        }
    }

}
