package twiskIG.vues;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import twiskIG.mondeIG.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;


import java.util.ArrayList;

public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;

    public VueMondeIG(MondeIG monde) {
        this.monde = monde;
        this.monde.ajouterObservateur(this);


        // Accepter le drag (drag over)
        this.setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Gérer le drop
        this.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasString()) {
                String id = dragboard.getString();
                double x = event.getX();
                double y = event.getY();

                //System.out.println("Drop effectué pour l'ID : " + id + " aux coordonnées X=" + x + ", Y=" + y);

                // Déplacer l’activité dans le modèle
                monde.deplacerEtape(id, x, y);

                monde.notifierObservateurs();
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });
        this.reagir();

    }

    @Override
    public void reagir() {
        this.getChildren().clear();

        // Afficher les arcs
        for (ArcIG arc : monde.getArcs()) {
            VueArcIG new_arc = new VueArcIG(arc);
            new_arc.setOnMouseClicked((MouseEvent event) -> monde.gererClicArc(arc));

            if (monde.getArcsSelectionnes().contains(arc)) {
                new_arc.setColor(Color.RED);
                new_arc.setArrowColor(Color.RED);
            } else {
                new_arc.setColor(Color.BLACK);
                new_arc.setArrowColor(Color.BLACK);
            }
            this.getChildren().add(new_arc);
        }

        // Dessiner chaque activité
        for (EtapeIG etape : monde.getEtapes()) {

            // Detect if this is a guichet or an activité
            VueEtapeIG vueEtape;
            HBox center;

            if (etape.estUnGuichet()) {
                vueEtape = new VueGuichetIG(monde, etape);
            } else {
                vueEtape = new VueActiviteIG(monde, etape);
            }

            center = vueEtape.getCenter();

            double hBoxWidth = center.getPrefWidth();
            double hBoxHeight = center.getPrefHeight();

            // Calculate dimensions
            double newWidth = etape.getLargeur();
            double newHeight = etape.getHauteur();

            if (hBoxWidth > 130) {
                newWidth += (hBoxWidth - 130) + 2;
            }
            if (hBoxHeight > 35) {
                newHeight += (hBoxHeight - 35) + 5;
            }

            // Create rectangle
            Rectangle rect = new Rectangle(etape.getX(), etape.getY(), newWidth, newHeight);

            // Icons
            Image enter = new Image(getClass().getResourceAsStream("/images/enter.png"), 15, 15, true, true);
            ImageView enterIcon = new ImageView(enter);

            Image sort = new Image(getClass().getResourceAsStream("/images/exit.png"), 15, 15, true, true);
            ImageView sortieIcon = new ImageView(sort);

            StackPane iconBox = new StackPane();
            iconBox.setPrefSize(15, 15);
            iconBox.setStyle("-fx-background-color: white;");
            if (etape.estEntree() && (etape.estUneActivite() || etape.estUnGuichet())) {
                iconBox.getChildren().add(enterIcon);
            } else if (etape.estSortie() && etape.estUneActivite() ) {
                iconBox.getChildren().add(sortieIcon);
            }

            iconBox.setLayoutX(etape.getX() + newWidth - 20);
            iconBox.setLayoutY(etape.getY() + 2);

            DropShadow shadow = new DropShadow();
            shadow.setColor(javafx.scene.paint.Color.LIGHTBLUE);
            shadow.setRadius(10);
            shadow.setOffsetX(0);
            shadow.setOffsetY(0);

            rect.setEffect(shadow);
            iconBox.setEffect(shadow);

            if (monde.getEtapesSelectionnees().contains(etape)) {
                rect.setStyle("-fx-fill: white; -fx-stroke: red; -fx-stroke-width: 3;");
            } else {
                rect.setStyle("-fx-fill: white; -fx-stroke: lightblue; -fx-stroke-width: 2;");
            }

            rect.setArcWidth(10);
            rect.setArcHeight(10);
            rect.setOnMouseClicked((MouseEvent event) -> monde.gererClicEtape(etape));
            this.getChildren().addAll(rect, iconBox);

            // Centering the VueEtapeIG
            double centerX = etape.getX() + (newWidth - hBoxWidth) / 2;
            double centerY = etape.getY() + (newHeight - hBoxHeight) / 3;
            vueEtape.relocate(centerX, centerY);
            this.getChildren().add(vueEtape);

            // Points de contrôle
            ArrayList<PointDeControleIG> newpoints = new ArrayList<>();
            newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth / 2), etape.getY(), etape)); // up
            newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth / 2), (int) (etape.getY() + newHeight), etape)); // down
            newpoints.add(new PointDeControleIG(etape.getX(), (int) (etape.getY() + newHeight / 2), etape)); // left
            newpoints.add(new PointDeControleIG((int)(etape.getX() + newWidth), (int) (etape.getY() + newHeight / 2), etape)); // right
            etape.setPointsDeControle(newpoints);

            for (PointDeControleIG pdc : etape.getPointsDeControle()) {
                this.getChildren().add(new VuePointDeControleIG(pdc, monde));
            }
        }

    }




}
