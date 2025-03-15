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
        System.load("/tmp/twisk/libTwisk.so") ;


        // Instanciations des paramètres du monde de test
        int nbEtapes = monde.nbEtapes();
        int nbClients = 10;
        int nbGuichets =monde.nbGuichet();
        int[] tabJetonsGuichet = new int[nbGuichets];
        //nombre de place dans l'activité
        tabJetonsGuichet[0] = 1;
        // Récupération de l'adresse du tableau contenant les pid des processus retourné par start_simulation
        int[] tabsimu = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);
        // Affichage pid
        System.out.printf("Les clients :");
        for (int i = 0; i < nbClients; i++) {
            System.out.printf(" %d, ",tabsimu[i]);

        }
        System.out.printf("\n");

        //Boucle inifinie qui sera fini que quand condition d'arrêt réalisée
        while (1==1) {
            // Récupération des informations des clients
            int[] tabclient = ou_sont_les_clients(nbEtapes, nbClients);
            // taille de ségment mémoire d'une étape (nbClient + 1 case qui stock le nombre de client en mémoire)
            int tailleEtapesEnMemoire = nbClients+1;
            for (int i = 0; i < nbEtapes; i++) {
                // On récupère le nombre de client de l'étape i
                int nbClientEtapeI = tabclient[i * tailleEtapesEnMemoire];
                // Selon le numéro l'étape on affiche un nom différent
                if (i == 0) {
                    System.out.printf("étape %d (entrée) %d clients : ", i, nbClientEtapeI);

                }else if ( i== nbEtapes-1) {
                    System.out.printf("étape %d (sortie) %d clients : ", i, nbClientEtapeI);

                }else if (i == 1 ||i == nbEtapes-3) {
                    System.out.printf("étape %d (guichet) %d clients : ", i, nbClientEtapeI);

                }
                else {
                    System.out.printf("étape %d (Activité) %d clients : ", i, nbClientEtapeI);

                }
                //Affichage des identifiants des clients à l'étape i
                for (int j = 0; j < nbClientEtapeI; j++) {
                    int pidClientEtapeI = tabclient[i *tailleEtapesEnMemoire + j + 1];
                    System.out.printf("%d,",pidClientEtapeI);
                }

                System.out.printf("\n");
            }
            //Condition d'arrêt : quand tous les clients arrivent à la sortie
            if ( tabclient[(nbEtapes-1) * tailleEtapesEnMemoire]==nbClients) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("\n");
        }
        nettoyage();
    }

    //Fonctions natives
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();

}
