package main.java.twisk.monde;

public class ActiviteRestreinte extends Activite {
    /**
     * Constructeur
     * @param nom le nom d'activité
     */
    public ActiviteRestreinte(String nom) {
        super(nom);
    }
    /**
     * Constructeur
     * @param nom nom de l'activité
     * @param t temps dans l'activité
     * @param e écart de temps
     */
    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom);
    }
}
