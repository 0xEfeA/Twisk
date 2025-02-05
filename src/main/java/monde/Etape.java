package main.java.monde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class Etape implements Iterable<Etape> {
    private String nom;
    private  ArrayList<Etape> successeurs;

    public Etape(String nom) {
        this.nom = nom;
        successeurs = new ArrayList<>();
    }
    @Override
    public Iterator<Etape> iterator() {
        return successeurs.iterator();
    }
    public void ajouterSuccesseur(Etape... e) {
        Collections.addAll(successeurs, e);
    }
    public int nbSuccesseurs() {
        return successeurs.size();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.nom).append(": ").append(this.nbSuccesseurs()).append(" - ");
        for (Etape e : successeurs) {
            sb.append(e.nom).append(", ");
        }
        return sb.toString();
    }

}
