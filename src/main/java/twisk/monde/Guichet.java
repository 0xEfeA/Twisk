package main.java.twisk.monde;

import java.util.Iterator;

public class Guichet  extends Etape {
    private int nbJetons;

    public Guichet(String nom) {
        super(nom);
    }


    @Override
    public Iterator<Etape> iterator() {
        return null;
    }

    @Override
    public void ajouterSuccesseur(Etape... e) {

    }

    @Override
    public int nbSuccesseurs() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean estUneActivite() {
        return false;
    }

    @Override
    public boolean estUnGuichet() {
        return false;
    }
}
