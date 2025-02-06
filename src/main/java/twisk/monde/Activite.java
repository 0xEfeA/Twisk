package main.java.twisk.monde;



public class Activite extends Etape {
    private int temps;
    private int ecartTemps;

    public Activite(String nom) {
        super(nom);
    }




    @Override
    public boolean estUneActivite() {
        return true;
    }

    @Override
    public boolean estUnGuichet() {
        return false;
    }
}
