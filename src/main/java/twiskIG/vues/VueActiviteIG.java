package twiskIG.vues;

import javafx.scene.layout.HBox;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG {
    private HBox center;

    // Constructeur
    public VueActiviteIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);

        // Zone pour contenir les clients
        center = new HBox();
        center.setStyle("-fx-border-color: #0059FF; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px; -fx-background-color: #E0E0E0;");
        // Obtenir la taille à partir de TailleComposants (singleton)
        TailleComposants taille = TailleComposants.getInstance();
        double largeurActivite = taille.getLargeurActivite();
        double hauteurActivite = taille.getHauteurActivite();

        // Application des tailles récupérées
        center.setPrefWidth(largeurActivite);
        center.setPrefHeight(hauteurActivite);
        this.getChildren().add(center);
    }

    /**
     * getter du hbox
     * @return
     */
    public HBox getCenter() {
        return center;
    }

}
