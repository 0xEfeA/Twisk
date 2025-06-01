package twiskIG.simulationig;

import twisk.monde.*;
import twisk.outils.ThreadsManager;
import twisk.simulation.Simulation;
import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.vues.Observateur;

import java.util.HashSet;
import java.util.Set;

public class SimulationIG implements Observateur {

    private final Simulation sim;
    private final MondeIG mondeIG;
    private boolean encoursSimulation= false;
    /**
     * Constructeur
     * @param sim la simulation
     * @param mondeIG le mondeIG
     */
    public SimulationIG(Simulation sim, MondeIG mondeIG) {
        this.sim = sim;
        this.mondeIG = mondeIG;
        this.mondeIG.ajouterObservateur(this);
        this.sim.ajouterObservateur(this);
    }

    /**
     * Vérifie le monde créé dans l'interface, crée le monde dans le modèle puis lance la simulation
     * @throws MondeException
     */
    public void simuler() throws MondeException {
        verifierMondeIG();
        Monde monde = creerMonde();
        sim.simuler(monde);
        encoursSimulation = true;
        mondeIG.notifierObservateurs();
    }

    /**
     * Construit le monde dans le modèle (Monde) depuis MondeIG
     * @return Monde
     */
    public Monde creerMonde() {
        CorrespondancesEtapes corr = new CorrespondancesEtapes();
        Monde monde = new Monde();
        Etape etp;

        // Créer les étapes dans le modèle depuis les étapes dans mondeIG
        for (EtapeIG etape : mondeIG.getEtapes()) {
            if (etape.estUnGuichet()) {
                etp = new Guichet(etape.getNom(), etape.getnbJetons());
            } else if (etape.estUneActiviteRestreinte()) {
                etp = new ActiviteRestreinte(etape.getNom(), etape.getDelai(), etape.getEcart());
            } else {
                etp = new Activite(etape.getNom(), etape.getDelai(), etape.getEcart());
            }
            corr.ajouter(etape, etp);
            monde.ajouter(etp);
        }

        // Ajoute les successeurs dans le modèle
        for (EtapeIG etapeig : mondeIG.getEtapes()) {
            Etape etape = corr.get(etapeig);
            for (EtapeIG succ : etapeig.getSuccesseurs()) {
                Etape succCorr = corr.get(succ);
                etape.ajouterSuccesseur(succCorr);
            }
        }

        // Ajoute entrée et sortie dans le modèle
        for (EtapeIG etapeig : mondeIG.getEtapes()) {
            Etape etape = corr.get(etapeig);
            if (etapeig.estEntree()) {
                monde.aCommeEntree(etape);
               monde.getEntree().setNomFonctionDelai(mondeIG.getNomLoiArrivee());
            }
            if (etapeig.estSortie()) {
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
        setActiviteRestreinte();
        if (!verifierEntreeSortie()) {
            throw new MondeException("Une activité n'est pas reliée à une entrée ou une sortie");
        }
        if (!verifierGrapheConnecte()) {
            throw new MondeException("Une activité n'est pas reliée correctement dans le monde");
        }
        if (mondeIG.getEtapes().isEmpty()) {
            throw new MondeException("Le monde est vide");
        }
    }

    /**
     * Si une activité est précédée d'un guichet, elle devient restreinte
     */
    public void setActiviteRestreinte() {
        for (EtapeIG etape : mondeIG.getEtapes()) {
            for (EtapeIG pred : etape.getPredecesseurs()) {
                if (pred.estUnGuichet() && etape.estUneActivite()) {
                    etape.setEstActiviteRestreinte(true);
                }
            }
        }
    }

    /**
     * Vérifie que toutes les étapes sont reliées à une entrée et une sortie
     * @return boolean
     */
    public boolean verifierEntreeSortie() {
        for (EtapeIG etape : mondeIG.getEtapes()) {
            if (!etape.estEntree() && !etape.estSortie()) {
                if (!aUneSortie(etape, new HashSet<>())) {
                    return false;
                }
                if (!aUneEntree(etape, new HashSet<>())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Vérifie que le graphe est connecté
     * @return boolean
     */
    public boolean verifierGrapheConnecte() {
        for (EtapeIG etape : mondeIG.getEtapes()) {
            if (!etape.estEntree() && etape.getPredecesseurs().isEmpty()) {
                return false;
            }
            if (!etape.estSortie() && etape.getSuccesseurs().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * DFS pour vérifier la liaison à une entrée
     */
    public boolean aUneEntree(EtapeIG etape, Set<EtapeIG> parcourus) {
        if (etape.estEntree()) {
            return true;
        }
        parcourus.add(etape);
        for (EtapeIG pred : etape.getPredecesseurs()) {
            if (!parcourus.contains(pred) && aUneEntree(pred, parcourus)) {
                return true;
            }
        }
        return false;
    }

    /**
     * DFS pour vérifier la liaison à une sortie
     */
    public boolean aUneSortie(EtapeIG etape, Set<EtapeIG> parcourus) {
        if (etape.estSortie()) {
            return true;
        }
        parcourus.add(etape);
        for (EtapeIG succ : etape.getSuccesseurs()) {
            if (!parcourus.contains(succ) && aUneSortie(succ, parcourus)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ajoute les prédécesseurs aux étapes
     */
    public void setPredecesseur() {
        for (ArcIG arcig : mondeIG.getArcs()) {
            arcig.getArrivee().getEtape().setPredecesseurs(arcig.getDepart().getEtape());
        }
    }

    /**
     * Ajoute les successeurs aux étapes
     */
    public void setSuccesseur() {
        for (ArcIG arcig : mondeIG.getArcs()) {
            arcig.getDepart().getEtape().setSuccesseurs(arcig.getArrivee().getEtape());
        }
    }

    /**
     * Réagit aux changements dans le modèle
     */
    @Override
    public void reagir() {
        mondeIG.notifierObservateurs();
    }

    public Simulation getSim() {
        return sim;
    }

    public boolean isEncoursSimulation() {
        return encoursSimulation;
    }

    public void setEncoursSimulation(boolean encoursSimulation) {
        this.encoursSimulation = encoursSimulation;
        sim.notifierObservateurs();
    }

    public void reinitialiserSimulation() {
        ThreadsManager.getInstance().detruireTout();
        sim.killProcesses(sim.getTabsimu());
        setEncoursSimulation(false);
        reagir();
    }

}
