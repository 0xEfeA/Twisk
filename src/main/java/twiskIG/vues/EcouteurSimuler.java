package twiskIG.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import twisk.simulation.Simulation;
import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;

public class EcouteurSimuler implements EventHandler<ActionEvent> {
    private MondeIG monde;
    private SimulationIG simulation;

    public EcouteurSimuler(MondeIG monde, SimulationIG simulation) {
        this.monde = monde;
        this.simulation = simulation;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            simulation.simuler();
        } catch (MondeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de simulation");
            alert.setHeaderText("Erreur de simulation");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

