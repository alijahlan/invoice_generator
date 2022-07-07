package org.invoice_generator.controller;

import org.invoice_generator.model.InvoiceLineModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class TableHandler implements ListSelectionListener {
    JButton cancelBtn;
    JLabel invNo;
    JTextField invDate;
    JTextField customerName;
    JLabel invTotal;
    JLabel guideLbl;

    JTable invTable;
    JScrollPane tableSP2;
    JPanel rightPanelBtn;

    JTable invItemsTable;
    InvoiceLineModel invItemsModel;

    ArrayList<String[]> readItemsFile = new ArrayList<>();

    public TableHandler(JButton cancelBtn, JLabel invNo, JTextField invDate, JTextField customerName,
                        JLabel invTotal, JLabel guideLbl, JTable invTable, JScrollPane tableSP2,
                        JPanel rightPanelBtn, JTable invItemsTable, InvoiceLineModel invItemsModel,
                        ArrayList<String[]> readItemsFile) {
        this.cancelBtn = cancelBtn;
        this.invNo = invNo;
        this.invDate = invDate;
        this.customerName = customerName;
        this.invTotal = invTotal;
        this.guideLbl = guideLbl;
        this.invTable = invTable;
        this.tableSP2 = tableSP2;
        this.rightPanelBtn = rightPanelBtn;
        this.invItemsTable = invItemsTable;
        this.invItemsModel = invItemsModel;
        this.readItemsFile = readItemsFile;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        cancelBtn.setEnabled(false);
        int[] selectedRow = invTable.getSelectedRows();
        int[] selectedColumns = invTable.getSelectedColumns();

        tableSP2.setVisible(true);
        rightPanelBtn.setVisible(true);

        if (selectedRow.length > 0) {


            invNo.setEnabled(true);
            invDate.setEnabled(true);

            customerName.setEnabled(true);

            invTotal.setEnabled(true);

            // invItemsTable.setEnabled(false);
            guideLbl.setFont(new Font("", Font.BOLD, 12));

            invNo.setText((String) invTable.getValueAt(selectedRow[0], 0));
            invDate.setText((String) invTable.getValueAt(selectedRow[0], 1));
            customerName.setText((String) invTable.getValueAt(selectedRow[0], 2));
            invTotal.setText((String) invTable.getValueAt(selectedRow[0], 3));

            ArrayList<String[]> temp = new ArrayList<>();

            for (String[] item : readItemsFile) {
                //item[]
                String[] row = item;
                try {

                    double itemTotal = Double.valueOf(row[3]) * Integer.valueOf(row[4]);
                    String[] row1 = new String[]{row[0], row[2], row[3], row[4], String.valueOf(itemTotal)};
                    if (row[1].equals(invTable.getValueAt(selectedRow[0], 0))) {
                        temp.add(row1);
                    }
                    invItemsModel.AddCSVData(temp);
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(invItemsTable, "Number format not valid: " + e2.getMessage());
                }

            }
        } else {
            invNo.setEnabled(false);
            invNo.setText("");
            invDate.setEnabled(false);
            invDate.setText("");
            customerName.setEnabled(false);
            customerName.setText("");
            invTotal.setEnabled(false);
            invTotal.setText("");


            tableSP2.setVisible(false);
            rightPanelBtn.setVisible(false);

            invTable.clearSelection();

            guideLbl.setFont(new Font("", Font.BOLD, 15));

        }

    }
}
