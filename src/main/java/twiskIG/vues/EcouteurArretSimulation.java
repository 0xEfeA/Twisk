package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;

public class EcouteurArretSimulation implements EventHandler<ActionEvent>{
    private MondeIG monde;
    private SimulationIG simulation;

    public EcouteurArretSimulation(MondeIG monde, SimulationIG simulation) {
        this.monde = monde;
        this.simulation = simulation;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        simulation.reinitialiserSimulation();
        monde.notifierObservateurs();
    }
}


