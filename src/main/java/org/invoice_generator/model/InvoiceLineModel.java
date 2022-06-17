package org.invoice_generator.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceLineModel extends AbstractTableModel {

    String itemName;
    double itemPrice;

    public InvoiceLineModel(){
        super();
    }
    private final String[] columnNames = {"No.", "Item Name", "Item Price", "Count", "Item Total"};
    public ArrayList<String[]> Data = new ArrayList<>();

    public void AddCSVData(ArrayList<String[]> DataIn) {

        this.Data = DataIn;
        this.fireTableDataChanged();

    }

    public  void deleteRow(int row){
        Data.remove(row);
        //this.fireTableRowsDeleted(row,row);

    }
    @Override
    public int getColumnCount() {
        return columnNames.length;// length;
    }

    @Override
    public int getRowCount() {
        return Data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        return Data.get(row)[col];
    }

//    @Override
//    public boolean isCellEditable(int row, int column) {
//        return true;
//    }
}