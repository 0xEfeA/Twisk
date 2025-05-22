package twiskIG.outils;

public class TailleComposants {

    private static TailleComposants instance;

    // Dimensions par défaut des composants
    private double largeurActivite = 130;
    private double hauteurActivite = 35;

    private double largeurGuichet = 200;
    private double hauteurGuichet = 35;

    // Constructeur privé pour éviter l'instanciation externe
    private TailleComposants() {}

    // Méthode pour récupérer l'instance unique
    public static TailleComposants getInstance() {
        if (instance == null) {
            instance = new TailleComposants();
        }
        return instance;
    }


    public double getLargeurActivite() {
        return largeurActivite;
    }

    public double getLargeurGuichet() {
        return largeurGuichet;
    }

    public void setLargeurGuichet(double largeurGuichet) {
        this.largeurGuichet = largeurGuichet;
    }

    public double getHauteurGuichet() {
        return hauteurGuichet;
    }

    public void setHauteurGuichet(double hauteurGuichet) {
        this.hauteurGuichet = hauteurGuichet;
    }

    public double getHauteurActivite() {
        return hauteurActivite;
    }

    public void setLargeurActivite(double largeur) {
        this.largeurActivite = largeur;
    }

    public void setHauteurActivite(double hauteur) {
        this.hauteurActivite = hauteur;
    }
}

