package org.invoice_generator.controller;

import org.invoice_generator.model.InvoiceHeaderModel;
import org.invoice_generator.model.InvoiceLineModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ActionsController {

    public static void createInvoice(DefaultTableModel invTableModel, Object[] data) {
        //invTableModel.addRow(data);
    }

    public static void saveInvoice(InvoiceHeaderModel invoiceHeaderModel, ArrayList<String[]> newRow) {

        invoiceHeaderModel.AddCSVData(newRow);
        invoiceHeaderModel.fireTableDataChanged();
    }

    public static void deleteInvoice(Component parent , InvoiceHeaderModel model, int row) {

        if (row < 0) {
            JOptionPane.showMessageDialog(parent,
                    "No row is selected! Please select one row",
                    "Select row",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            //DefaultTableModel model = (DefaultTableModel) invTable.getModel();

            if(JOptionPane.showConfirmDialog(parent,"Are you sure ???") == 0){

            model.deleteRow(row);
            model.fireTableDataChanged();

            }
        }
    }

    public static void cancelInvoice() {

    }

}
