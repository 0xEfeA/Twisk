package twiskIG.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twiskIG.mondeIG.ActiviteIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.GuichetIG;
import twiskIG.mondeIG.MondeIG;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.image.WritableImage;


public abstract class VueEtapeIG extends VBox {
    protected Label label;
    protected EtapeIG step;
    protected HBox center;

    public VueEtapeIG(MondeIG monde, EtapeIG etape) {
        this.step = etape;

        if (etape.isEstActivite()) {
            this.label = new Label(etape.getNom() + ":    " + ((ActiviteIG)etape).getDelai() + " +/- " + ((ActiviteIG)etape).getEcart());
        }

        if (etape.isEstGuichet()) {
            this.label = new Label(etape.getNom() + ":    " + ((GuichetIG)etape).getNbJetons() + " Jetons ");
        }

        this.getChildren().add(label);
        label.setStyle("-fx-alignment: center; -fx-font-size: 9; -fx-font-weight: bold;");

        this.setOnDragDetected(event -> {
            Dragboard dragboard = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(etape.getIdentifiant());
            WritableImage image = this.snapshot(null, null);
            dragboard.setDragView(image);
            dragboard.setContent(content);
            event.consume();
        });
    }

    /**
     * Getter for the center HBox used in both Activite and Guichet
     */
    public HBox getCenter() {
        return center;
    }
}

