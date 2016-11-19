package controller;

import java.io.IOException;
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
                    ex.printStackTrace();
                }
            }
            break;
            case "Start as a client": {
                try {
                    Client c = new Client();
                    c.sendMessageObject(Global.ACTION_REGISTER);
                    c.sendMessageObject(c.getIp());
                    c.sendMessageObject(JOptionPane.showInputDialog("Ingrese su nombre"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "You aren't available to connect because the server has a max capacity exceeded");
                }
            }
            break;
        }
    }
                    
    public static void main(String[] args) {
        new ControllerFinal();
    }
}
