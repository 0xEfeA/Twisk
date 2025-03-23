package twisk.simulation;

import twisk.monde.*;
import twisk.outils.KitC;

public class Simulation {
    private KitC environnement;
    /**
     * Constructeur sans argument
     */
    public Simulation() {
        this.environnement = new KitC();
        this.environnement.creerEnvironnement(); // Création du répertoire et copie des fichiers
    }

    /**
     * Affichage des details du monde en mode C
     * @param monde
     */
    public void simulerprint(Monde monde){
        System.out.println(monde.toC());
    }

    /**
     * Affiche le monde sur la sortie standard
     * @param monde
     */
    public void simuler(Monde monde) {
        String codeC = monde.toC();
        environnement.creerFichier(codeC);
        environnement.compiler();
        environnement.construireLaBibliotheque();
        System.load("/tmp/twisk/libTwisk.so");

        // Instanciations des paramètres du monde de test
        int nbEtapes = monde.nbEtapes();
        int nbClients = 10;
        int nbGuichets = monde.nbGuichet();
        int[] tabJetonsGuichet = new int[nbGuichets];

        if (nbGuichets > 0) tabJetonsGuichet[0] = 1;
        if (nbGuichets > 1) tabJetonsGuichet[1] = 1;

        // Récupération du tableau des PIDs des processus simulés
        int[] tabsimu = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);

        // Affichage des PIDs des clients
        System.out.print("Les clients : ");
        for (int i = 0; i < nbClients; i++) {
            System.out.printf(" %d, ", tabsimu[i]);
        }
        System.out.println("\n");

        // Boucle de simulation
        while (true) {
            int[] tabclient = ou_sont_les_clients(nbEtapes, nbClients);
            int tailleEtapesEnMemoire = nbClients + 1;

            // Place les clients au début (sas d'entrée)
            if (tabclient[0] == 0) {
                for (int i = 0; i < nbClients; i++) {
                    tabclient[i] = 0;
                }
            }

            // Parcourir toutes les étapes du monde
            for (int i = 0; i < nbEtapes; i++) {
                int nbClientEtapeI = tabclient[i * tailleEtapesEnMemoire];

                // Trouver l'étape correspondante
                Etape etape = monde.getLesEtapes().getEtapeI(i);

                // Déterminer le type d'étape
                String typeEtape = "Pas de nom"; //par défaut
                if (etape.estSasEntree()) {
                    typeEtape = "Entrée";
                } else if (etape.estUnGuichet()) {
                    typeEtape = "Guichet";
                } else if (etape.estUneActivite()) {
                    typeEtape = "Activité: " + etape.getNom();
                } else if (etape.estSasSortie()) {
                    typeEtape = "Sortie";
                }

                System.out.printf("étape %d (%s) %d clients : ", i, typeEtape, nbClientEtapeI);

                // Affichage des identifiants des clients
                for (int j = 0; j < nbClientEtapeI; j++) {
                    int pidClientEtapeI = tabclient[i * tailleEtapesEnMemoire + j + 1];
                    System.out.printf("%d,", pidClientEtapeI);
                }

                System.out.println();
            }

            // Condition d'arrêt : Tous les clients sont arrivés à la sortie
            if (tabclient[(nbEtapes - 1) * tailleEtapesEnMemoire] == nbClients) {
                break;
            }

            // Pause d'une seconde
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Interruption détectée, arrêt de la simulation.");
                Thread.currentThread().interrupt();
                break;
            }

            System.out.println();
        }

        nettoyage();
    }



    //Fonctions natives
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();

}
