package main.java.twisk.monde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GestionnaireEtapes implements  Iterable<Etape>{
    ArrayList<Etape> list_etapes ;
    public GestionnaireEtapes(){
        this.list_etapes = new ArrayList<>();
    }
    @Override
    public Iterator<Etape> iterator() {
        return list_etapes.iterator();
    }
   public void ajouterEtape(Etape... etapes) {
        Collections.addAll(list_etapes, etapes);
    }
    public int nbEtapes(){
        return list_etapes.size();
    }
}
