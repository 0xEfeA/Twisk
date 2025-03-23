package twisk.monde;


import twisk.outils.FabriqueNumero;

import java.text.Normalizer;

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

    /**
     * Renvoie le nombre de jeton de l'activité
     * @return le nombre de jetons
     */
    public int getNbJetons(){
        return nbJetons;
    }
    @Override
    public String toC() {
        StringBuilder sb = new StringBuilder();

        // P(ids, num_sem_guichet);
        sb.append("P(ids,").append(this.getNumeroSemaphore()).append(");\n");

        // Délai
        sb.append("delai(4,2);\n");

        // V(ids, num_sem_guichet);
        sb.append("V(ids,").append(this.getNumeroSemaphore()).append(");\n");

        // transfert(guichet, successeur); pour tous les successeurs
        for (Etape etape : this.getSuccesseurs()) {
            sb.append("transfert(")
                    .append(genererNomConstante(this.nom))
                    .append(",")
                    .append(genererNomConstante(etape.getNom()))
                    .append(");\n");
        }

        return sb.toString();
    }


    /**
     * @return  le numéro sémaphore du guichet
     */
    public int getNumeroSemaphore() {
        return numeroSemaphore;
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

