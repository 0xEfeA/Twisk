package twisk.simulation;

import javafx.application.Platform;
import javafx.concurrent.Task;
import twisk.monde.*;
import twisk.outils.KitC;
import twisk.outils.ThreadsManager;
import twiskIG.mondeIG.SujetObserve;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Simulation extends SujetObserve {
    private KitC environnement;
    private int nbClients = 5 ; // Par défaut on a 3 clients
    private GestionnaireClients gestionnaireClients;
    private Map<String, Integer> nbClientsParEtape = new ConcurrentHashMap<>();

    private int[] tabsimu;

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
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //Instanciation du gestionnaire de clientsgestionnaireClients
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
                int nbClients = Simulation.this.nbClients;
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
                tabsimu = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);
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
                    //notifierObservateurs();
                    Platform.runLater(() -> notifierObservateurs());
                    // taille de ségment mémoire d'une étape (nbClient + 1 case qui stock le nombre de client en mémoire)
                    int tailleEtapesEnMemoire = nbClients+1;
                    nbClientsParEtape.clear();
                    //Déplacement des client à travers les étapes
                    for (int i = 0; i < nbEtapes; i++) {
                        int nbclients = tabclient[i * tailleEtapesEnMemoire];
                        for (int j = 0; j < nbclients; j++) {
                            int pid = tabclient[j + 1 + i * tailleEtapesEnMemoire];

                            // Move the client and update counts
                            if (i == 0) {
                                gestionnaireClients.allerA(pid, monde.getEntree(), j);
                                synchronized (nbClientsParEtape){
                                    nbClientsParEtape.put(
                                            monde.getEntree().getNom(),
                                            nbClientsParEtape.getOrDefault(monde.getEntree().getNom(), 0) + 1
                                    );
                                }
                            } else if (i == 1) {
                                gestionnaireClients.allerA(pid, monde.getSortie(), j);
                                synchronized (nbClientsParEtape) {
                                    nbClientsParEtape.put(
                                            monde.getSortie().getNom(),
                                            nbClientsParEtape.getOrDefault(monde.getSortie().getNom(), 0) + 1
                                    );
                                }
                            } else {
                                String nomEtape = monde.getNomEtape(i - 2);
                                gestionnaireClients.allerA(pid, monde.getEtapeI(i - 2), j);
                                synchronized (nbClientsParEtape) {
                                    nbClientsParEtape.put(
                                            nomEtape,
                                            nbClientsParEtape.getOrDefault(nomEtape, 0) + 1
                                    );
                                }
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

                            double rayonClient = 5.0;
                            // Conversion des coordonnées pour un affichage horizontal (par exemple, clients espacés de 20 pixels)
                            double posXClient = (j * 20) + 10; // Décalage pour éviter que tous les cercles soient au même endroit
                            double posYClient = 10; // Ligne horizontale au début

                            System.out.printf(" (Coordonnées: x=%.1f, y=%.1f)", posXClient, posYClient);
                        }
                        System.out.print("\n");
                    }
                    System.out.print("\n");
                    //Condition d'arrêt : quand tous les clients arrivent à la sortie ( Etape 1 )
                    if ( tabclient[tailleEtapesEnMemoire]==nbClients) {
                        break;
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println("---------------------------------------------------------------");
                }
                gestionnaireClients.nettoyer();
                nettoyage();
                //notifierObservateurs();
                System.out.println();
                System.out.println("Simulation terminée.\n");
                Platform.runLater(() -> notifierObservateurs());
                return null;
            }
        };
        ThreadsManager.getInstance().lancer(task);

    }

    /**
     * Setter du nombre de Clients dans la simulation
     * @param nbClients nombre de clients
     */
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    public int getNbClients() {
        return nbClients;
    }

    //Fonctions natives
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();

    public Map<String, Integer> getNbClientsParEtape() {
        //System.out.println("getNbClientsParEtape() called.");
        //System.out.println("nbClientsParEtape content: " + nbClientsParEtape);
        return nbClientsParEtape;
    }

    public KitC getEnvironnement() {
        return environnement;
    }

    public void killProcesses(int[] pids) {
        for (int pid : pids) {
            if (isProcessAlive(pid)) {
                try {
                    new ProcessBuilder("kill", "-9", String.valueOf(pid)).start().waitFor();
                    Platform.runLater(() -> notifierObservateurs());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isProcessAlive(int pid) {
        try {
            Process check = new ProcessBuilder("kill", "-0", String.valueOf(pid)).start();
            int exitCode = check.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    public int[] getTabsimu() {
        return tabsimu;
    }
}
