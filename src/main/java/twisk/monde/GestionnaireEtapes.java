package main.java.twisk.monde;

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
}
