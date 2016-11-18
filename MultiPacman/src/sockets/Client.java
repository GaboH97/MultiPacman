package sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public String[] aux;
    private String ip;
    
    public Client() throws IOException {
        this.ip = JOptionPane.showInputDialog("input your ip address");
        socket = new Socket(ip, Global.DEFAULT_PORT);
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
                System.out.println("Son iguales? " + op.equals("Sorry"));
                if (op.equals("Sorry")) {
                    // JOptionPane.showMessageDialog(null, getResponse());
                    JOptionPane.showMessageDialog(null, Global.CONNECTION_FORBIDDEN_MESSAGE);
                    System.out.println("Sorry but you cant connect");
                    break;
                }
                if (Objects.equals(op, "Sorry")) {
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
