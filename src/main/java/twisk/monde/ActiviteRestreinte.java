package twisk.monde;

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
     * @param temps temps dans l'activité
     * @param ecart écart de temps
     */
    public ActiviteRestreinte(String nom, int temps, int ecart) {
        super(nom);
    }

}
