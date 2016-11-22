package models.entity;

import java.awt.Point;

/**
 *
 * @author USUARIO
 */
public class Cookie {

    private Point position;
    private static final int RADIUS = 10;

    public Cookie() {
        this.position = new Point(Global.randomCoordenateX(), Global.randomCoordenateY());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
