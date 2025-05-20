package twiskIG.outils;

public class TailleComposants {

    private static TailleComposants instance;

    // Dimensions par défaut des composants
    private double largeurActivite = 130;
    private double hauteurActivite = 35;

    // Constructeur privé pour éviter l'instanciation externe
    private TailleComposants() {}

    // Méthode pour récupérer l'instance unique
    public static TailleComposants getInstance() {
        if (instance == null) {
            instance = new TailleComposants();
        }
        return instance;
    }

    // Getter pour la largeur de l'activité
    public double getLargeurActivite() {
        return largeurActivite;
    }

    // Getter pour la hauteur de l'activité
    public double getHauteurActivite() {
        return hauteurActivite;
    }

    // Méthodes pour modifier la taille si nécessaire
    public void setLargeurActivite(double largeur) {
        this.largeurActivite = largeur;
    }

    public void setHauteurActivite(double hauteur) {
        this.hauteurActivite = hauteur;
    }
}

