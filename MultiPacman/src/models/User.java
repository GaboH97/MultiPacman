package models;

/**
 *
 * @author Andres Torres & Cesar Cardozo & Gabriel Amaya & Megan Ibage & Lina
 * Melo
 */
public class User {

    private String name;
    private String ip;
    private int id;

    public User(int id, String ip, String name) {
        this.id = id;
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " - CLIENTE CONECTADO: ip(" + ip + ") [" + name + "]";
    }
}
