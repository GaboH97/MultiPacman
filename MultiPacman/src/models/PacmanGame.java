package models;

import models.entity.Cookie;
import models.entity.Pacman;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.entity.Global;
import models.entity.User;
import viewpacman.MainWindowClient;

/**
 *
 * @author RA302
 */
public class PacmanGame {

    ArrayList<User> listClients; //LA LISTA QUE SE RECIBE DEL SERVER
    ArrayList<Pacman> listPacman;
    private Cookie cookie;
    private MainWindowClient mainWindowClient;
    private int pacmanMe;

    public PacmanGame(MainWindowClient mainWindowClient) {
        this.mainWindowClient = mainWindowClient;
        this.listClients = new ArrayList<>();
        this.listPacman = new ArrayList<>();
    }

    public void reciveUsers(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!(validatePacman(list.get(i).getId()))) {
                listPacman.add(new Pacman(list.get(i).getName(), list.get(i).getId()));
            }
        }
        testPacmans();
    }

    public void removePacman(int id) {
        for (int i = 0; i < listPacman.size(); i++) {
            if (listPacman.get(i).getId() == id) {
                System.out.println(listPacman.get(i) + "--ELIMINADO--");
                listPacman.remove(i);
            }
        }
    }
    
      public void generateOnePacman(int id, ArrayList<User> list) {
        this.pacmanMe = id;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                if (!(validatePacman(id))) {
                    listPacman.add(new Pacman(list.get(i).getName(), id));
                    listPacman.get(i).setDirection(1);
                    moved(listPacman.get(i));
//                    System.out.println("anguloooooooooooooooo "+listPacman.get(i).getAngulo());
                }
            }
        }
        testPacmans();
    }

    public void testPacmans() {
//        cookie = new Cookie(new Point(randomCoordenateX(), randomCoordenateY()));
//        moved();
        mainWindowClient.getPanel().pload(listPacman);
    }

    public boolean validatePacman(int id) {
        for (int i = 0; i < listPacman.size(); i++) {
            if (listPacman.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

   

    public void updateListPacmans(ArrayList<User> list) {
        this.listClients = list;
    }

    public void recivePacmans(ArrayList<Pacman> list) {
        this.listPacman = list;
    }

    public void moved(Pacman p) {
//        for (int i = 0; i < listPacman.size(); i++) {
        threadMove(p);
//        }
    }

    public void move(Pacman pacman) {
        if (!mainWindowClient.getPanelsito().colission(pacman, mainWindowClient.getPanelsito().getCookie())) {
            if (pacman.getDirection() == 1) {
                pacman.setCoordinate(new Point(pacman.getCoordinate().x + 5, pacman.getCoordinate().y));
            }
            if (pacman.getDirection() == 2) {
                pacman.setCoordinate(new Point(pacman.getCoordinate().x - 5, pacman.getCoordinate().y));
            }
            if (pacman.getDirection() == 3) {
                pacman.setCoordinate(new Point(pacman.getCoordinate().x, pacman.getCoordinate().y + 5));
            }
            if (pacman.getDirection() == 4) {
                pacman.setCoordinate(new Point(pacman.getCoordinate().x, pacman.getCoordinate().y - 5));
            }
        } else {
            mainWindowClient.getPanelsito().getCookie().setPosition(new Point(Global.randomCoordenateX(), Global.randomCoordenateY()));
            pacman.setScore(pacman.getScore() + 1);
            mainWindowClient.getPanel().deleteRows();
            mainWindowClient.getPanel().pload(listPacman);
        }
    }

    public void threadMove(Pacman pacman) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
//                        System.out.println("hola");
                        move(pacman);
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PacmanGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        t.start();

    }

    public Pacman searchPacman(int id) {
        Pacman aux = null;
        for (int i = 0; i < listPacman.size(); i++) {
            if (listPacman.get(i).getId() == id) {
                aux = listPacman.get(i);
            }
        }
        return aux;
    }

    public int searchPacmanI(int id) {
        int aux = 0;
        for (int i = 0; i < listPacman.size(); i++) {
            if (listPacman.get(i).getId() == id) {
                aux = i;
            }
        }
        return aux;
    }

    public ArrayList<Pacman> getListPacman() {
        return listPacman;
    }

    public ArrayList<User> getListClients() {
        return listClients;
    }

    public void setListPacman(ArrayList<Pacman> listPacman) {
        if (this.listPacman.isEmpty()) {
            this.listPacman = listPacman;
        } else {
            for (int i = 0; i < listPacman.size(); i++) {
                if (!(validatePacman(listPacman.get(i).getId()))) {
                    this.listPacman.add(listPacman.get(i));

                }
            }
        }
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public int getPacmanMe() {
        return pacmanMe;
    }

}
