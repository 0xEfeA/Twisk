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

    public EcouteurSimuler(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Simulation sim = new Simulation(); // Create a Simulation instance
            this.simulation = new SimulationIG(sim, monde);
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
