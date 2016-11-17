package sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Global;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina Melo
 */
public class Client extends Thread{
    
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public String[] aux;
    private String ip;
    
    public Client() {
        try {
            this.ip = JOptionPane.showInputDialog("input your ip address");
            socket= new Socket(ip, Global.DEFAULT_PORT);
            config();
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Client(String ip) {
        try {
            this.ip = ip;
            socket= new Socket(ip, Global.DEFAULT_PORT);
            config();
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public void config(){
        try {
             output = new ObjectOutputStream(socket.getOutputStream());
             input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessageObject(Object mensage){
        try {
            output.writeObject(mensage);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMensageObjectUnique(Object mensage){
        try {
            output.writeUnshared(mensage);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object getMensagge() throws ClassNotFoundException, IOException {
       return input.readObject();
    }
    
    public String[] splitData(String text){
        return text.split(";");
    }
    
    public void showThree(int [][] threeOnline){
        for (int i = 0; i < threeOnline.length; i++) {
            for (int j = 0; j < threeOnline[i].length; j++) {
                System.out.print(threeOnline[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                String op = (String) getMensagge();
                if(op.equals("MEFUI")){
                    JOptionPane.showMessageDialog(null, (String) getMensagge());
                }
            } catch (ClassNotFoundException ex) {
            } catch (IOException ex) {
            }
        }
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public String getIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}