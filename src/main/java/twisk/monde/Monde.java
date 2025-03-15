package twisk.monde;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Monde implements Iterable<Etape> {
    private final GestionnaireEtapes lesEtapes ;
    private final SasEntree entree;
    private final SasSortie sortie;

    /**
     * Constructeur
     */
    public Monde() {
        this.lesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
    }

    /**
     * Function qui gère les Etapes qui sont des Entrées
     * @param etape les etapes passé en paramètre
     */
    public void aCommeEntree(Etape... etape) {
        for (Etape e : etape ){
            entree.ajouterSuccesseur(e);
        }
    }

    /**
     * Function qui gère les Etapes qui sont des Sortoes
     * @param etape les etapes passé en paramètre
     */
    public void aCommeSortie(Etape... etape) {
        for (Etape e : etape ){
            e.ajouterSuccesseur(sortie);
        }
    }

    /**
     * @return la collection d'etapes dans le monde
     */
    public GestionnaireEtapes getLesEtapes() {
        return lesEtapes;
    }

    /**
     * Fonction qui ajout les etapes dans un monde
     * @param etape les etape(s) à ajouter
     */
    public void ajouter(Etape... etape) {
        lesEtapes.ajouterEtape(etape);
    }

    /**
     * Function qui compte le nombre d'etapes dans un monde
     * @return le nombre d'etapes
     */
    public int nbEtapes(){
        return lesEtapes.nbEtapes();
    }

    /**
     * Function qui compte le nombre de guichets dans un monde
     * @return le nombre de guichets
     */
    public int nbGuichet() {
        int nbEtapes = 0;
        for (Etape etape : lesEtapes) {
            if(etape.estUnGuichet()){
                nbEtapes++;

            }

        }
        return nbEtapes;
    }

    /**
     * Iterator
     */
    @Override
    public Iterator<Etape> iterator() {
        return lesEtapes.iterator();
    }


    /**
     * Méthode toString de monde qui affiche sous cette forme
     * Monde :
     * Entree : "Activtité"
     * Activité1: 2 successeurs - "Actvité", Guichet,
     * Activité2 : 1 successeurs - SasSortie,
     * Activté2: 1 successeurs - "Activité",
     * Sortie :"Activité"
     * @return stringbuilder
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Monde : \n").append("Entree :");
        boolean first = true;
        for (Etape etape : entree.getSuccesseurs()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(etape.nom);
            first = false;
        }
        sb.append("\n");
        for (Etape etape : lesEtapes) {
            sb.append(etape.toString()).append("\n");
        }
        sb.append("Sortie :");
        boolean one = true;
        for (Etape etape : lesEtapes) {
            if (etape.getSuccesseurs().contains(sortie)) {
                if (!one) {
                    sb.append(", ");
                }
                sb.append(etape.nom);
                one = false;
            }
        }
        return sb.toString();
    }

    public String toC() {
        StringBuilder sb = new StringBuilder();
        sb.append("#include \"def.h\"\n");
        sb.append("#define SASENTREE ").append(entree.getNumeroEtape()).append("\n")
                .append("#define SASSORTIE 1\n");

        int tempIndex = 0;
        Map<String, String> tempNames = new HashMap<>();

        for (Etape etape : lesEtapes) {
            String nomConstante = genererNomConstante(etape.nom);

            // If the name is empty or "_", assign a unique temporary name
            if (nomConstante.equals("_") || nomConstante.isEmpty()) {
                nomConstante = "TEMP_ETAPE_" + (tempIndex++);
                tempNames.put(etape.nom, nomConstante);
            }

            sb.append("#define ").append(nomConstante).append(" ")
                    .append(etape.getNumeroEtape()).append("\n");
        }
        sb.append("\n");
        sb.append("void simulation(int ids){\n").append(entree.toC()).append("\n");

        for (Etape etape : lesEtapes) {
            if (etape.estUnGuichet()) {
                //Cast en guichet pour pouvoir appelé la méthode dans Guichet
                Guichet guichet = (Guichet) etape;
                sb.append(guichet.toC()).append("\n");
            } else {
                sb.append(etape.toC()).append("\n");
            }
        }
        sb.append("}");
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

