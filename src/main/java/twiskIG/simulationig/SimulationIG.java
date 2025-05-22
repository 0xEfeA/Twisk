package twiskIG.simulationig;

import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;

import java.util.ArrayList;

public class SimulationIG {

    private MondeIG mondeIG;

    /**
     * Constructeur
     * @param mondeIG
     */
    public SimulationIG(MondeIG mondeIG) {
        this.mondeIG = mondeIG;
    }

    /**
     * Parcours tous les arcs et ajoute les prédécesseurs
     */
    public void setPredecesseur(){
        for(ArcIG arcig : mondeIG.getArcs()){
            // Soit un ArcIG, son point de départ appartient forcément  au prédécesseur de l'étape du point d'arrvié
            arcig.getArrivee().getEtape().setPredecesseurs(arcig.getDepart().getEtape());
        }
    }

    /**
     * Parcours les arcs et ajoutes les successeurs
     */
    public void setSuccesseur(){
        for(ArcIG arcig : mondeIG.getArcs()){
            arcig.getDepart().getEtape().setSuccesseurs(arcig.getArrivee().getEtape());
        }
    }
}
