package main.java.twisk.outils;

public class FabriqueNumero {
    // Seule instance de fabrique numéro (Singleton)
    private static final FabriqueNumero instance = new FabriqueNumero();
    // Compteur du numéro d'étape
    private int cptEtape;
    // Numero de Sémaphore du guichet
    private int cptSemaphore;

    /**
     * Constructeur qui initialise les 2 compteurs
     */
    private FabriqueNumero() {
        cptEtape = 0;
        cptSemaphore = 1;
    }

    /**
     * Renvoie l'instance de la classe
     * @return  l'unique instance de la classe
     */
    public static FabriqueNumero getInstance() {
        return instance;
    }

    /**
     * Méthode renvoyant numéro d'étape
     * @return  renvoie le numéro d'étape
     */
    public int getNumeroEtape() {
        return cptEtape++;
    }
    /**
     * Méthode renvoyant numéro de sémaphore du guichet
     * @return  renvoie le numéro de sémaphore du guichet
     */
    public int getNumeroSemaphore() {
        return cptSemaphore++;
    }
    /**
     * Méthode qui réinitilise le numéro d'étapes et de sémaphore
     */    public void reset() {
        cptEtape = 0;
        cptSemaphore = 1;
    }
}
