package org.invoice_generator.controller;

import org.invoice_generator.model.FileOperations;
import org.invoice_generator.model.InvoiceHeaderModel;
import org.invoice_generator.model.InvoiceLineModel;
import org.invoice_generator.view.NewInvoiceDialog;
import org.invoice_generator.view.NewItemDialog;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class ActionHandler implements ActionListener {

    final String invTablePath = "src/main/java/dataFiles/InvoiceHeader.csv";
    final String invTableItemsPath = "src/main/java/dataFiles/InvoiceLine.csv";
    ArrayList<String[]> readInvFile;
    ArrayList<String[]> readItemsFile;
    InvoiceHeaderModel invoiceHeaderModel;
    InvoiceLineModel invItemsModel;
    JTable invTable;
    JTable invItemsTable;
    JButton cancelBtn;
    JButton saveBtn;
    JLabel invNo;
    JTextField invDate;
    JTextField customerName;
    JLabel invTotal;

    JFrame parent = null;


    public ActionHandler(ArrayList<String[]> readInvFile, ArrayList<String[]> readItemsFile,
                         JFrame parent, InvoiceHeaderModel invoiceHeaderModel,
                         InvoiceLineModel invItemsModel, JTable invTable, JTable invItemsTable,
                         JButton cancelBtn, JButton saveBtn, JLabel invNo,
                         JTextField invDate, JTextField customerNam, JLabel invTotal) {
        this.parent = parent;
        this.invoiceHeaderModel = invoiceHeaderModel;
        this.invItemsModel = invItemsModel;
        this.invTable = invTable;

        this.cancelBtn = cancelBtn;
        this.saveBtn = saveBtn;
        this.invNo = invNo;
        this.invDate = invDate;
        this.customerName = customerNam;
        this.invTotal = invTotal;

        this.invTable = invTable;
        this.invItemsTable = invItemsTable;

        //readInvFile = FileOperations.readFile(new File(invTablePath), parent);
        //readItemsFile = FileOperations.readFile(new File(invTableItemsPath), parent);

        this.readInvFile = readInvFile;
        this.readItemsFile = readItemsFile;

    }

    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "create":
                createNewInvoice();
                break;

            case "load":
                loadTablesData();
                break;

            case "delete":
                deleteInvoice();
                break;

            case "save":
                saveItems();
                break;

            case "saveAll":
                saveToFiles();
                break;


            case "cancel":
                deleteItem();
                break;
        }


    }

    private void createNewInvoice() {
        int index = invoiceHeaderModel.getRowCount() + 1;
        int biggestNo = 1;
        for (int i = 0; i < invoiceHeaderModel.getRowCount(); i++) {
            String mm = (String) invoiceHeaderModel.getValueAt(i, 0);
            int mmToInt = Integer.valueOf(mm);
            if (mmToInt > biggestNo) {
                biggestNo = mmToInt;
                index = (biggestNo + 1);
            }
        }
        // Dialog object for new invoice
        NewInvoiceDialog newInvoiceDialog;
        newInvoiceDialog = new NewInvoiceDialog(parent, "Add new invoice", index);
        newInvoiceDialog.setVisible(true);

        if (newInvoiceDialog.getInvoiceData() != null) {

            String str = Integer.toString(index);

            ArrayList<String[]> temp = new ArrayList<>();
            invNo.setText(newInvoiceDialog.getInvNo());
            invDate.setText(newInvoiceDialog.getInvDate());

            customerName.setText(newInvoiceDialog.getInvCustomerName());
            invTotal.setText(newInvoiceDialog.invTotal);

            readInvFile.add(readInvFile.size(), newInvoiceDialog.getInvoiceData());
            invoiceHeaderModel.AddCSVData(readInvFile);
            invTable.setRowSelectionInterval(invTable.getRowCount() - 1, invTable.getRowCount() - 1);
            ArrayList<String[]> temp2 = new ArrayList<>();
            temp2.add(new String[]{"", "", "", "", ""});
            invItemsModel.AddCSVData(temp2);
            if (index > 0) {
                invDate.setEditable(true);
                customerName.setEditable(true);

            }

            //String[][] invItemsData = new String[][]{{}, {}, {}};


            //String[] row ;
            for (int i = 0; i < 1; i++) {

                temp.add(new String[]{"1", "", "", "", ""});
            }

            try {

                saveBtn.setEnabled(true);
            } catch (NullPointerException e2) {

            }
            //cancelBtn.setEnabled(true);

                   /* FileOperations.writeFiles(invTablePath, invTableItemsPath, readInvFile, readItemsFile, parent);
                    invItemsModel.AddCSVData(temp); */
        }

//                saveBtn.doClick();
//                if (invItemsModel.getRowCount()<1){
//                    invoiceHeaderModel.deleteRow(invoiceHeaderModel.getRowCount()-1);
//                    invoiceHeaderModel.fireTableDataChanged();
//                }
    }

    private void saveToFiles(){

        File invoiceFile = null;
        String invoiceFileStr;
        String itemsFile = null;

        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("csv,txt", "csv", "txt"));
            fileChooser.setCurrentDirectory(new File("src/main/java/dataFiles/"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int res = fileChooser.showSaveDialog(parent);
            if (res == JFileChooser.APPROVE_OPTION) {
                invoiceFile = fileChooser.getSelectedFile();

                itemsFile = String.valueOf(invoiceFile.getParentFile());
                if (!invoiceFile.toString().endsWith(".csv")){
                   invoiceFileStr = invoiceFile.toString()+".csv";
                    System.out.println(invoiceFileStr);
                }else{
                invoiceFileStr= invoiceFile.toString();
                }

                String concatPath = invoiceFileStr.substring(invoiceFile.getParentFile().toString().length()+1,invoiceFileStr.length()-4);
               itemsFile+="/InvoiceLine-for-"+ concatPath +".csv";
               FileOperations.writeFiles(invoiceFileStr,itemsFile,readInvFile,readItemsFile, parent);

            }
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }



    }

    private void saveItems() {
        if (invDate.getText().length() > 0 && (customerName.getText().length() > 0) && invTotal.getText().length() > 0) {

                saveNewItem();

            InvoiceLineModel invTableModel = new InvoiceLineModel();
            //TableModel invItemsTableModel = invItemsTable.getModel();
            ArrayList<String[]> newRow = new ArrayList<>();

            String customerName1 = customerName.getText().replace(" ", "-");
            String[] newRowTemp = {invNo.getText(), invDate.getText(), customerName1, invTotal.getText()};
            String[] singleRow;
            boolean isExist = false;
            int indexExistItem = 0;
            for (int row = 0; row < invTableModel.getRowCount(); row++) {
                //System.out.println(row);
                singleRow = new String[invTableModel.getColumnCount()];
                for (int column = 0; column < invTableModel.getColumnCount(); column++) {
                    singleRow[column] = String.valueOf(invTableModel.getValueAt(row, column));

                }

                newRow.add(singleRow);
                //System.out.println(invNo.getText());
                //System.out.println(singleRow[0]);
                if (singleRow[0].equals(String.valueOf(invNo.getText()))) {
                    //System.out.println("is exist");
                    isExist = true;
                    indexExistItem = row + 1;
                    break;

                } else {
                    isExist = false;
                    //System.out.println("not exist");
                    indexExistItem = -1;
                    //break;
                }
            }

            if (!isExist) {

                newRow.add(newRowTemp);

            } else {
                if (indexExistItem > 0) {
                    ArrayList<String[]> arrayListTemp = new ArrayList<>();
                    newRow.set(indexExistItem - 1, newRowTemp);

                }
            }
            int[] selectedInvRow = invTable.getSelectedRows();
            ArrayList<String[]> readInvFile1 = new ArrayList<>();
            //invTable.setModel(invItemsModel);
            ActionsController.saveInvoice(invoiceHeaderModel, invItemsModel, readInvFile, readItemsFile, newRowTemp, parent);

            if (indexExistItem > 0) {
                invTable.setRowSelectionInterval(selectedInvRow[0], selectedInvRow[0]);
            } else {
                invTable.setRowSelectionInterval(selectedInvRow[0], selectedInvRow[0]);
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Please complete all fields");
        }

    }

    private void saveNewItem() {
        NewItemDialog newItemDialog;
        newItemDialog = new NewItemDialog(null, "Add new item");
        newItemDialog.setVisible(true);
        //invItemsListener(invItemsTable,newItemDialog.itemData);

        final double[] totalInvItems = {0.0};

        if (invTotal.getText().length() > 0) {
            totalInvItems[0] = Double.valueOf(invTotal.getText());
        } else {
            totalInvItems[0] = 0.0;
        }
        ArrayList<String[]> temp = new ArrayList<>();

        ArrayList<String[]> dataModel = invItemsModel.Data;
        String result1 = null, result2 = null, result3 = null;
        if (newItemDialog.itemData != null) {
            result1 = newItemDialog.itemData[1];
            result2 = newItemDialog.itemData[2];
            result3 = newItemDialog.itemData[3];
        }
        //result1 = JOptionPane.showInputDialog("Enter item name:");


        String itemTotal;
        if (result1 != null && result2 != null && result3 != null) {

            if (Integer.valueOf(result3) > 0 || Double.valueOf(result2) > 0) {

                double singleItem = Integer.parseInt(result3) * Double.parseDouble(result2);
                totalInvItems[0] += singleItem;
                itemTotal = String.valueOf(totalInvItems[0]);
                totalInvItems[0] = 0.0;
                invTotal.setText(itemTotal);
                String[] row1 = new String[]{String.valueOf(1), result1, result2, result3, String.valueOf(singleItem)};
                try {
                    if (dataModel.get(0)[1] == "") {
                        temp.add(row1);
                    } else {
                        row1[0] = String.valueOf(Integer.valueOf(dataModel.get(dataModel.size() - 1)[0]) + 1);
                        temp.addAll(dataModel);
                        temp.add(row1);
                    }
                    invItemsModel.AddCSVData(temp);
                } catch (IndexOutOfBoundsException e) {

                }

            } else {
                JOptionPane.showMessageDialog(null, "Sorry, the item will not added, zeros or minus data not acceptable", "Value rejected", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void deleteItem() {
        ArrayList<String[]> itemsAfterDelete;
        int selectedInvRow = 0;
        int selectedItemRow = 0;
        if (invItemsTable.getValueAt(0, 0) != "") {

            selectedInvRow = Integer.parseInt((String) invTable.getValueAt(invTable.getSelectedRows()[0], 0));

            try {
                selectedItemRow = Integer.parseInt((String) invItemsTable.getValueAt(invItemsTable.getSelectedRows()[0], 0));

            } catch (ArrayIndexOutOfBoundsException e1) {
                System.out.println("Delete item error");
                System.out.println(e1.getMessage());
            } catch (IndexOutOfBoundsException e2){

            }
        }

        int[] selectedInvRows = invTable.getSelectedRows();
        System.out.println(selectedItemRow);
        System.out.println(invItemsTable.getValueAt(invItemsTable.getSelectedRow(), 0));
        if (invItemsTable.getRowCount() != 1) {
            if (selectedItemRow > 0 && invItemsTable.getValueAt(invItemsTable.getSelectedRow(), 0) != "") {

                String invTotalTemp = String.valueOf(invItemsTable.getValueAt(invItemsTable.getSelectedRow(), invItemsTable.getColumnCount() - 1));


                itemsAfterDelete = ActionsController.deleteItem(parent, readItemsFile, selectedItemRow, selectedInvRow);
                cancelBtn.setEnabled(false);
                String invTablePath = "src/main/java/dataFiles/InvoiceHeader.csv";
                String invTableItemsPath = "src/main/java/dataFiles/InvoiceLine.csv";
                readItemsFile = itemsAfterDelete;
                ArrayList<String[]> newArrayList = new ArrayList<>();
                System.out.println(selectedInvRow);
                double x = Double.valueOf(readInvFile.get(selectedInvRow - 1)[3]) - Double.valueOf(invTotalTemp);
                System.out.println(x);
                String[] tempRow = readInvFile.get(selectedInvRow - 1);
                tempRow[3] = String.valueOf(x);
                readInvFile.set(selectedInvRow - 1, tempRow);
                FileOperations.writeFiles(invTablePath, invTableItemsPath, readInvFile, itemsAfterDelete, parent);
                //readItemsFile = FileOperations.readFile(new File(invTableItemsPath), parent);
                //readItemsFile = itemsAfterDelete;
                invItemsModel.AddCSVData(itemsAfterDelete);
                if (invTable.getRowCount() > 0) {
                    if (invTable.getRowCount() > selectedInvRows[0]) {

                        invTable.clearSelection();
                        invTable.setRowSelectionInterval(selectedInvRows[0], selectedInvRows[0]);

                    }

                }

            } else {
                JOptionPane.showMessageDialog(parent, "No row is selected! Please select one row", "Select row", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "The invoice should have at least one item, delete the invoice on the left side");
        }
//                invItemsData = new String[][]{};

        if (invItemsModel.getRowCount() <= 0) {

            ArrayList<String[]> temp4 = new ArrayList<>();
            temp4.add(new String[]{"", "", "", "", ""});
            invItemsModel.AddCSVData(temp4);

        }

    }

    private void deleteInvoice() {
        int[] selectedRow;
        selectedRow = invTable.getSelectedRows();
        invTable.clearSelection();
        invTable.setRowSelectionInterval(selectedRow[0], selectedRow[0]);
        if (selectedRow.length > 0) {
            ArrayList<String[]> itemsAfterDelete = ActionsController.deleteInvoice(parent, invoiceHeaderModel, readItemsFile, selectedRow[0]);

            readItemsFile = itemsAfterDelete;
            ArrayList<String[]> newArrayList = new ArrayList<>();

            FileOperations.writeFiles(invTablePath, invTableItemsPath, readInvFile, itemsAfterDelete, parent);
            //readItemsFile = FileOperations.readFile(new File(invTableItemsPath), this);
            //readItemsFile = itemsAfterDelete;
            invItemsModel.AddCSVData(itemsAfterDelete);
            if (invoiceHeaderModel.getRowCount() > 0) {
                if (invoiceHeaderModel.getRowCount() > selectedRow[0]) {

                    invTable.clearSelection();
                    invTable.setRowSelectionInterval(selectedRow[0], selectedRow[0]);

                } else {
                    invTable.clearSelection();
                    invTable.setRowSelectionInterval(selectedRow[0] - 1, selectedRow[0] - 1);
                }

            }

        } else {
            JOptionPane.showMessageDialog(parent, "No row is selected! Please select one row", "Select row", JOptionPane.ERROR_MESSAGE);
        }
        if (invTable.getRowCount() <= 0) {

            cancelBtn.setEnabled(false);
            saveBtn.setEnabled(false);
            invNo.setText("");
            invDate.setText("");
            customerName.setText("");
            invTotal.setText("");


        }
        //invItemsData = new String[][]{};

        if (invNo.getText().length() == 0) {
            invDate.setEditable(false);
            customerName.setEditable(false);
            ArrayList<String[]> temp4 = new ArrayList<>();
            temp4.add(new String[]{"", "", "", "", ""});
            invItemsModel.AddCSVData(temp4);

        }
    }

    private void loadTablesData() {

        File invoiceFile = null;
        File itemsFile = null;

        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("csv,txt", "csv", "txt"));
            fileChooser.setCurrentDirectory(new File("src/main/java/dataFiles/"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            int res = fileChooser.showOpenDialog(parent);
            if (res == JFileChooser.APPROVE_OPTION) {
                invoiceFile = fileChooser.getSelectedFile();
                readInvFile = FileOperations.readFile(new File(invoiceFile.getPath()), parent);
                try{

                if (readInvFile.get(0).length == 4) {

                    int res2 = fileChooser.showOpenDialog(parent);
                    if (res2 == JFileChooser.APPROVE_OPTION) {
                        itemsFile = fileChooser.getSelectedFile();

                        readItemsFile = FileOperations.readFile(new File(itemsFile.getPath()), parent);

                        if (readItemsFile.get(0).length == 5) {

                            invoiceHeaderModel.AddCSVData(readInvFile);
                            invoiceHeaderModel.fireTableDataChanged();
                            invItemsModel.AddCSVData(readItemsFile);
                            invItemsModel.fireTableDataChanged();

                            invTable.setRowSelectionInterval(0, 0);
                        } else {
                            JOptionPane.showMessageDialog(null, "The items file not found, please select the right file");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The invoices file not found, please select the right file");

                }
                }catch (IndexOutOfBoundsException ex){
                    ex.printStackTrace();
                }

            }
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }


        //JOptionPane.showMessageDialog(parent, "Loading the last update completed successfully");

    }


} // ActionHandler class
