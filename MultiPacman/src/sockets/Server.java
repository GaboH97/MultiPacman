package sockets;

import controller.ServerController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Global;

/**
 *
 * @author Cesar Cardozo
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    private ServerController servercController;
    private ArrayList<Connection> connections;

    public Server(ServerController servercController) {
        try {
            serverSocket = new ServerSocket(Global.DEFAULT_PORT);
            this.servercController = servercController;
            connections = new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        start();
    }

    public void evaluateConnections() {
        ArrayList<Connection> list = new ArrayList<>();
        for (int i = 0; i < connections.size(); i++) {
            if(connections.get(i).isActive()){
                list.add(connections.get(i));
            }
        }
        this.connections = list;
        servercController.evaluateListUser(connections);
        servercController.getServerWindow().validateList(servercController.getListUser());
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    @Override
    public void run() {
        messageInit();
        while (true) {
            evaluateConnections();
            System.out.println("este es el tamaño de la lista de connecction despues de evaluar" + connections.size());
            if (connections.size() <= Global.CAPACITY_MAX) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    connections.add(new Connection(socket, servercController));
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void messageInit() {
        System.out.println("SERVIDOR LISTO");
        System.out.println("Servicio de hacer sumitas");
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("IP SERVIDOR " + address.getHostAddress());
        } catch (UnknownHostException ex) {
        }
    }
    
        

}
