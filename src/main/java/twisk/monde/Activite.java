package main.java.twisk.monde;

public class Activite extends Etape {
    int temps;
    int ecartTemps;



    public Activite(String nom) {
        super(nom);
    }
    public Activite(String nom, int temps, int ecartTemps) {
        super(nom);
        this.temps = temps;
        this.ecartTemps = ecartTemps;
    }
    @Override
   public  boolean estUneActivite() {
        return  true;
    }

    @Override
   public  boolean estUnGuichet() {
        return false;
    }
}
