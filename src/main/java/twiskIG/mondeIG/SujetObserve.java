package twiskIG.mondeIG;

import twiskIG.vues.Observateur;

import java.util.ArrayList;

public class SujetObserve {
    private ArrayList<Observateur> observateurs;


    /**
     * Constructeur
     */
    public SujetObserve() {
        this.observateurs = new ArrayList<>();
    }

    /**
     * Fonction pour ajouter un observateur
     * @param v l'observateur
     */
    public void ajouterObservateur(Observateur v){
        this.observateurs.add(v);
    }

    /**
     * Fonction pour mettre a jour les observateurs
     */
    public void notifierObservateurs(){
        for(Observateur e: observateurs){
            e.reagir();
        }
    }
}

