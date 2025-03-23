package twisk.monde;

import java.text.Normalizer;

public class SasEntree extends Activite{
    /**
     * Constructeur
     */
    public SasEntree() {
        super("sasEntree");
    }

    @Override
    public String toC() {
        StringBuilder sb = new StringBuilder();
        // entrer(sasEntree); avec #define sasEntree 0
        sb.append("entrer(").append(genererNomConstante(this.nom)).append(");\n").append("delai(4,2);\n");

        // transfert(sasEntree, successeur);
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
