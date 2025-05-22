package twiskIG.simulationig;

import twisk.monde.Etape;
import twiskIG.mondeIG.EtapeIG;

import java.util.HashMap;

public class CorrespondancesEtapes {
    private HashMap<EtapeIG, Etape> correspondances;
    public CorrespondancesEtapes(){
        correspondances = new HashMap<>();
    }
    public void ajouter(EtapeIG etapeig, Etape etape){
        correspondances.put(etapeig, etape);
    }
    public Etape get(EtapeIG etapeig){
        return correspondances.get(etapeig);
    }
}
