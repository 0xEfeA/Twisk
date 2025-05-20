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

            //Access the HBox size from VueActiviteIG
            VueActiviteIG vueActivite = new VueActiviteIG(monde, etape);

            HBox center = vueActivite.getCenter();
            double hBoxWidth = center.getPrefWidth();
            double hBoxHeight = center.getPrefHeight();

            //Calculate the new width and height without modifying etape directly
            double newWidth = etape.getLargeur();
            double newHeight = etape.getHauteur();

            // Adjust dimensions based on hBox size
            if (hBoxWidth > 130) {
                newWidth = etape.getLargeur() + (hBoxWidth - 130) + 2;
            }
            if (hBoxHeight > 35) {
                newHeight = etape.getHauteur() + (hBoxHeight - 35) + 5;
            }

            // Create the rectangle for the activity with the updated size
            Rectangle rect = new Rectangle(
                    etape.getX(),
                    etape.getY(),
                    newWidth,
                    newHeight
            );

            Image enter = new Image(getClass().getResourceAsStream("/images/enter.png"), 15, 15, true, true);
            ImageView enterIcon = new ImageView(enter);

            Image sort = new Image(getClass().getResourceAsStream("/images/exit.png"), 15, 15, true, true);
            ImageView sortieIcon = new ImageView(sort);

            StackPane iconBox = new StackPane();
            iconBox.setPrefSize(15, 15);
            iconBox.setStyle("-fx-background-color: white; ");
            if (etape.estEntree() && etape.isEstActivite()) {
                iconBox.getChildren().add(enterIcon);
            } else if (etape.estSortie() && etape.isEstActivite()) {
                iconBox.getChildren().add(sortieIcon);
            }

            // Position icon at top-right of the rectangle
            iconBox.setLayoutX(etape.getX() + newWidth - 20);
            iconBox.setLayoutY(etape.getY() + 2);

            // LED Border
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
            this.getChildren().addAll(rect,iconBox);

            //the position for the HBox to be centered inside the rectangle
            double centerX = etape.getX() + (newWidth - hBoxWidth) / 2;
            double centerY = etape.getY() + (newHeight - hBoxHeight) / 3;

            // Relocation
            vueActivite.relocate(centerX, centerY);

            this.getChildren().add(vueActivite);

            // Ajouter les points de contrôle de cette étape
            ArrayList<PointDeControleIG> newpoints = new ArrayList<>();
            newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth / 2), etape.getY(), etape)); //up
            newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth / 2), (int) (etape.getY() + newHeight), etape)); //down
            newpoints.add(new PointDeControleIG(etape.getX(), (int) (etape.getY() + newHeight / 2), etape));  //left
            newpoints.add(new PointDeControleIG((int)(etape.getX() + newWidth), (int) (etape.getY() + newHeight / 2), etape)); //right
            etape.setPointsDeControle(newpoints);

            ArrayList<PointDeControleIG> points = etape.getPointsDeControle();
            for (PointDeControleIG pdc : points) {
                VuePointDeControleIG vuePdc = new VuePointDeControleIG(pdc,monde);
                this.getChildren().add(vuePdc);
            }
        }
    }




}
