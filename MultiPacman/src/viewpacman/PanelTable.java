/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewpacman;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import models.PacmanGame;
import controller.ControllerClient;
import models.entity.Pacman;

/**
 *
 * @author USUARIO
 */
public class PanelTable extends JPanel {

    private JTable table;
    private MainWindowClient fg;
    private DefaultTableModel model;
    private ControllerClient c;

    public PanelTable(MainWindowClient fg, ControllerClient c) {
        setSize(300, 700);
        this.fg = fg;
        this.c = c;
        createJtable();
        setColumnJtable();

    }

    private void createJtable() {
        table = new JTable();
        this.model = (DefaultTableModel) table.getModel();
        TableRowSorter order = new TableRowSorter(model);
        table.setRowSorter(order);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        table.setPreferredScrollableViewportSize(new Dimension(210, (fg.getHeight() - (fg.getHeight() / 2))));
    }


    public void setColumnJtable() {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.addColumn("NOMBRE");
        dtm.addColumn("PUNTAJE");
//		dtm.addColumn("Distance");

    }

    public void pload(ArrayList<Pacman> a) {
        model.getDataVector().removeAllElements();
        for (int i = 0; i < a.size(); i++) {
            System.out.println("datos " + a.get(i).getName());
            System.out.println("datos " + a.get(i).getScore());
            model.addRow(a.get(i).returnDataValues());
        }
    }
    public void deleteRows(){
        int sizeModel = model.getRowCount(); 
	    for (int i = 0; i < sizeModel ; i ++) {
	    	model.removeRow(0);
	    }
    }
}
