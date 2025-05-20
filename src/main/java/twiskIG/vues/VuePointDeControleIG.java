package twiskIG.vues;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twiskIG.mondeIG.MondeIG;
import twiskIG.mondeIG.PointDeControleIG;

public class VuePointDeControleIG extends Circle {
    private final PointDeControleIG point;

    public VuePointDeControleIG(PointDeControleIG point, MondeIG monde) {
        this.point = point;
        this.setRadius(5);
        this.setFill(Color.BLUE);
        this.setStroke(Color.BLACK);

        this.setCenterX(point.getX());
        this.setCenterY(point.getY());

        // Ajouter un écouteur sur le clic
        this.setOnMouseClicked(event -> monde.selectionnerPoint(point));
    }
}
