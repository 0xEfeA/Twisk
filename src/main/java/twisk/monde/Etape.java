package main.java.twisk.monde;

import java.util.Iterator;

public abstract class Etape implements Iterable<Etape> {
   protected final String nom;


    public Etape(String nom) {
        this.nom = nom;
    }


    public abstract Iterator<Etape> iterator();
    public abstract void ajouterSuccesseur(Etape... e) ;
    public abstract int nbSuccesseurs();
    public abstract String toString();
    public abstract boolean estUneActivite();
    public abstract boolean estUnGuichet();

}
