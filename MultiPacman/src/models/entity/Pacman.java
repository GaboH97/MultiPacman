package models.entity;

import java.awt.Point;
import java.io.Serializable;
import models.AbstracPacman;

/**
 *
 * @author RA302
 */
public class Pacman extends AbstracPacman implements Serializable {

    public String name;
    private int id;
    public int score;

    public Pacman(String name) {
        this.name = name;
        generateCoordinates();
    }

    public Pacman(String name, int id) {
        this.name = name;
        this.id = id;
        this.setDirection(1);
        generateCoordinates();
    }

    public void generateCoordinates() {
        if (getCoordinate() == null) {
            setCoordinate(new Point(Global.randomCoordenateX(), Global.randomCoordenateY()));
        }
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Pacman{" + "name=" + name + ", id=" + id + ", score=" + score + '}';
    }

    public Object[] returnDataValues(){
        return new Object[]{name,score};
    }
}
