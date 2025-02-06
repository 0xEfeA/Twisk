package main.java.twisk.monde;


public class Guichet   extends Etape {
    // Nombre de personne que le guichet peut acceuillir
    int nbJetons;
    /**
     * Constructeur
     * @param nom nom du Guichet
     */
    public Guichet(String nom) {
        super(nom);
    }
    /**
     * Constructeur
     * @param nom nom du Guichet
     * @param nbJetons nombre de jetons ( personne pouvant aller dans le guichet)
     */
    public Guichet(String nom, int nbJetons) {
        super(nom);
        this.nbJetons = nbJetons;
    }
    /**
     * @return  si c'est une activité
     */
    @Override
    public boolean estUneActivite() {
        return false;
    }
    /**
     * @return  si c'est un guichet
     */
    @Override
    public boolean estUnGuichet() {
        return true;
    }
}

