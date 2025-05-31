package twiskIG.vues;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.outils.TailleComposants;

public class VueGuichetIG extends VueEtapeIG {

    public VueGuichetIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);

        center = new HBox();
        center.setSpacing(3);
        center.setPadding(new Insets(5));
        center.setStyle("-fx-background-color: #EDEDED; -fx-border-color: #009688; -fx-border-width: 2px;");

        TailleComposants taille = TailleComposants.getInstance();
        center.setPrefSize(taille.getLargeurGuichet(), taille.getHauteurGuichet());

        // Add slot visuals
        for (int i = 0; i < 10; i++) {
            HBox slot = new HBox();
            slot.setPrefSize(30, 40);
            slot.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white;");
            center.getChildren().add(slot);
        }

        this.getChildren().add(center);
    }


}
