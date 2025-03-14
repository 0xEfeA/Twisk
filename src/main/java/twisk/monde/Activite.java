package twisk.monde;



public class Activite extends Etape {

   //temps en passé dans l'activité
    private int temps;
    //réduction ou augmentation du temps passé dans l'activité
    private int ecartTemps;
    /**
     * Constructeur
     * @param nom le nom d'activité
     */
    public Activite(String nom) {
        super(nom);
    }
    /**
     * Constructeur
     * @param nom le nom d'activité
     * @param temps temps de l'activité
     * @param  ecartTemps delta de temps de l'activité
     */
    public Activite(String nom, int temps, int ecartTemps) {
        super(nom);
        this.temps = temps;
        this.ecartTemps = ecartTemps;
    }


    /**
     * Renvoie si oui ou non c'est une activité
     * @return boolean
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }
    /**
     * Renvoie si oui ou non c'est une guichet
     * @return boolean
     */
    @Override
    public boolean estUnGuichet() {
        return false;
    }
    /**
     * Renvoie La durée de l'actvité
     * @return un entier
     */
    public int getTemps() {
        return temps;
    }
    /**
     * Renvoie Le delta de la durée de l'actvité
     * @return un entier
     */
    public int getEcartTemps() {
        return ecartTemps;
    }

    public String toC(){
        StringBuilder sb = new StringBuilder();
        //Commentaire pour lisibilité dans code générer
        sb.append("// Activite").append(nom).append("\n");
        // délai(temps,ecartTemps);
        sb.append("delai(").append(temps).append(",").append(ecartTemps).append(");\n");
        // tranfert(activiter, successeur); pour tous les successeurs
        for (Etape etape: this.getSuccesseurs()){
            sb.append("tranfert(").append(nom).append(",").append(etape.getNom()).append(");\n");
        }
        return sb.toString();
    }

}
