package org.invoice_generator.controller;

import org.invoice_generator.model.FileOperations;
import org.invoice_generator.model.InvoiceHeaderModel;
import org.invoice_generator.model.InvoiceLineModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class ActionsController {


    public static void createInvoice(DefaultTableModel invTableModel, Object[] data) {
        //invTableModel.addRow(data);
    }

    public static void saveInvoice(InvoiceHeaderModel invoiceHeaderModel, InvoiceLineModel invoiceLineModel, ArrayList<String[]> readFile,ArrayList<String[]> readItemsFile, String[] row) {
        String invTablePath = "src/main/java/dataFiles/InvoiceHeader.csv";
        String invTableItemsPath = "src/main/java/dataFiles/InvoiceLine.csv";
        int flag = -1;
        System.out.println( readFile.size());
        for ( int i=0; i < readFile.size(); i++) {
            if (readFile.get(i)[0] == row[0]){
                flag = i;
                break;
                //System.out.println("index exist");
            }
            else {
                flag = -1;
               System.out.println("index doesn't exist");
            }
        }

        if (flag >=0){
            System.out.println("Edit");
            readFile.set(flag, row);
             // if (readItemsFile.size()< )
            //readItemsFile.addAll(addInvIndexRow(invoiceLineModel,readItemsFile, Integer.parseInt(row[0])));

        }else {
            readFile.add(row);
            //ArrayList<String[]> dataModel = invoiceLineModel.Data;
            //dataModel = addInvIndexRow(invoiceLineModel, flag);
            readItemsFile.addAll(addInvIndexRow(invoiceLineModel, Integer.parseInt(row[0])));

        }

        invoiceHeaderModel.AddCSVData(readFile);
        invoiceLineModel.AddCSVData(readItemsFile);



        FileOperations.writeFiles(invTablePath,invTableItemsPath,readFile,readItemsFile);
       /* invoiceHeaderModel.fireTableDataChanged();
        invoiceLineModel.fireTableDataChanged();*/
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



    private static ArrayList<String[]> addInvIndexRow(InvoiceLineModel model, int rowIndex) {
        ArrayList<String[]> allData = new ArrayList<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            String[] singleRow = new String[model.getColumnCount()];
            for (int column = 0; column < model.getColumnCount(); column++) {
                if ((column!=model.getColumnCount()-1)){

                singleRow[column] = (String) model.getValueAt(row, column);
                    System.out.println(model.getColumnName(column));
                }

            }
            singleRow = new String[]{singleRow[0], String.valueOf(rowIndex), singleRow[1],singleRow[2],singleRow[3]};
            System.out.println("Total: " + singleRow.length);
            allData.add(row,singleRow);
            //allData.a
//            if (singleRow[0] == invNo.getText()) {
//                isExist = true;
//                indexExistItem = row;
//
//
//            }
        }
        return allData;
    }



    private static ArrayList<String[]> addInvIndexRow(InvoiceLineModel model, ArrayList<String[]> readItemsFile,  int rowIndex) {
        ArrayList<String[]> allData = new ArrayList<>();



        for (int row = 0; row < model.getRowCount(); row++) {
            String[] singleRow = new String[model.getColumnCount()];
            for (int column = 0; column < model.getColumnCount(); column++) {
                if ((column!=model.getColumnCount()-1)){

                    singleRow[column] = (String) model.getValueAt(row, column);
                    System.out.println(model.getColumnName(column));
                }

            }
            int i = 0;
            for (String[] itemRow: readItemsFile) {
                if (itemRow[0] != model.getValueAt(row,0) && itemRow[1] == String.valueOf(rowIndex)){
                    singleRow = new String[]{singleRow[0], String.valueOf(rowIndex), singleRow[1],singleRow[2],singleRow[3]};
                    System.out.println("Total: " + singleRow.length);
                    allData.add(row,singleRow);
                    //break;
                    //allData.a
//            if (singleRow[0] == invNo.getText()) {
//                isExist = true;
//                indexExistItem = row;


                } else{
                    break;
                }
                //i++;
            }


//
//
//            }
        }
        return allData;
    }

}
