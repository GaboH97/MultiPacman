package sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import models.Global;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina
 * Melo
 */
public class Client extends Thread {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String ip;
    private boolean active;

    public Client() throws IOException {
        this.ip = JOptionPane.showInputDialog("input your ip address");
        socket = new Socket(ip, Global.DEFAULT_PORT);
        active = true;
        config();
        this.start();
    }

    public Client(String ip) throws IOException {
        this.ip = ip;
        socket = new Socket(ip, Global.DEFAULT_PORT);
        config();
        this.start();
    }

    public void config() throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessageObject(Object mensage) throws IOException {
        output.writeObject(mensage);
    }

    public void sendMensageObjectUnique(Object mensage) throws IOException {
        output.writeUnshared(mensage);
    }

    public Object getMensagge() throws ClassNotFoundException, IOException {
        return input.readObject();
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
        while (active) {
            try {
                String op = (String) getMensagge();
                if (op.equals("ya se salio")) {
                    active = false;
                    break;
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
