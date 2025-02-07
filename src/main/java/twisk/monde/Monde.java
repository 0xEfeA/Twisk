package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    private GestionnaireEtapes lesEtapes ;
    private SasEntree entree;
    private SasSortie sortie;

    /**
     * Constructeur
     */
    public Monde() {
        this.lesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
    }

    /**
     * Function qui gère les Etapes qui sont des Entrées
     * @param etape les etapes passé en paramètre
     */
    public void aCommmeEntree(Etape... etape) {
        for (Etape e : etape ){
            entree.ajouterSuccesseur(e);
        }
    }

    /**
     * Function qui gère les Etapes qui sont des Sortoes
     * @param etape les etapes passé en paramètre
     */
    public void aCommmeSortie(Etape... etape) {
        for (Etape e : etape ){
            e.ajouterSuccesseur(sortie);
        }
    }

    /**
     * @return la collection d'etapes dans le monde
     */
    public GestionnaireEtapes getLesEtapes() {
        return lesEtapes;
    }

    /**
     * Fonction qui ajout les etapes dans un monde
     * @param etape les etape(s) à ajouter
     */
    public void ajouter(Etape... etape) {
        lesEtapes.ajouterEtape(etape);
    }

    /**
     * Function qui compte le nombre d'etapes dans un monde
     * @return le nombre d'etapes
     */
    public int nbEtapes(){
        return lesEtapes.nbEtapes();
    }

    /**
     * Function qui compte le nombre de guichets dans un monde
     * @return le nombre de guichets
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
     * Iterator
     */
    @Override
    public Iterator<Etape> iterator() {
        return lesEtapes.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monde : Entrée ");
        // Affichage entrées
        sb.append(entree.getSuccesseurs().toString());
        //Affichage Etapes
        for (Etape etape : lesEtapes) {
            etape.toString();
        }
        sb.append("\nMonde : Sortie ");
        // Affichage sorties
        for(Etape etape : lesEtapes){
            if (etape.getSuccesseurs().contains(sortie)  ){
                sb.append(etape.getSuccesseurs().toString());
            }
        }
        return sb.toString();
    }

}
