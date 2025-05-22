import org.junit.jupiter.api.Test;
import twisk.monde.GestionnaireEtapes;
import twisk.simulation.Simulation;
import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.ActiviteIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;

import static org.junit.jupiter.api.Assertions.*;

class SimulationIGTest {
    @Test
    void testEntreeSortie() {

        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée",5,5);
        EtapeIG milieu = new ActiviteIG("milieu",5,5);
        EtapeIG sortie = new ActiviteIG("sortie",5,5);
        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        sortie.setPredecesseurs(milieu);
        entree.setEntree(true);
        sortie.setSortie(true);
        milieu.setSuccesseurs(sortie);
        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(sortie);
        SimulationIG simulation = new SimulationIG(monde);
        assertTrue(simulation.verifierEntreeSortie(),"Le monde est normalement bien formé");
    }
    @Test
    void testGrapheConnecter() {
        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée",5,5);
        EtapeIG milieu = new ActiviteIG("milieu",5,5);
        EtapeIG sortie = new ActiviteIG("sortie",5,5);
        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        sortie.setPredecesseurs(milieu);
        entree.setEntree(true);
        sortie.setSortie(true);
        milieu.setSuccesseurs(sortie);
        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(sortie);
        SimulationIG simulation = new SimulationIG(monde);
        assertTrue(simulation.verifierGrapheConnecte(),"Le monde est normalement bien formé");
    }

    @Test
    void testVerifierMonde() throws MondeException {
        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée",5,5);
        EtapeIG milieu = new ActiviteIG("milieu",5,5);
        EtapeIG sortie = new ActiviteIG("sortie",5,5);
        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        sortie.setPredecesseurs(milieu);
        entree.setEntree(true);
        sortie.setSortie(true);
        milieu.setSuccesseurs(sortie);
        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(sortie);
        SimulationIG simulation = new SimulationIG(monde);
        assertDoesNotThrow(simulation::verifierMondeIG,"Le monde est normalement bien formé");
    }
}