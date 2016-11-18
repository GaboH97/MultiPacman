package controller;

import view.ServerWindow;
import java.io.IOException;
import java.util.ArrayList;
import sockets.Server;
import models.User;
import sockets.Connection;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina
 * Melo
 */
public class ServerController {

    private ServerWindow serverWindow;
    private Server server;
    private ArrayList<User> listUser;

    public ServerController() throws IOException {
        this.listUser = new ArrayList<>();
        this.serverWindow = new ServerWindow();
        this.server = new Server(this);
    }

    public void evaluateListUser(ArrayList<Connection> list) {
        ArrayList<User> listAux = new ArrayList<>();
        for (Connection connection : list) {
            for (User user : listUser) {
                String auxIp = connection.getSocket().getLocalAddress().toString().replaceAll("/", "");
                if (auxIp.equals(user.getIp()) && connection.getIdOfConnection() == user.getId()) {
                    listAux.add(user);
                }
            }
        }
        this.listUser = listAux;
    }

    public Server getServer() {
        return server;
    }

    public ServerWindow getServerWindow() {
        return serverWindow;
    }

    public ArrayList<User> getListUser() {
        return listUser;
    }

    public static void main(String[] args) {
        try {
            new ServerController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
