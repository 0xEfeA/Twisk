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

public class VueOutils extends BorderPane implements Observateur {
    private MondeIG monde;
    private Button addActivity;
    private Button addGuichet;

    public VueOutils(MondeIG monde) {
        this.monde = monde;
        this.monde.ajouterObservateur(this);

        addActivity = new Button();
        addGuichet = new Button();

        Image add_act = new Image(getClass().getResourceAsStream("/images/add_act.png"), 40, 40, true, true);
        addActivity.setGraphic(new ImageView(add_act));

        Image add_gui = new Image(getClass().getResourceAsStream("/images/add_guichet.png"), 40, 40, true, true);
        addGuichet.setGraphic(new ImageView(add_gui));

        // Set fixed size for both buttons
        setButtonSize(addActivity);
        setButtonSize(addGuichet);

        // Tooltips
        Tooltip.install(addActivity, new Tooltip("Ajouter une nouvelle activité"));
        Tooltip.install(addGuichet, new Tooltip("Ajouter un nouveau guichet"));

        addActivity.setOnAction(new EcouteurBtnAddActivite(monde));
        addGuichet.setOnAction(new EcouteurBtnAddGuichet(monde));


        // Bottom bar with spacing
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10));
        bottomBar.setSpacing(10);

        // Spacer to push buttons to corners
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bottomBar.getChildren().addAll(addActivity, spacer, addGuichet);
        this.setBottom(bottomBar);
    }

    private void setButtonSize(Button button) {
        button.setMinSize(40, 40);
        button.setMaxSize(40, 40);
        button.setPrefSize(40, 40);
    }

    @Override
    public void reagir() {
        addActivity.setDisable(monde.numEtapes() >= 10);
    }
}






