package twiskIG.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import twiskIG.mondeIG.MondeIG;
import twiskIG.simulationig.SimulationIG;

public class VueOutils extends BorderPane implements Observateur {
    private MondeIG monde;
    private Button addActivity;
    private Button addGuichet;
    private Button simulate;
    private SimulationIG simulation;

    public VueOutils(MondeIG monde, SimulationIG simulation) {
        this.monde = monde;
        this.simulation = simulation;
        this.monde.ajouterObservateur(this);
        simulate = new Button();
        addActivity = new Button();
        addGuichet = new Button();

        Image add_act = new Image(getClass().getResourceAsStream("/images/add_act.png"), 40, 40, true, true);
        addActivity.setGraphic(new ImageView(add_act));

        Image add_gui = new Image(getClass().getResourceAsStream("/images/add_guichet.png"), 40, 40, true, true);
        addGuichet.setGraphic(new ImageView(add_gui));



        // Set fixed size for both buttons
        setButtonSize(addActivity);
        setButtonSize(addGuichet);
        setButtonSize(simulate);
        // Tooltips
        Tooltip.install(addActivity, new Tooltip("Ajouter une nouvelle activité"));
        Tooltip.install(addGuichet, new Tooltip("Ajouter un nouveau guichet"));
        addActivity.setOnAction(new EcouteurBtnAddActivite(monde));
        addGuichet.setOnAction(new EcouteurBtnAddGuichet(monde));


        // Bottom bar with spacing
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10));
        bottomBar.setSpacing(10);

        bottomBar.getChildren().addAll(addActivity, simulate, addGuichet);
        this.setBottom(bottomBar);

        updateSimulationButton();
        monde.notifierObservateurs();
    }

    private void setButtonSize(Button button) {
        button.setMinSize(40, 40);
        button.setMaxSize(40, 40);
        button.setPrefSize(40, 40);
    }

    @Override
    public void reagir() {
        addActivity.setDisable(monde.numEtapes() >= 10);
        addGuichet.setDisable(monde.numEtapes() >= 10);
        updateSimulationButton();
    }

    private void updateSimulationButton() {
        Image simu = new Image(getClass().getResourceAsStream("/images/simuler.png"), 40, 40, true, true);
        Image simu2 = new Image(getClass().getResourceAsStream("/images/simulerStop.png"), 40, 40, true, true);

        simulate.setOnAction(null); // Remove any existing handler

        if (this.simulation.isEncoursSimulation()) {
            simulate.setGraphic(new ImageView(simu2));
            simulate.setOnAction(new EcouteurArretSimulation(monde, simulation));
            Tooltip.install(simulate, new Tooltip("Arrêter la simulation"));
            monde.notifierObservateurs();
        } else {
            simulate.setGraphic(new ImageView(simu));
            simulate.setOnAction(new EcouteurSimuler(monde, simulation));
            Tooltip.install(simulate, new Tooltip("Lancer la simulation"));
            monde.notifierObservateurs();
        }
    }

}






