package main.java.twisk.monde;


public class Guichet   extends Etape {
    int nbJetons;
    public Guichet(String nom) {
        super(nom);
    }
    public Guichet(String nom, int nbJetons) {
        super(nom);
        this.nbJetons = nbJetons;
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

