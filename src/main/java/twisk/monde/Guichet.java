package main.java.twisk.monde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Guichet  extends Etape {
    private int nbJetons;
    private final ArrayList<Etape> successeurs;

    public Guichet(String nom) {
        super(nom);
        this.successeurs = new ArrayList<>();
    }


    @Override
    public Iterator<Etape> iterator() {
        return successeurs.iterator();
    }

    @Override
    public void ajouterSuccesseur(Etape... e) {
        Collections.addAll(successeurs, e);
    }

    @Override
    public int nbSuccesseurs() {
        return successeurs.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.nom).append(": ").append(this.nbSuccesseurs()).append(" - ");
        sb.append(this.nom).append(": ").append(this.nbSuccesseurs()).append(" successeurs").append(" - ");
        for (Etape e : successeurs) {
            sb.append(e.nom).append(", ");
        }
        return sb.toString();
    }

    @Override
    public boolean estUneActivite() {
        return false;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }
}
