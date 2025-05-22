package twiskIG.mondeIG;


import twiskIG.outils.FabriqueIdentifiant;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    protected String nom;
    protected String identifiant;
    protected int posX;
    protected int posY;
    protected int largeur;
    protected int hauteur;
    protected ArrayList<PointDeControleIG> pointsDeControle;
    protected boolean selectionnee = false;
    protected boolean estEntree;
    protected boolean estSortie;
    protected boolean estActivite;
    protected boolean estGuichet;
    protected ArrayList<EtapeIG> predecesseurs;
    protected ArrayList<EtapeIG> successeurs;
    protected boolean estActiviteRestreinte;
    protected int nbJetons;
    private int delai;
    private int ecart;
    /**
     * Constructor for EtapeIG.
     * @param nom The name of the step.
     * @param larg The width of the step.
     * @param haut The height of the step.
     */
    public EtapeIG(String nom, int larg, int haut) {
        this.nom = nom;
        this.identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        this.posX = (int) (Math.random() * 500);
        this.posY = (int) (Math.random() * 500);
        this.largeur = larg;
        this.hauteur = haut;
        this.pointsDeControle = new ArrayList<>();
        creerPointsDeControle();
        this.estEntree = false;
        this.estSortie = false;
        this.estActivite = false;
        this.estGuichet = false;
        this.predecesseurs = new ArrayList<>();
        this.successeurs = new ArrayList<>();
        this.estActiviteRestreinte = false;
        this.nbJetons = 0;
        this.delai = 0;
        this.ecart = 0;

    }
    public boolean estUneActiviteRestreinte() {
        return false;
    }

    public void setnbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }
    public int getnbJetons() {
        return nbJetons;
    }

    public void setEstActiviteRestreinte(boolean estActiviteRestreinte) {
        this.estActiviteRestreinte = estActiviteRestreinte;
    }
    public boolean estUneActivite() {
        return false;
    }


    public void setEstActivite(boolean estActivite) {
        this.estActivite = estActivite;
    }

    public boolean estUnGuichet() {
        return false;
    }

    public void setEstGuichet(boolean estGuichet) {
        this.estGuichet = estGuichet;
    }

    // Getter et Setter pour l'entrée et la sortie
    public boolean estEntree() {
        return estEntree;
    }

    public void setEntree(boolean estEntree) {
        this.estEntree = estEntree;
    }

    public boolean estSortie() {
        return estSortie;
    }

    public void setSortie(boolean estSortie) {
        this.estSortie = estSortie;
    }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getIdentifiant() { return identifiant; }

    public int getX() { return posX; }
    public void setX(int x) { this.posX = x; }

    public int getY() { return posY; }
    public void setY(int y) { this.posY = y; }

    public int getLargeur() { return largeur; }
    public void setLargeur(int largeur) { this.largeur = largeur; }

    public int getHauteur() { return hauteur; }
    public void setHauteur(int hauteur) { this.hauteur = hauteur; }

    /**
     * Crée 4 points de contrôle placés sur les milieux des côtés de l'étape.
     */
    private void creerPointsDeControle() {
        pointsDeControle.add(new PointDeControleIG(posX + largeur / 2, posY, this)); //up
        pointsDeControle.add(new PointDeControleIG(posX + largeur / 2, posY + hauteur, this)); //down
        pointsDeControle.add(new PointDeControleIG(posX, posY + hauteur / 2, this));  //left
        pointsDeControle.add(new PointDeControleIG(posX + largeur, posY + hauteur / 2, this)); //right
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        return pointsDeControle.iterator();
    }

    public ArrayList<PointDeControleIG> getPointsDeControle() {
        return pointsDeControle;
    }

    public void setPointsDeControle(ArrayList<PointDeControleIG> nouveauxPoints) {
        if (nouveauxPoints != null && nouveauxPoints.size() == this.pointsDeControle.size()) {
            for (int i = 0; i < pointsDeControle.size(); i++) {
                pointsDeControle.get(i).setX(nouveauxPoints.get(i).getX());
                pointsDeControle.get(i).setY(nouveauxPoints.get(i).getY());
            }
        }
    }

    /**
     * fonction pour determinée si un etape est selectioner ou pas
     * @return
     */
    public boolean estSelectionnee() {
        return selectionnee;
    }

    /**
     * fonction pour switcher entre selectionné et non
     */
    public void toggleSelection() {
        selectionnee = !selectionnee;
    }

    /**
     * Ajoute un predecesseurs
     * @param etapeIG
     */
    public void setPredecesseurs(EtapeIG etapeIG) {
        predecesseurs.add(etapeIG);
    }

    /**
     * Ajoute successeurs
     * @param etapeIG
     */
    public void setSuccesseurs(EtapeIG etapeIG) {
        successeurs.add(etapeIG);
    }

    public ArrayList<EtapeIG> getPredecesseurs() {
        return predecesseurs;
    }
    public ArrayList<EtapeIG> getSuccesseurs() {
        return successeurs;
    }
    public int getDelai() {
        return delai;
    }

    public int getEcart() {
        return ecart;
    }

    public void setDelai(int delai) {
        this.delai = delai;
    }

    public void setEcart(int ecart) {
        this.ecart = ecart;
    }


}
