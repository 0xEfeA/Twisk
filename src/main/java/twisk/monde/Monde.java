package main.java.twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    // Gestionnaire qui contient toutes les étapes du monde
    GestionnaireEtapes lesEtapes ;
    // Sas d'entrée qui est prédécésseur de toutes les entrées du monde
    SasEntree entree;
    // Sas de sortie qui est successeurs de toutes les sorties du monde
    SasSortie sortie;
    /**
     * Constructeur
     */
    public Monde() {
        this.lesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
    }
    /**
     * @return renvoie les étapes du monde
     */
    @Override
    public Iterator<Etape> iterator() {
        return lesEtapes.iterator();
    }
    /**
     * Permet d'ajouter des étapes au monde
     * @param etape nom du Guichet
     */
    public void ajouter(Etape... etape) {

        lesEtapes.ajouterEtape(etape);
    }
    /**
     * Méthode qui renvoie le nombre d'étapes
     * @return le nombre d'étapes
     */
    public int nbEtapes(){
        return lesEtapes.nbEtapes();
    }
    /**
     * Méthode qui renvoie le nombre de guichet
     * @return le nombre de guichet
     */
    public int nbGuichet() {
        int nbEtapes = 0;
        for (Etape etape : lesEtapes) {
            if(etape.estUnGuichet()){
                nbEtapes++;

            }

        }
        return nbEtapes;
    }

    /**
     * Méthode qui ajoute les entrées du monde
     */
    void aCommmeEntree(Etape... etape) {
        // Ajoute le sas d'entrée comme prédécésseur de toutes les entrée du monde
        for (Etape e : etape ){
            entree.ajouterSuccesseur(e);
        }
    }
    /**
     * Méthode qui ajoute les sorties du monde
     */
    void aCommmeSortie(Etape... etape) {
        // Ajoute le sas de sortie comme successeur de toute les sorties du monde
        for (Etape e : etape ){
            e.ajouterSuccesseur(sortie);
        }
    }
}
