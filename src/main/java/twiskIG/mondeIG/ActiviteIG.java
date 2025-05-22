package twiskIG.mondeIG;

/**
 * Class representing an activity in the graphical world.
 */
public class ActiviteIG extends EtapeIG {
    private int delai;
    private int ecart;
    private boolean restreinte;
    /**
     * Constructor for ActiviteIG.
     * @param nom The name of the activity.
     * @param larg The width of the activity.
     * @param haut The height of the activity.
     */
    public ActiviteIG(String nom, int larg, int haut) {
        super(nom, larg, haut);
        this.estActivite = true;
        this.setEcart(1);
        this.setDelai(4);
        this.restreinte = false;
    }
    public boolean estUneActiviteRestreinte() {
        return restreinte;
    }
    public void setEstActiviteRestreinte(boolean estActiviteRestreinte) {
        this.restreinte = estActiviteRestreinte;
    }
    public int getDelai() {
        return delai;
    }

    public int getEcart() {
        return ecart;
    }

    public void setDelai(int delai) {
        this.delai = delai;
    }

    public void setEcart(int ecart) {
        this.ecart = ecart;
    }

    @Override
    public boolean estUneActivite() {
        return true;
    }

}
