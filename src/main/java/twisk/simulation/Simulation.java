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
     * Affiche le monde sur la sortie standard
     * @param monde
     */
    public void simuler(Monde monde) {
        String codeC = monde.toC();
        environnement.creerFichier(codeC);
        environnement.compiler();
        environnement.construireLaBibliotheque();
    }
}
