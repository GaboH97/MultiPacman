package controller;

import view.ServerWindow;
import java.io.IOException;
import java.util.ArrayList;
import models.entity.Pacman;
import models.entity.Global;
import sockets.Server;
import models.entity.User;
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
    private int idRemove;

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

    public void sendObtain() throws IOException {
        for (int i = 0; i < server.getConnections().size(); i++) {
            server.getConnections().get(i).sendMessage(Global.OBTAIN_COMMAND);
        }
    }

    public void sendClient() throws IOException {
        for (int i = 0; i < server.getConnections().size(); i++) {
            server.getConnections().get(i).sendMessage(Global.SEND_MESSAGE_LIST_COMMAND);
            server.getConnections().get(i).sendMenssage(listUser);
            server.getConnections().get(i).sendMessage(server.getConnections().get(i).getIdOfConnection());
        }
    }

    public void sendRemove() throws IOException {
        for (int i = 0; i < server.getConnections().size(); i++) {
            server.getConnections().get(i).sendMessage(Global.REMOVE_COMMAND);
            server.getConnections().get(i).sendMenssage(idRemove);
        }
    }

    public void sendClientPacmans(ArrayList<Pacman> list) throws IOException {
        for (int i = 0; i < server.getConnections().size(); i++) {
            server.getConnections().get(i).sendMessage(Global.LISTPACMAN_COMMAND);
            server.getConnections().get(i).sendMenssage(list);
        }
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

    public void setIdRemove(int idRemove) {
        this.idRemove = idRemove;
        System.out.println(idRemove + "ID RE");
    }

    public static void main(String[] args) {
        try {
            new ServerController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}