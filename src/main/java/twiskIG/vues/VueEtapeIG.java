package twiskIG.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import twiskIG.mondeIG.ActiviteIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.image.WritableImage;


public abstract class VueEtapeIG extends VBox {
    protected Label label;
    protected EtapeIG step;

    // constructeur
    public VueEtapeIG(MondeIG monde, EtapeIG etape) {
        this.step = etape;

        if (etape.isEstActivite()){
            this.label = new Label(etape.getNom() + ":    " + ((ActiviteIG)etape).getDelai() + " +/- " + ((ActiviteIG)etape).getEcart());
        }

        this.getChildren().add(label);
        label.setStyle("-fx-alignment: center; -fx-font-size: 9;-fx-font-weight: bold;");

        // Activation du drag'n drop
        this.setOnDragDetected(event -> {
            Dragboard dragboard = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            content.putString(etape.getIdentifiant());

            // Capture d’image pour l’animation du drag
            WritableImage image = this.snapshot(null, null);
            dragboard.setDragView(image);

            dragboard.setContent(content);

            //System.out.println("Drag détecté pour : " + etape.getIdentifiant());
            event.consume();
        });
        
    }

}
