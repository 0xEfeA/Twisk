package twisk.simulation;

import twisk.monde.*;
import twisk.outils.KitC;
import twiskIG.mondeIG.SujetObserve;

import java.util.ArrayList;

public class Simulation extends SujetObserve {
    private KitC environnement;
    private int nbClients = 3 ; // Par défaut on a 3 clients
    private GestionnaireClients gestionnaireClients;
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
        //Instanciation du gestionnaire de clients
        gestionnaireClients = new GestionnaireClients();
        monde.getLesEtapes().reoganiser(monde.getEntree());
        String codeC = monde.toC();
        environnement.creerFichier(codeC);
        environnement.compiler();
        environnement.construireLaBibliotheque();
        System.load("/tmp/twisk/libTwisk.so") ;
        //Affiche le schéma du monde
        System.out.println(monde.toString());
        System.out.println();
        // Instanciations des paramètres du monde de test
        int nbEtapes = monde.nbEtapes()+2; // +2 pour le sasEntrée et SasSortie
        int nbClients = this.nbClients;
        int nbGuichets =monde.nbGuichet();
        int[] tabJetonsGuichet = new int[nbGuichets];
        //nombre de jetons pour chaque guichets
        ArrayList<Guichet> guichets = monde.getGuichets();
        int indiceGuichet = 0;
        for(Guichet gui: guichets){
            tabJetonsGuichet[indiceGuichet] = gui.getNbJetons();
            indiceGuichet++;
        }
        // Récupération de l'adresse du tableau contenant les pid des processus retourné par start_simulation
        int[] tabsimu = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);
        //Initialisation du gestionnaire avec les PID des clients
        gestionnaireClients.setClient(tabsimu);
        // Affichage pid avant simulation
        System.out.print("Les clients :");
        for (int i = 0; i < nbClients; i++) {
            System.out.printf(" %d, ",tabsimu[i]);

        }
        System.out.print("\n");

        //Boucle inifinie qui sera fini que quand condition d'arrêt réalisée
        while (true) {
            // Récupération des informations des clients
            int[] tabclient = ou_sont_les_clients(nbEtapes, nbClients);
            notifierObservateurs();
            // taille de ségment mémoire d'une étape (nbClient + 1 case qui stock le nombre de client en mémoire)
            int tailleEtapesEnMemoire = nbClients+1;
            //Déplacement des client à travers les étapes
            for (int i = 0; i < nbEtapes; i++) { //Parcours chaque étape
                int nbclients = tabclient[i * tailleEtapesEnMemoire];
                for (int j = 0; j < nbclients; j++) { //PArcours chaque client dans l'étape i
                    int pid = tabclient[j + 1 + i * tailleEtapesEnMemoire];
                    if (i == 0) { //Cas 0 càd quand les clients arrivent en entrée
                        gestionnaireClients.allerA(pid,monde.getEntree(),j);
                    }else if(i==1){ // Cas où les clients arrivent en sortie
                        gestionnaireClients.allerA(pid,monde.getSortie(),j);
                    }else { // Cas autre ( activité ou guichet)
                    gestionnaireClients.allerA(pid, monde.getEtapeI(i-2),j);
                }
                }
            }






            System.out.println();


                for (int i = 0; i < nbEtapes-1; i++) {
                    // On récupère le nombre de client de l'étape i
                    int nbClientEtapeI = tabclient[i * tailleEtapesEnMemoire];

                    // Etape 0 SasEntrée
                    if(i==0){

                        System.out.printf("étape %d (SasEntrée) %d clients : ", i, nbClientEtapeI);

                    }// Etape 1 SasSortie (Comme dans le cours)
                    else if(i==1){
                        System.out.printf("étape %d (SasSortie) %d clients : ", i, nbClientEtapeI);

                    }// Les autres étapes sont soit (Activite | ActiviteRestreinte | Guichet)
                    else{
                        System.out.printf("étape %d (%s) %d clients : ", i, monde.getNomEtape(i-2), nbClientEtapeI);

                    }
                    //Affichage des identifiants des clients à l'étape i
                    for (int j = 0; j < nbClientEtapeI; j++) {
                        int pidClientEtapeI = tabclient[i *tailleEtapesEnMemoire + j + 1];
                        System.out.printf("%d,",pidClientEtapeI);


                    }
                    System.out.print("\n");
                }
                System.out.print("\n");
            //Condition d'arrêt : quand tous les clients arrivent à la sortie ( Etape 1 )
            if ( tabclient[tailleEtapesEnMemoire]==nbClients) {
                break;
            }



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---------------------------------------------------------------");
        }
        gestionnaireClients.nettoyer();
        nettoyage();
        System.out.println();
        System.out.println("Simulation terminée.\n");


    }

    /**
     * Setter du nombre de Clients dans la simulation
     * @param nbClients nombre de clients
     */
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }
    //Fonctions natives
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();

}
