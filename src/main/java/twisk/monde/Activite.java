package twisk.monde;


import java.text.Normalizer;

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

    @Override
    public boolean estSasEntree() {
        return false;
    }

    @Override
    public boolean estSasSortie() {
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
    /**
     * @return Code c pour l'activité
     */
    @Override
    public String toC() {
        StringBuilder sb = new StringBuilder();

        // délai(temps, ecartTemps);
        sb.append("delai(").append(temps).append(",").append(ecartTemps).append(");\n");

        // transfert(activite, successeur); pour tous les successeurs
        for (Etape etape: this.getSuccesseurs()) {
            sb.append("transfert(").append(genererNomConstante(nom)).append(",").append(genererNomConstante(etape.getNom())).append(");\n");
        }

        return sb.toString();
    }


    /**
     * Transforme un nom d'étape en un identifiant valide pour le C.
     * @param etape Nom de l'étape
     * @return Identifiant valide en C
     */
    private String genererNomConstante(String etape) {
        //Supprimer les accents
        String normalized = Normalizer.normalize(etape, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", ""); // Supprime les accents

        // Remplacer les caractères interdits par "_"
        normalized = normalized.replaceAll("[^a-zA-Z0-9]", "_");

        // Ajouter un préfixe si le nom commence par un chiffre
        if (Character.isDigit(normalized.charAt(0))) {
            normalized = "_" + normalized;
        }

        // Mettre en majuscule pour respecter la convention C
        return normalized.toUpperCase();
    }

}
