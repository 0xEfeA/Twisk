package main.java.twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    GestionnaireEtapes lesEtapes ;
    SasEntree entree;
    SasSortie sortie;

    public Monde() {
        this.lesEtapes = new GestionnaireEtapes();
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
    }

    @Override
    public Iterator<Etape> iterator() {
        return lesEtapes.iterator();
    }

    public void ajouter(Etape... etape) {

        lesEtapes.ajouterEtape(etape);
    }
    public int nbEtapes(){
        return lesEtapes.nbEtapes();
    }
    public int nbGuichet() {
        int nbEtapes = 0;
        for (Etape etape : lesEtapes) {
            if(etape.estUnGuichet()){
                nbEtapes++;

            }

        }
        return nbEtapes;
    }


    void aCommmeEntree(Etape... etape) {
        for (Etape e : etape ){
            entree.ajouterSuccesseur(e);
        }
    }
    void aCommmeSortie(Etape... etape) {
        for (Etape e : etape ){
            e.ajouterSuccesseur(sortie);
        }
    }
}
