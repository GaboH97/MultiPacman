package sockets;

import controller.ServerController;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Objects;
import models.Global;

/**
 *
 * @author Cesar Cardozo
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    private ServerController serverController;
    private ArrayList<Connection> connections;

    public Server(ServerController serverController) {
        try {
            serverSocket = new ServerSocket(Global.DEFAULT_PORT);
            this.serverController = serverController;
            connections = new ArrayList<>();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        start();
    }

    public void evaluateConnections() {
        for (int i = 0; i < connections.size(); i++) {
            if (!connections.get(i).isActive()) {
                connections.remove(i);
            }
        }
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public boolean isLoggedMoreTimesThanAvailable(String IP) { //NOMBRE DE MÉTODO TIPO CESAR :V
        int counter = 0;
        for (Connection connection : connections) {
            if (Objects.equals(connection.getSocket().getRemoteSocketAddress().toString().replaceAll("/", "").substring(0, IP.indexOf(":")), IP.substring(0, IP.indexOf(":")))) {
                counter++;
            }
            if (counter >= Global.MAX_CONNECTIONS_PER_IP) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        messageInit();
        while (true) {
            System.out.println("Size of connections before adding " + getConnections().size());
            Socket socket = null;
            try {
                evaluateConnections();
                socket = serverSocket.accept();

                if (connections.size() < Global.CAPACITY_MAX &&!isLoggedMoreTimesThanAvailable(socket.getRemoteSocketAddress().toString().replaceAll("/", ""))) {
                    connections.add(new Connection(socket, serverController));
                    System.out.println("Size of connections after adding " + getConnections().size());
                } else {
                    /**
                     * ENVIA MENSAJE DE QUE NO PUEDECONECTARSE , O QUE INTENTE
                     * EN UN RATO Y LUEGO CIERRA LOS CANALES DE INFORMACIÒN
                     */
//                    socket.seendMessageDeQueNoLoPodemosAceptar()
//                    socket.close();
                    ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
                    ois.writeObject("Sorry");
                    ois.writeUTF(Global.CONNECTION_FORBIDDEN_MESSAGE);

                    // serverController.evaluateListUser(serverController.getServer().getConnections());
                    //serverController.getServerWindow().validateList(serverController.getListUser());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
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
