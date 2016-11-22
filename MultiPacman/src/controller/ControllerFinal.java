package controller;

import java.io.IOException;
import javax.swing.JOptionPane;
import models.entity.Global;
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
        String[] options = {Global.START_AS_A_SERVER_COMMAND, Global.START_AS_A_CLIENT_COMMAND};
        String option = (String) JOptionPane.showInputDialog(null, Global.PLEASE_SELECT_AN_OPTION_MESSAGE, Global.TITLE_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (option) {
            case Global.START_AS_A_SERVER_COMMAND: {
                try {
                    System.out.println("rth¿¿ooioasd");
                    new ServerController();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            break;
            case Global.START_AS_A_CLIENT_COMMAND: {
                try {
                    Client c = new Client();
                    c.sendMessageObject(Global.ACTION_REGISTER);
                    c.sendMessageObject(c.getIp());
                    c.sendMessageObject(JOptionPane.showInputDialog(Global.INGRESE_SU_NOMBRE_MESSAGE));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, Global.CAPACITY_MAX_EXCEEDED_MESSAGE);
                }
            }
            break;
        }
    }
   
    public static void main(String[] args) {
        new ControllerFinal();
    }
}