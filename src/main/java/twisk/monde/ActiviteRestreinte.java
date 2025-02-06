package main.java.twisk.monde;

public class ActiviteRestreinte extends Activite {
    /**
     * Constructeur
     * @param nom le nom d'activité
     */
    public ActiviteRestreinte(String nom) {
        super(nom);
    }
    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom, t, e);
    }
}
