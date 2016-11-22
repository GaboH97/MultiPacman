/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpacman;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import controller.ControllerClient;

/**
 *
 * @author USUARIO
 */
public class MainWindowClient extends JFrame {

    public PanelGame1 panelsito;
    public PanelTable panel;
    private ControllerClient c;

    public MainWindowClient(ControllerClient c) {
//        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 600);
        this.c = c;
        this.setLayout(null);
        panelsito = new PanelGame1(c);
        panelsito.setFocusable(true);
        panelsito.requestFocusInWindow();
        panel = new PanelTable(this, c);
//        panel.setBackground(Color.BLACK);
        panelsito.setBounds(0, 0, 965, 560);
        panel.setBounds(825, 0, 500, 900);
        this.add(panelsito, BorderLayout.WEST);
        this.add(panel, BorderLayout.EAST);
        this.addKeyListener(c);
        threadRepaint(this);
//        createTable();
    }

    public PanelGame1 getPanelsito() {
        return panelsito;
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

    public PanelTable getPanel() {
        return panel;
    }

    public void setPanel(PanelTable panel) {
        this.panel = panel;
    }

}
