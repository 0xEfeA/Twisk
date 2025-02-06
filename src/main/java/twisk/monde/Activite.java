package main.java.twisk.monde;



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
     * Renvoie si oui on non c'est une activité
     * @return boolean
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }
    /**
     * Renvoie si oui on non c'est une guichet
     * @return boolean
     */
    @Override
    public boolean estUnGuichet() {
        return false;
    }
}
