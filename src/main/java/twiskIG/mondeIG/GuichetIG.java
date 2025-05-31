package twiskIG.mondeIG;

public class GuichetIG extends EtapeIG{
    private int nbJetons;
    private boolean sensDeplacement = false; // true: left to right; false: right to left

    /**
     * Constructor for ActiviteIG.
     * @param nom The name of the guichet.
     * @param larg The width of the guichet.
     * @param haut The height of the guichet.
     */
    public GuichetIG(String nom, int larg, int haut){
        super(nom,larg,haut);
        this.estGuichet = true;
        this.setnbJetons(1);
    }
    public GuichetIG(String nom, int larg, int haut, String id){
        super(nom,larg,haut,id);
        this.estGuichet = true;
        this.setnbJetons(1);
    }
    @Override
    public boolean estUnGuichet() {
        return true;
    }
    public int getNbJetons() {
        return nbJetons;
    }
        @Override
    public void setnbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }
    @Override
    public int getnbJetons() {
        return this.nbJetons;
    }

    public void setSensDeplacement(boolean sens) {
        this.sensDeplacement = sens;
    }

    public boolean getSensDeplacement() {
        return this.sensDeplacement;
    }

}


