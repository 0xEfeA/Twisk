package twiskIG.simulationig;

import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SimulationIG {

    private MondeIG mondeIG;

    /**
     * Constructeur
     * @param mondeIG
     */
    public SimulationIG(MondeIG mondeIG) {
        this.mondeIG = mondeIG;
    }
    public void verifierMondeIG() throws MondeException {

    }

    /**
     * Parcours toutes les étapes et guichet et vérifie qu'ils sont reliées à une entrée et une sortie minimum
     * @return
     */
    public boolean verifierEntreeSortie(){
        //On construite les listes de pred et succ
        setPredecesseur();
        setSuccesseur();

        for(EtapeIG etape : mondeIG.getEtapes()){
            if(!etape.estEntree() && !etape.estSortie()){
                if(!aUneSortie(etape,new HashSet<>())){ //vérifie chemin vers sortie
                    return false;
                }
                if(!aUneEntree(etape,new HashSet<>())){//vérifie chemin vers entrée
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Parcours récursivement tous les prédécesseurs pour trouver une entrée (DFS cf : cours d'algo)
     * @param etape
     * @param parcourus
     * @return true si etape lié à une entrée false sinon
     */
    public boolean aUneEntree(EtapeIG etape, Set<EtapeIG> parcourus){
        if(etape.estEntree()){
            return true;
        }
        parcourus.add(etape);
        for (EtapeIG pred : etape.getPredecesseurs()){
            if(!parcourus.contains(pred) && aUneEntree(pred, parcourus)){
                return true;
            }
        }
        return false;
    }
    public boolean aUneSortie(EtapeIG etape, Set<EtapeIG> parcourus){
        if(etape.estSortie()){
            return true;
        }
        parcourus.add(etape);
        for (EtapeIG succ : etape.getSuccesseurs()){
            if(!parcourus.contains(succ) && aUneSortie(succ, parcourus)){
                return true;
            }
        }
        return false;
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
