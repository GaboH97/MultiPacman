package controller;

import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import models.Global;
import sockets.Client;

/**
 *
 * @author Cesar Nicolas & Gabriel Amaya
 */
public class ControllerFinal {

    public ControllerFinal() {
        menu();
    }

    public void menu() {
        String[] options = {"Start as a server", "Start as a client"};
        String option = (String) JOptionPane.showInputDialog(null, "Please select an option", "Tic-Tac-Toe", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (option) {
            case "Start as a server": {
                try {
                    new ServerController();
                } catch (IOException ex) {
                }
            }
            break;
            case "Start as a client": {
                try {
                    Client c = new Client();
                    c.sendMessageObject(Global.ACTION_REGISTER);
                    c.sendMessageObject(c.getIp());
                    c.sendMessageObject(JOptionPane.showInputDialog("Ingrese su nombre"));
                } catch (UnknownHostException ex) {
                }
            }
            break;
        }
    }

    public static void main(String[] args) {
        new ControllerFinal();
    }
}
