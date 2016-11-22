package sockets;

import models.entity.User;
import controller.ServerController;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.entity.Pacman;
import models.entity.Global;

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
    private boolean isActive;

    public Connection(Socket socket, ServerController serverController) {
        id = BASIC_ID++;
        this.socket = socket;
        this.serverController = serverController;
        configChannels(socket);
        isActive = true;
        start();
    }

    private void configChannels(Socket socket1) {
        try {
            tx = new ObjectOutputStream(socket1.getOutputStream());
            tx.flush();
            rx = new ObjectInputStream(socket1.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
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

    public void sendMenssage(Object output) {
        try {
            tx.writeUnshared(output);
        } catch (IOException ex) {
            System.out.println("LA ENVIADA DE LA LISTA ME EXPLOTAx1");
        }
    }

    @Override
    public String toString() {
        return "Connection{" + "socket ip=" + (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "") + '}';
    }

    @Override
    public void run() {
        super.run();
        String action = "";
        while (isActive) {
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
                        System.err.println(serverController.getListUser().toString());
                        serverController.sendObtain();
                        System.out.println("-----------------------------------------------------------------------------------------------------");
                        System.out.println("");
                        break;

                    case "MEFUI":
                        System.out.println("EL CLIENTE PERDIO LA CONEXION");
                        break;

                    case "PACMANBEFORE":
                        System.out.println("AQUI LLEGA PACMANBEFORE");
                        ArrayList<Pacman> list = (ArrayList<Pacman>) recieveObject();
                        serverController.sendClientPacmans(list);
                        System.err.println(list.toString());
                        break;
                    case "PACMANAFTER":
                        System.out.println("AQUI LLEGA PACMANAFTER");
                        serverController.sendClient();
                        break;
                    case "PACMAN":
                        System.out.println("AQUI LLEGA PACMAN");
                        ArrayList<Pacman> listAux = (ArrayList<Pacman>) recieveObject();
                        serverController.sendClientPacmans(listAux);
                        System.err.println(listAux.toString());
                        break;
                }
            } catch (IOException ex) {
                try {
                    serverController.setIdRemove(id);
                    System.out.println("CLIENTE DESCONECTADO");
                    serverController.getServer().evaluateConnections();
                    serverController.evaluateListUser(serverController.getServer().getConnections());
                    serverController.getServerWindow().validateList(serverController.getListUser());
                    serverController.sendRemove();
                    //serverController.sendClient();
                    System.err.println(serverController.getListUser().toString());
                    isActive = false;
                } catch (IOException ex1) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public int getIdOfConnection() {
        return id;
    }
}
