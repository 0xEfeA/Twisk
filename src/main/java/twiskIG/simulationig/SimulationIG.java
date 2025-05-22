package twiskIG.simulationig;

import twisk.monde.*;
import twisk.simulation.Simulation;
import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;

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

    /**
     * Verifie le monde créé dans l'interface, créer le monde dans le modèle puis lance la simulation
     * @throws MondeException
     */
    public void simuler() throws MondeException {

        verifierMondeIG();
        Monde monde = creerMonde();
        monde.getLesEtapes().reoganiser(monde.getEntree());

        Simulation simu = new Simulation();
        System.out.println(mondeIG);
        simu.simuler(monde);
    }

    /**
     * Construite monde dans modèle (Monde) depuis MondeIG
     * @return
     */
    public Monde creerMonde() {
        CorrespondancesEtapes corr = new CorrespondancesEtapes();
        Monde monde = new Monde();
        Etape etp;
        //créer etape dans le modele depuis les étapes dans mondeIG
        for (EtapeIG etape : mondeIG.getEtapes()) {
            if(etape.estUnGuichet()){
                 etp = new Guichet(etape.getNom(),etape.getnbJetons());
            } else if (etape.estUneActiviteRestreinte()) {
                etp = new ActiviteRestreinte(etape.getNom(),etape.getDelai(),etape.getEcart());

            }else {
                etp = new Activite(etape.getNom(),etape.getDelai(),etape.getEcart());
            }
            corr.ajouter(etape,etp);
            monde.ajouter(etp);
        }
        //Ajoute les successeurs dans le modele
        for (EtapeIG etapeig : mondeIG.getEtapes()) {
            Etape etape = corr.get(etapeig);
            for (EtapeIG succ : etapeig.getSuccesseurs()){
                Etape succCorr = corr.get(succ);
                etape.ajouterSuccesseur(succCorr);
            }
        }
        //Ajoute entrée sortie dans modele
        for(EtapeIG etapeig : mondeIG.getEtapes()){
            Etape etape = corr.get(etapeig);
            if(etapeig.estEntree()){
                monde.aCommeEntree(etape);
            }
            if(etapeig.estSortie()){
                monde.aCommeSortie(etape);
            }
        }
        return monde;
    }

    /**
     * Vérifie que le monde est bien construit
     * @throws MondeException
     */
    public void verifierMondeIG() throws MondeException {
        setActiviteRestreinte(); // Passe les activités après un guichet en restreinte
        if(!verifierEntreeSortie()){
            throw new MondeException("Une activité n'est pas reliée à une entrée ou sortie");
        }

        if(!verifierGrapheConnecte()){
            throw new MondeException("Une activité n'est pas reliée correctement dans le monde");
        }
        if(mondeIG.getEtapes().isEmpty()){
            throw new MondeException("Le monde est vide");
        }
    }

            /**
             * Si une activité est précédé d'un guichet elle devient restreinte
             */
            public void setActiviteRestreinte(){

                for (EtapeIG etape : mondeIG.getEtapes()) {
                    for(EtapeIG pred : etape.getPredecesseurs()){
                        if(pred.estUnGuichet() && etape.estUneActivite()){
                            etape.setEstActiviteRestreinte(true);
                        }
                    }
                }
            }




    /**
     * Parcours toutes les étapes et guichet et vérifie qu'ils sont reliées à une entrée et une sortie minimum
     * @return
     */
    public boolean verifierEntreeSortie(){


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
     * Vérifie que le monde (graphe) n'a pas d'activité ou guichet qui traine reliée à rien
     * @return
     */
    public boolean verifierGrapheConnecte(){
        for(EtapeIG etape : mondeIG.getEtapes()){
            if(!etape.estEntree() && etape.getPredecesseurs().isEmpty()){ // Cas où c'est pas une entrée et n'a pas de pred
                return false;
            }if(!etape.estSortie() && etape.getSuccesseurs().isEmpty()){ // Cas où c'est pas une sortie et n'a pas de succ
                return false;
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
