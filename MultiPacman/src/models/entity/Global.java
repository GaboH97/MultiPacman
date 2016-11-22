package models.entity;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina
 * Melo
 */
public class Global {

    //constants---------------
    public static final int DEFAULT_PORT = 12345;
    public static final int CAPACITY_MAX = 5;
    public final static int WIDTH = 600;
    public final static int HEIGTH = 400;

    //commands ------------------
    public static final String START_AS_A_CLIENT_COMMAND = "Start as a client";
    public static final String START_AS_A_SERVER_COMMAND = "Start as a server";
    public static final String ACTION_REGISTER = "register";
    public static final String DESCONECT = "desconect";
    public static final String MAX = "max";
    public static final String OBTAIN_COMMAND = "OBTAIN";
    public static final String SEND_MESSAGE_LIST_COMMAND = "LIST";
    public static final String LISTPACMAN_COMMAND = "LISTPACMAN";
    public static final String REMOVE_COMMAND = "REMOVE";

    //message-------------------------------------------------------
    public static final String CAPACITY_MAX_EXCEEDED_MESSAGE = "You aren't available to connect because the server has a max capacity exceeded";
    public static final String INGRESE_SU_NOMBRE_MESSAGE = "Ingrese su nombre";
    public static final String CONNECTION_FORBIDDEN_MESSAGE = "Sorry but the capacity of our systems was exceeded, try again later";
    public static final String TITLE_MESSAGE = "Pacmans";
    public static final String PLEASE_SELECT_AN_OPTION_MESSAGE = "Please select an option";
    public static final String INPUT_SERVER_IP_COMMAND = "Input the IP of the server you want to connect to";

    //methods--------------------------------------
    public static int randomCoordenateY() {
        return (int) (Math.random() * Global.HEIGTH);
    }

    public static int randomCoordenateX() {
        return (int) (Math.random() * Global.WIDTH);
    }

}
