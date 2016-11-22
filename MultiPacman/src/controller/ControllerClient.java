package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import models.PacmanGame;
import models.entity.Pacman;
import models.entity.User;
import viewpacman.MainWindowClient;

/**
 *
 * @author USUARIO
 */
public class ControllerClient implements KeyListener {

    private PacmanGame pacmanGame;
    private MainWindowClient mainWindowClient;

    public ControllerClient() {
        mainWindowClient = new MainWindowClient(this);
        pacmanGame = new PacmanGame(mainWindowClient);
    }

    public void drawAndRefresh(ArrayList<User> list) {
        pacmanGame.reciveUsers(list);
        mainWindowClient.getPanelBoard().setListPacmans(pacmanGame.getListPacman());
        mainWindowClient.repaint();
        mainWindowClient.setVisible(true);
    }

    public void refresh() {
        mainWindowClient.getPanelBoard().setListPacmans(pacmanGame.getListPacman());
        mainWindowClient.repaint();
        mainWindowClient.setVisible(true);
    }

    public ArrayList<Pacman> generatePacmans(ArrayList<User> list) {
        pacmanGame.reciveUsers(list);
        return pacmanGame.getListPacman();
    }

    public PacmanGame getAdmin() {
        return pacmanGame;
    }

    public void recivePacmans(ArrayList<Pacman> list) {
        pacmanGame.setListPacman(list);
    }

    public ArrayList<User> getUsers() {
        return pacmanGame.getListClients();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        int aux = pacmanGame.searchPacmanI(pacmanGame.getPacmanMe());
        switch (tecla) {
            case KeyEvent.VK_UP:
                pacmanGame.getListPacman().get(aux).setDirection(4);
                break;
            case KeyEvent.VK_DOWN:
                pacmanGame.getListPacman().get(aux).setDirection(3);
                break;
            case KeyEvent.VK_LEFT:
                pacmanGame.getListPacman().get(aux).setDirection(2);
                break;
            case KeyEvent.VK_RIGHT:
                pacmanGame.getListPacman().get(aux).setDirection(1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
