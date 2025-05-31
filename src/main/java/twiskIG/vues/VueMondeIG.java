package twiskIG.vues;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import twiskIG.mondeIG.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import twiskIG.outils.ClientMover;
import twiskIG.simulationig.SimulationIG;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;
    private SimulationIG simulation;
    private final ArrayList<ClientMover> clientMovers = new ArrayList<>();

    public VueMondeIG(MondeIG monde, SimulationIG simulation) {
        this.monde = monde;
        this.simulation = simulation;
        this.monde.ajouterObservateur(this);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (ClientMover mover : clientMovers) {
                    mover.update();
                }
            }
        };
        animationTimer.start();

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
        Runnable command = new Runnable() {
            @Override
            public void run() {
                VueMondeIG.this.getChildren().clear();

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
                    VueMondeIG.this.getChildren().add(new_arc);
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
                    } else if (etape.estSortie() && etape.estUneActivite()) {
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
                    VueMondeIG.this.getChildren().addAll(rect, iconBox);

                    // Centering the VueEtapeIG
                    double centerX = etape.getX() + (newWidth - hBoxWidth) / 2;
                    double centerY = etape.getY() + (newHeight - hBoxHeight) / 3;
                    vueEtape.relocate(centerX, centerY);
                    VueMondeIG.this.getChildren().add(vueEtape);

                    // Points de contrôle
                    ArrayList<PointDeControleIG> newpoints = new ArrayList<>();
                    newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth / 2), etape.getY(), etape)); // up
                    newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth / 2), (int) (etape.getY() + newHeight), etape)); // down
                    newpoints.add(new PointDeControleIG(etape.getX(), (int) (etape.getY() + newHeight / 2), etape)); // left
                    newpoints.add(new PointDeControleIG((int) (etape.getX() + newWidth), (int) (etape.getY() + newHeight / 2), etape)); // right
                    etape.setPointsDeControle(newpoints);

                    for (PointDeControleIG pdc : etape.getPointsDeControle()) {
                        VueMondeIG.this.getChildren().add(new VuePointDeControleIG(pdc, monde));
                    }

                    // Affichage des clients
                    HashMap<String, Integer> etapeClients = simulation.getSim().getNbClientsParEtape();
                    int nbClients = etapeClients.getOrDefault(etape.getNom(), 0);


                    if(etape.estUneActivite()){
                        // Find the center coordinates of the HBox inside the vueEtape
                        double hBoxX = etape.getX() + (newWidth - hBoxWidth) / 2;
                        double hBoxY = etape.getY() + (newHeight - hBoxHeight) / 3;
                        double spacing = 20.0;  // Espace horizontal entre les clients

                        Random random = new Random();
                        double radius = 5.0;
                        double topPadding = 10.0;
                        double bottomPadding = 10.0;

                        for (int i = 0; i < nbClients; i++) {
                            double clientX = hBoxX + radius + (i * spacing);

                            double minX = hBoxX + radius;
                            double maxX = hBoxX + hBoxWidth - radius;
                            double minY = hBoxY + topPadding + radius;
                            double maxY = hBoxY + hBoxHeight + bottomPadding - radius;

                            // Start at vertical center of bouncing zone
                            double clientY = (minY + maxY) / 2.0;

                            //Color randomColor = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
                            Color randomColor = Color.GREEN;



                            Circle client = new Circle(clientX, clientY, radius);

                            client.setFill(randomColor);

                            VueMondeIG.this.getChildren().add(client);
                            client.toFront();

                            ClientMover mover = new ClientMover(client, minX, minY, maxX, maxY);
                            clientMovers.add(mover);
                        }

                    }

                    if (etape.estUnGuichet()) {
                        VueGuichetIG vueGuichet = (VueGuichetIG) vueEtape;
                        GuichetIG guichetIG = (GuichetIG) etape;

                        // First, clear all previous clients from each slot
                        for (Node slot : vueGuichet.getCenter().getChildren()) {
                            if (slot instanceof HBox) {
                                ((HBox) slot).getChildren().clear();
                            }
                        }

                        nbClients = Math.min(nbClients, 10);  // cap at 10 slots

                        // Determine the fill order based on sensDeplacement
                        int index = guichetIG.getSensDeplacement() ? 0 : 9;
                        int step = guichetIG.getSensDeplacement() ? 1 : -1;

                        // Draw the clients
                        for (int i = 0; i < nbClients; i++) {
                            // Create the client circle
                            //Circle client = new Circle(5, Color.color(Math.random(), Math.random(), Math.random()));
                            Circle client = new Circle(5, Color.GREEN);


                            // Place the client circle in the correct slot
                            HBox slot = (HBox) vueGuichet.getCenter().getChildren().get(index);
                            slot.getChildren().add(client);
                            slot.setAlignment(Pos.CENTER);
                            // Move to the next slot based on direction
                            index += step;
                        }
                    }






                }




            }


        };

        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }


    }


}
