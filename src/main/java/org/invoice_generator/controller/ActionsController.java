package org.invoice_generator.controller;

import org.invoice_generator.model.FileOperations;
import org.invoice_generator.model.InvoiceHeaderModel;
import org.invoice_generator.model.InvoiceLineModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ActionsController {


    public static void createInvoice(DefaultTableModel invTableModel, Object[] data) {
        //invTableModel.addRow(data);
    }

    public static void saveInvoice(InvoiceHeaderModel invoiceHeaderModel, InvoiceLineModel invoiceLineModel, ArrayList<String[]> readInvFile,ArrayList<String[]> readItemsFile, String[] row, Component parent) {
        String invTablePath = "src/main/java/dataFiles/InvoiceHeader.csv";
        String invTableItemsPath = "src/main/java/dataFiles/InvoiceLine.csv";
        int flag = -1;
        for ( int i=0; i < readInvFile.size(); i++) {
            if (readInvFile.get(i)[0].equals(row[0])){
                flag = Integer.valueOf(readInvFile.get(i)[0]);
                break;

            }
            else {
                flag = -1;
            }
        }

        if (flag >0){
            //System.out.println(flag);
           // System.out.println(row[0]);
            if (flag==1){
                readInvFile.set(0, row);
            }else{

            readInvFile.set(flag-1, row);
            }
             // if (readItemsFile.size()< )
            ArrayList<String[]> readItemsFileTemp = (ArrayList<String[]>) readItemsFile.clone();
            int index=0;
            for(String[] item: readItemsFileTemp){
                //System.out.println(item[1]);
                //System.out.println(row[0]);
                //System.out.println("-------------------");
                if(item[1].equals(row[0])){
                    readItemsFile.remove(index);
                    index--;
                }
                index++;
            }
            readItemsFile.addAll(addInvIndexRow(invoiceLineModel,flag));


        }else {
            readInvFile.add(row);
            //ArrayList<String[]> dataModel = invoiceLineModel.Data;
            //dataModel = addInvIndexRow(invoiceLineModel, flag);
            //readItemsFile.addAll(addInvIndexRow(invoiceLineModel, Integer.parseInt(row[0])));
            readItemsFile.addAll(addInvIndexRow(invoiceLineModel, Integer.parseInt(row[0])));

        }

        invoiceHeaderModel.AddCSVData(readInvFile);
        invoiceLineModel.AddCSVData(readItemsFile);



        FileOperations.writeFiles(invTablePath,invTableItemsPath,readInvFile,readItemsFile, parent);
       /* invoiceHeaderModel.fireTableDataChanged();
        invoiceLineModel.fireTableDataChanged();*/
    }



    public static ArrayList<String[]> deleteInvoice(Component parent , InvoiceHeaderModel invoiceHeaderModel, ArrayList<String[]> readItemsFile , int row) {

       //String value = (String) invoiceHeaderModel.getValueAt(row, 0);
        ArrayList<String[]> arrayListTemp = new ArrayList<>();
        if (row < 0) {
            JOptionPane.showMessageDialog(parent,
                    "No row is selected! Please select one row",
                    "Select row",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            //DefaultTableModel invoiceHeaderModel = (DefaultTableModel) invTable.getModel();

            if (JOptionPane.showConfirmDialog(parent, "Are you sure ???") == 0) {



                ArrayList<String[]> readItemsFileTemp = (ArrayList<String[]>) readItemsFile.clone();
                int index=0;
//                if (invoiceHeaderModel.getRowCount()==1){
//                    row+=1;
//                }
                for(String[] item: readItemsFileTemp){
                    if (invoiceHeaderModel.getRowCount()<=1){
                        readItemsFile = new ArrayList<String[]>();
                        break;
                    }
                    if(Integer.parseInt(item[1]) == row+1){
                        readItemsFile.remove(index);

                        index--;
                        if (readItemsFile.size()<1){
                            break;
                        }

                    }
                    index++;
                }
                //readItemsFile.addAll();


                invoiceHeaderModel.deleteRow(row);
                invoiceHeaderModel.fireTableDataChanged();
                //return readItemsFile;
            }
        }


        return readItemsFile;
    }

    public static void cancelInvoice() {

    }



    private static ArrayList<String[]> addInvIndexRow(InvoiceLineModel invoiceLineModel, int rowIndex) {
        ArrayList<String[]> allData = new ArrayList<>();

        for (int row = 0; row < invoiceLineModel.getRowCount(); row++) {
            String[] singleRow = new String[invoiceLineModel.getColumnCount()];
            for (int column = 0; column < invoiceLineModel.getColumnCount(); column++) {
                if ((column!=invoiceLineModel.getColumnCount()-1)){

                singleRow[column] = (String) invoiceLineModel.getValueAt(row, column);
                }

            }
            singleRow = new String[]{singleRow[0], String.valueOf(rowIndex), singleRow[1],singleRow[2],singleRow[3]};
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



    private static ArrayList<String[]> addInvIndexRow(InvoiceLineModel invoiceLineModel, ArrayList<String[]> readItemsFile,  int rowIndex) {
        ArrayList<String[]> allData = new ArrayList<>();



        for (int row = 0; row < invoiceLineModel.getRowCount(); row++) {
            String[] singleRow = new String[invoiceLineModel.getColumnCount()];
            for (int column = 0; column < invoiceLineModel.getColumnCount(); column++) {
                if ((column!=invoiceLineModel.getColumnCount()-1)){

                    singleRow[column] = (String) invoiceLineModel.getValueAt(row, column);
                }

            }
            int i = 0;
            for (String[] itemRow: readItemsFile) {
                if (itemRow[0] == invoiceLineModel.getValueAt(row,0) && itemRow[1] == String.valueOf(rowIndex)){
                    singleRow = new String[]{singleRow[0], String.valueOf(rowIndex), singleRow[1],singleRow[2],singleRow[3]};
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

    public  static void dateFormatter(final JTextField invDate){

        invDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

                if (invDate.getText().equals("dd-mm-yyyy")) {
                    invDate.setText("");
                    invDate.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (invDate.getText().isEmpty()) {
                    invDate.setForeground(Color.GRAY);
                    invDate.setText("dd-mm-yyyy");
                }
            }
        });
    }


}
