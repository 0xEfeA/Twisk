package twiskIG.vues;

import javafx.scene.layout.HBox;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG {
    public VueActiviteIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);

        center = new HBox();
        center.setStyle("-fx-border-color: #0059FF; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px; -fx-background-color: #E0E0E0;");

        TailleComposants taille = TailleComposants.getInstance();
        center.setPrefSize(taille.getLargeurActivite(), taille.getHauteurActivite());

        this.getChildren().add(center);
    }
}

