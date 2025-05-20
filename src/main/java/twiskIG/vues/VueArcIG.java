package twiskIG.vues;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twiskIG.mondeIG.ArcIG;

public class VueArcIG extends Pane {
    private final ArcIG arc;
    private final Line ligne;
    private final Polyline fleche;

    public VueArcIG(ArcIG arc) {
        this.arc = arc;
        this.ligne = new Line();
        this.fleche = new Polyline();
        dessinerArc();
    }

    private void dessinerArc() {
        double x1 = arc.getDepart().getX();
        double y1 = arc.getDepart().getY();
        double x2 = arc.getArrivee().getX();
        double y2 = arc.getArrivee().getY();

        // Set up the line
        ligne.setStartX(x1);
        ligne.setStartY(y1);
        ligne.setEndX(x2);
        ligne.setEndY(y2);
        ligne.setStroke(Color.BLACK);
        ligne.setStrokeWidth(2);

        // Create the arrow
        double arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        double xA = x2 - arrowSize * Math.cos(angle - Math.PI / 6);
        double yA = y2 - arrowSize * Math.sin(angle - Math.PI / 6);
        double xB = x2 - arrowSize * Math.cos(angle + Math.PI / 6);
        double yB = y2 - arrowSize * Math.sin(angle + Math.PI / 6);

        fleche.getPoints().setAll(x2, y2, xA, yA, xB, yB, x2, y2);
        fleche.setStroke(Color.BLACK);
        fleche.setFill(Color.BLACK);

        this.getChildren().addAll(ligne, fleche);
    }

    public void setColor(Color color) {
        ligne.setStroke(color);
    }

    public void setArrowColor(Color color) {
        fleche.setStroke(color);
        fleche.setFill(color);
    }

    public ArcIG getArc() {
        return arc;
    }

    public Line getLigne() {
        return ligne;
    }

    public Polyline getFleche() {
        return fleche;
    }
}
