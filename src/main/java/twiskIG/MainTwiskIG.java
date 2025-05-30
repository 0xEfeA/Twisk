package twiskIG;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.simulation.Simulation;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;
import twiskIG.vues.VueMenu;
import twiskIG.vues.VueOutils;
import twiskIG.vues.VueMondeIG;

public class MainTwiskIG extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Twisk IG");

        //instance du modèle
        MondeIG monde = new MondeIG();
        SimulationIG simulation= new SimulationIG(new Simulation(),monde);

        // Création de la vue des outils et de la vue du monde
        BorderPane root = new BorderPane();
        VueOutils vueOutils = new VueOutils(monde);
        VueMondeIG vueMondeIG = new VueMondeIG(monde,simulation);
        VueMenu vueMenu = new VueMenu(monde);

        // vue du monde au centre et la vue des outils en bas
        root.setTop(vueMenu);
        root.setCenter(vueMondeIG);
        root.setBottom(vueOutils);

        Scene scene = new Scene(root, 650, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
