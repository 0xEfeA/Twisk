package twiskIG;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.simulation.Simulation;
import twiskIG.exceptions.MondeException;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;
import twiskIG.vues.VueMenu;
import twiskIG.vues.VueOutils;
import twiskIG.vues.VueMondeIG;

public class MainTwiskIG extends Application {
    private SimulationIG simulation;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Twisk IG");

        MondeIG monde = new MondeIG();
        simulation = new SimulationIG(new Simulation(), monde);
        BorderPane root = new BorderPane();
        VueOutils vueOutils = new VueOutils(monde,simulation);
        VueMondeIG vueMondeIG = new VueMondeIG(monde, simulation);
        VueMenu vueMenu = new VueMenu(monde);

        root.setTop(vueMenu);
        root.setCenter(vueMondeIG);
        root.setBottom(vueOutils);

        Scene scene = new Scene(root, 650, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public SimulationIG getSimulation() {
        return simulation;
    }
}

