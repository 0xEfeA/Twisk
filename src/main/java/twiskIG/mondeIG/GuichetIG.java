package twiskIG.mondeIG;

public class GuichetIG extends EtapeIG{
    private int nbJetons;
    private Boolean versLaDroite;
    private Boolean sensChoisi;
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
        this.versLaDroite = true;
        this.sensChoisi = false;
    }
    public GuichetIG(String nom, int larg, int haut, String id){
        super(nom,larg,haut,id);
        this.estGuichet = true;
        this.setnbJetons(1);
        this.versLaDroite = true;
        this.sensChoisi = false;
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

    public Boolean getVersLaDroite() {
        return versLaDroite;
    }
    public void setVersLaDroite(Boolean versLaDroite) {
        this.versLaDroite = versLaDroite;
        setSensChoisi(true);
    }
    public Boolean getSensChoisi() {
        return sensChoisi;
    }
    public void setSensChoisi(Boolean sensChoisi) {
        this.sensChoisi = sensChoisi;
    }
}


