package sockets;

import models.User;
import controller.ServerController;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Global;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina
 * Melo
 */
public class Connection extends Thread {

    private int id;
    private static int BASIC_ID = 0;
    private Socket socket;
    private ObjectOutputStream tx;
    private ObjectInputStream rx;
    private ServerController serverController;
    private boolean active;

    public Connection(Socket socket, ServerController serverController) {
        this.active = true;
        id = BASIC_ID++;
        this.socket = socket;
        this.serverController = serverController;
        configChannels(socket);
        start();
    }

    private void configChannels(Socket socket1) {
        try {
            tx = new ObjectOutputStream(socket1.getOutputStream());
            tx.flush();
            rx = new ObjectInputStream(socket1.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object recieveObject() throws IOException, ClassNotFoundException {
        return rx.readObject();
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendMessage(Object output) throws IOException {
        tx.writeObject(output);
    }

    @Override
    public String toString() {
        return "Connection{" + "socket ip=" + (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "") + '}';
    }

    @Override
    public void run() {
        super.run();
        String action = "";
        while (active) {
            try {
                action = (String) recieveObject();
                switch (action) {
                    case Global.ACTION_REGISTER:
                        String ip = (String) recieveObject();
                        String nameClient = (String) recieveObject();
                        User user = new User(this.id, ip, nameClient);
                        serverController.getListUser().add(user);

                        serverController.getServerWindow().setColor(Color.GREEN);
                        serverController.getServerWindow().addToList("CLIENTE CONECTADO: ip(" + ip + ") [" + nameClient + "]");
                        break;
                }
            } catch (IOException ex) {
                System.out.println("CLIENTE DESCONECTADO");
                this.active = false;
                serverController.getServer().evaluateConnections();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        try {
            rx.close();
            tx.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getIdOfConnection() {
        return id;
    }
}
