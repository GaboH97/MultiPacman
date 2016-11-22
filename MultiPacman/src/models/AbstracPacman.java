package models;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Lina Melo
 */
public abstract class AbstracPacman implements Serializable {

    private Color color;
    private Point coordinate;
    private int direction;

    public AbstracPacman() {
        this.color = randomColor();
    }

    public Color randomColor() {
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new Color(r, g, b);
    }

    public Color getColor() {
        return color;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public int getDirection() {
        return direction;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Color: " + color + "Coordenadas: " + coordinate.toString();
    }
}
