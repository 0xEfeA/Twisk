package twiskIG.mondeIG;

public class GuichetIG extends EtapeIG{
    private int nbJetons;

    /**
     * Constructor for ActiviteIG.
     * @param nom The name of the guichet.
     * @param larg The width of the guichet.
     * @param haut The height of the guichet.
     */
    public GuichetIG(String nom, int larg, int haut){
        super(nom,larg,haut);
        this.estGuichet = true;
        this.setNbJetons(1);
    }
    @Override
    public boolean estUnGuichet() {
        return true;
    }
    public int getNbJetons() {
        return nbJetons;
    }

    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }
}


