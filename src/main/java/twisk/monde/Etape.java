package main.java.twisk.monde;

import main.java.twisk.outils.FabriqueNumero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class Etape implements Iterable<Etape> {
    // Nom de l'étape
    protected final String nom;
    // Liste des successeurs de cette étape
   private final ArrayList<Etape> successeurs;

   // Numéro d'étape
    private int numero;

    /**
     * Constructeur
     * @param  nom nom de l'étape
     */
    public Etape(String nom) {
        this.nom = nom;
        this.successeurs = new ArrayList<>();
        this.numero = FabriqueNumero.getInstance().getNumeroEtape(); // Assignation d'un numéro unique

    }

    /**
     * Itérateur qui parcourt les successeurs de l'étape
     * @return l'ensemble des successeurs de l'étape
     */
    public  Iterator<Etape> iterator(){
        return successeurs.iterator();
    }
    /**
     * Ajoute un ou plusieurs successeur(s)
     * @param  e Différentes successeur à ajouter
     */
    public  void ajouterSuccesseur(Etape... e) {
        Collections.addAll(successeurs, e);
    }
    /**
     * Renvoie le nombre de successeurs de l'étape
     * @return Nb successeurs
     */
    public  int nbSuccesseurs(){
        return successeurs.size();
    }
    /**
     * Méthode toString pour l'étape
     * @return StringBuilder de l'étape et ses successeurs
     */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.nom).append(": ").append(this.nbSuccesseurs()).append(" successeurs").append(" - ");
            for (Etape e : successeurs) {
                sb.append(e.nom).append(", ");
            }
            return sb.toString();
        }

    /**
     * Renvoie si oui ou non, c'est une activité
     * @return bolleen
     */
    public abstract boolean estUneActivite();
    /**
     * Renvoie si oui ou non, c'est un guichet
     * @return bolleen
     */
    public abstract boolean estUnGuichet();
    /**
     * @return  le numéro d'étape du guichet
     */
    public int getNumeroEtape() {
        return numero;
    }


}
