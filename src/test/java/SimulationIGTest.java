import org.junit.jupiter.api.Test;
import twisk.simulation.Simulation;
import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.ActiviteIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.GuichetIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;

import static org.junit.jupiter.api.Assertions.*;

class SimulationIGTest {

    @Test
    void testEntreeSortie() {
        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée", 5, 5);
        EtapeIG milieu = new ActiviteIG("milieu", 5, 5);
        EtapeIG sortie = new ActiviteIG("sortie", 5, 5);

        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        milieu.setSuccesseurs(sortie);
        sortie.setPredecesseurs(milieu);

        entree.setEntree(true);
        sortie.setSortie(true);

        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(sortie);

        Simulation sim = new Simulation();
        SimulationIG simulationIG = new SimulationIG(sim, monde);

        assertTrue(simulationIG.verifierEntreeSortie(), "Le monde est normalement bien formé");
    }

    @Test
    void testGrapheConnecter() {
        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée", 5, 5);
        EtapeIG milieu = new ActiviteIG("milieu", 5, 5);
        EtapeIG sortie = new ActiviteIG("sortie", 5, 5);

        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        milieu.setSuccesseurs(sortie);
        sortie.setPredecesseurs(milieu);

        entree.setEntree(true);
        sortie.setSortie(true);

        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(sortie);

        Simulation sim = new Simulation();
        SimulationIG simulationIG = new SimulationIG(sim, monde);

        assertTrue(simulationIG.verifierGrapheConnecte(), "Le monde est normalement bien formé");
    }

    @Test
    void testVerifierMonde() throws MondeException {
        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée", 5, 5);
        EtapeIG milieu = new ActiviteIG("milieu", 5, 5);
        EtapeIG sortie = new ActiviteIG("sortie", 5, 5);

        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        milieu.setSuccesseurs(sortie);
        sortie.setPredecesseurs(milieu);

        entree.setEntree(true);
        sortie.setSortie(true);

        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(sortie);

        Simulation sim = new Simulation();
        SimulationIG simulationIG = new SimulationIG(sim, monde);

        assertDoesNotThrow(simulationIG::verifierMondeIG, "Le monde est normalement bien formé");
    }

    @Test
    void testSimuler() throws MondeException {
        MondeIG monde = new MondeIG();
        EtapeIG entree = new ActiviteIG("entrée", 5, 5);
        EtapeIG milieu = new ActiviteIG("milieu", 5, 5);
        EtapeIG guichet = new GuichetIG("guichet", 5, 5);
        EtapeIG sortie = new ActiviteIG("sortie", 5, 5);

        entree.setSuccesseurs(milieu);
        milieu.setPredecesseurs(entree);
        milieu.setSuccesseurs(guichet);
        guichet.setPredecesseurs(milieu);
        guichet.setSuccesseurs(sortie);
        sortie.setPredecesseurs(guichet);

        entree.setEntree(true);
        sortie.setSortie(true);

        monde.ajouterEtape(entree);
        monde.ajouterEtape(milieu);
        monde.ajouterEtape(guichet);
        monde.ajouterEtape(sortie);

        Simulation sim = new Simulation();
        SimulationIG simulationIG = new SimulationIG(sim, monde);

        simulationIG.simuler();
    }
}
