package twisk.monde;


import twisk.outils.FabriqueNumero;

public class Guichet   extends Etape {
    // Nombre de personne que le guichet peut acceuillir
    int nbJetons;

    private int numeroSemaphore;

    /**
     * Constructeur
     * @param nom nom du Guichet
     */
    public Guichet(String nom) {
        super(nom);
        this.numeroSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Constructeur
     * @param nom nom du Guichet
     * @param nbJetons nombre de jetons ( personne pouvant aller dans le guichet)
     */
    public Guichet(String nom, int nbJetons) {
        super(nom);
        this.nbJetons = nbJetons;
        this.numeroSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();

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

    @Override
    public String toC() {
        StringBuilder sb = new StringBuilder();
        //Commentaire dans code C
        sb.append("//Guichet : ").append(this.nom).append("\n");
        //P(ids,num_sem_guichet);
        sb.append("P(ids,").append(this.getNumeroSemaphore()).append(");\n");
        // Délai
        sb.append("delai(4,2);\n");
        //V(ids,num_sem_guichet);
        sb.append("V(ids,").append(this.getNumeroSemaphore()).append(");\n");
        // tranfert(guichet, successeur); pour tous les successeurs

        for(Etape etape: this.getSuccesseurs()) {
            sb.append("transfert(").append(nom).append(",").append(etape.getNom()).append(");\n");
        }
        return sb.toString();
    }

    /**
     * @return  le numéro sémaphore du guichet
     */
    public int getNumeroSemaphore() {
        return numeroSemaphore;
    }
}

