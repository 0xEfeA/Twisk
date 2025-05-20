package twiskIG.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twiskIG.mondeIG.MondeIG;

import java.io.InputStream;

public class VueOutils extends TilePane implements Observateur {
    private MondeIG monde;
    private Button btnAjouter;

    public VueOutils(MondeIG monde) {
        this.monde = monde;
        this.monde.ajouterObservateur(this);

        // Bouton pour ajouter une activité
        btnAjouter = new Button();

        Image add = new Image(getClass().getResourceAsStream("/images/add_act.jpg"), 40, 40, true, true);
        ImageView icon = new ImageView(add);
        btnAjouter.setGraphic(icon);

        // Force button size to match the image
        btnAjouter.setMinSize(40, 40);
        btnAjouter.setMaxSize(40, 40);
        btnAjouter.setPrefSize(40, 40);

        // Ajouter un Tooltip au bouton
        Tooltip tooltip = new Tooltip("Ajouter une nouvelle activité");
        Tooltip.install(btnAjouter, tooltip);

        btnAjouter.setOnAction(new EcouteurBouton(monde));


        this.getChildren().add(btnAjouter);
    }

    @Override
    public void reagir() {
        if (monde.numEtapes() >= 10) {
            btnAjouter.setDisable(true);
        } else {
            btnAjouter.setDisable(false);
        }
    }
}

