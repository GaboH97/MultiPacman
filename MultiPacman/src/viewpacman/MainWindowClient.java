/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpacman;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import controller.ControllerClient;
import java.awt.Dimension;

/**
 *
 * @author USUARIO
 */
public class MainWindowClient extends JFrame {

    public PanelBoard panelBoard;
    public PanelTable panelTable;
    private ControllerClient controllerClient;

    public MainWindowClient(ControllerClient controllerClient) {
//        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.controllerClient = controllerClient;
        this.setLayout(new BorderLayout());
        setSize(600, 600);

        panelBoard = new PanelBoard(controllerClient);
        panelBoard.setFocusable(true);
        panelBoard.requestFocusInWindow();
        panelTable = new PanelTable(this, controllerClient);
//        panel.setBackground(Color.BLACK);
        this.add(panelBoard, BorderLayout.CENTER);
        this.add(panelTable, BorderLayout.EAST);
        this.addKeyListener(controllerClient);
        threadRepaint(this);
//        createTable();
    }

    public PanelBoard getPanelBoard() {
        return panelBoard;
    }

    public void threadRepaint(MainWindowClient frameGame) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    System.out.println("hola");
                    frameGame.repaint();
                }
            }
        });
        t.start();
    }

    public PanelTable getPanelTable() {
        return panelTable;
    }

    public void setPanelTable0(PanelTable panelTable) {
        this.panelTable = this.panelTable;
    }

}
