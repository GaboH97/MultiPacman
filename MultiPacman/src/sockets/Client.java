package sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import controller.ControllerClient;
import models.entity.Pacman;
import models.entity.Global;
import models.entity.User;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina
 * Melo
 */
public class Client extends Thread {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public String[] aux;
    private ControllerClient controllerClient;

    public Client() throws IOException {
        controllerClient = new ControllerClient();
        socket = new Socket(JOptionPane.showInputDialog(Global.INPUT_SERVER_IP_COMMAND), Global.DEFAULT_PORT);
        config();
        this.start();

    }

    public Client(String ip) throws IOException {
        socket = new Socket(ip, Global.DEFAULT_PORT);
        config();
        this.start();
    }

    public void config() throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessageObject(Object mensage) {
        try {
            output.writeObject(mensage);
        } catch (IOException ex) {

        }
    }

    public void sendMensageObjectUnique(Object mensage) {
        try {
            output.writeUnshared(mensage);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getMensagge() throws ClassNotFoundException, IOException {
        return input.readObject();
    }

    public String getResponse() throws IOException {
        return input.readUTF();
    }

    public void setControl(ControllerClient control) {
        this.controllerClient = control;
    }

    public String[] splitData(String text) {
        return text.split(";");
    }

    public void showThree(int[][] threeOnline) {
        for (int i = 0; i < threeOnline.length; i++) {
            for (int j = 0; j < threeOnline[i].length; j++) {
                System.out.print(threeOnline[i][j] + " ");
            }
            System.out.println("");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String op = (String) getMensagge();
                System.out.println("This is the response " + op);
                if (op.equals("LIST")) {
                    System.out.println("LLEGO LA LISTA!");
                    ArrayList<User> list = new ArrayList<>();
                    try {
                        //System.out.println("this is the response " + getMensagge().toString());
                        list = (ArrayList<User>) getMensagge();
                        int idAux = (int) getMensagge();
                        controllerClient.getAdmin().generateOnePacman(idAux, list);
                        sendMessageObject("PACMAN");
                        sendMensageObjectUnique(controllerClient.getAdmin().getListPacman());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        System.out.println("VALI POSHA");
                    }
                } else if (op.equals("LISTPACMAN")) {
                    ArrayList<Pacman> listAux = new ArrayList<>();
                    listAux = (ArrayList<Pacman>) getMensagge();
                    controllerClient.recivePacmans(listAux);
                    controllerClient.drawAndRefresh(controllerClient.getUsers());
                } else if (op.equals("Sorry")) {
                    // JOptionPane.showMessageDialog(null, getResponse());
                    JOptionPane.showMessageDialog(null, Global.CONNECTION_FORBIDDEN_MESSAGE);
                    System.out.println("Sorry but you cant connect");
                    break;
                } else if (op.equals("OBTAIN")) {
                    sendMessageObject("PACMANBEFORE");
                    sendMensageObjectUnique(controllerClient.getAdmin().getListPacman());
                    sendMessageObject("PACMANAFTER");
                } else if (op.equals("REMOVE")) {
                    int idAux = (int) getMensagge();
                    System.out.println(idAux + "A ELIMINAR");
                    controllerClient.getAdmin().removePacman(idAux);
                    controllerClient.refresh();
                    System.err.println(controllerClient.getAdmin().getListPacman().toString());
                } else if (Objects.equals(op, "Sorry")) {
                    // JOptionPane.showMessageDialog(null, getResponse());
                    JOptionPane.showMessageDialog(null, Global.CONNECTION_FORBIDDEN_MESSAGE);
                    System.out.println("Sorry but you cant connect");
                    break;
                }
            }
            input.close();
            output.close();
        } catch (IOException ex) {
            System.out.println("Failed trying to close IO channels");
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ObjectInputStream getInput() {
        return input;
    }

    public String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
