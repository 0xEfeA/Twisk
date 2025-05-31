package twiskIG.outils;
import javafx.scene.shape.Circle;

import java.util.Random;

public class ClientMover {
    public Circle circle;
    private double dx;
    private double dy;
    private final double minX, minY, maxX, maxY;

    public ClientMover(Circle circle, double minX, double minY, double maxX, double maxY) {
        this.circle = circle;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        Random rand = new Random();
        this.dx = 1 + rand.nextDouble() * 2; // speed x between 1 and 3 px/frame
        this.dy = 1 + rand.nextDouble() * 2; // speed y between 1 and 3 px/frame

        // Randomize direction
        if (rand.nextBoolean()) dx = -dx;
        if (rand.nextBoolean()) dy = -dy;
    }

    public void update() {
        double x = circle.getCenterX() + dx;
        double y = circle.getCenterY() + dy;

        // Bounce horizontally
        if (x - circle.getRadius() < minX) {
            dx = -dx;
            x = minX + circle.getRadius();
        } else if (x + circle.getRadius() > maxX) {
            dx = -dx;
            x = maxX - circle.getRadius();
        }

        // Bounce vertically
        if (y - circle.getRadius() < minY) {
            dy = -dy;
            y = minY + circle.getRadius();
        } else if (y + circle.getRadius() > maxY) {
            dy = -dy;
            y = maxY - circle.getRadius();
        }

        circle.setCenterX(x);
        circle.setCenterY(y);
    }
}
