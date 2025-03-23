package twisk.monde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GestionnaireEtapes implements  Iterable<Etape>{
    // Ensemble des étapes constituant le mondez
    ArrayList<Etape> list_etapes ;

    /**
     * Constructeur du GestionnaireEtapes
     */
    public GestionnaireEtapes(){
        this.list_etapes = new ArrayList<>();
    }

    /**
     * Renvoie l'ensemble des étapes
     * @return Ensemble des étapes
     */
    @Override
    public Iterator<Etape> iterator() {
        return list_etapes.iterator();
    }

    /**
     * Ajoute un ou des étape(s)
     * @param etapes  les étapes à ajouter au gestionnaire
     */
   public void ajouterEtape(Etape... etapes) {
        Collections.addAll(list_etapes, etapes);

    }

    /**
     * Renvoie le nombre d'étape
     * @return nombre d'étapes
     */
    public int nbEtapes(){
        return list_etapes.size();
    }

    /**
     * Renvoie le derniere element(Etape dans la collection)
     */
    public Etape lastEtape(){
        //return list_etapes.getLast();  //Pour Java 21 +
        return list_etapes.get(list_etapes.size() - 1);
    }

    /**
     * Renvoie la ième étape du monde
     * @param index indice de l'étape à renvoyer
     * @return Etape I
     */
    public Etape getEtapeI(int index) {
        return list_etapes.get(index);
    }

    /**
     * Réorganise les étapes dans leur ordre de succession
     * @param initial entree du monde
     */
    public void  reoganiser(Etape initial) {
        ArrayList<Etape> etapes_parcourus = new ArrayList<>();
        parcourirGraphe(initial, etapes_parcourus);
        // Etapes trié dans l'ordre de succession
        this.list_etapes = etapes_parcourus;
    }

    /**
     * Renvoie le parcour dans l'ordre des successeurs
     * @param etape Etape initial (entrée)
     * @param etapesParcourues Tableau qui stock parcours
     */
    private void parcourirGraphe(Etape etape, ArrayList<Etape> etapesParcourues) {
        for (Etape successeur : etape.getSuccesseurs()){
            // on s'arrete quand on arrive à la sortie
            if(successeur.estSasSortie())
                continue;
            if(!etapesParcourues.contains(successeur)){
                etapesParcourues.add(successeur);
                parcourirGraphe(successeur, etapesParcourues);
            }
        }
}
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Etape etape : list_etapes) {
            sb.append(etape.toString()).append(" -- ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
