package org.invoice_generator.view;

import org.invoice_generator.controller.ActionsController;
import org.invoice_generator.model.FileOperations;
import org.invoice_generator.model.InvoiceHeaderModel;
import org.invoice_generator.model.InvoiceLineModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame implements ActionListener {

//    using to add indexing to the items' table rows
    //private static int counter=0;

//    Menu bar params
    JMenuBar menuBar;
    JMenu file;
    JMenuItem loadFile;
    JMenuItem saveFile;
    JTable invTable;
    JTable invItemsTable;
    JButton createBtn;
    JButton deleteBtn;
    JButton saveBtn;
    JButton cancelBtn;

    JLabel invNo;
    JTextField invDate;
    JTextField customerName;
    JLabel invTotal;
    String[] invTableCols;
    String[][] invTableData;

    String[] invItemsCols ;
    String[][] invItemsData;
    JPanel rightPanelInvData;
    DefaultTableModel invTableModel;

    InvoiceHeaderModel invoiceHeaderModel;
    InvoiceLineModel invItemsModel;
    ArrayList<String[]> readInvFile;
    ArrayList<String[]> readItemsFile;
    final File invTablePath = new File("src/main/java/dataFiles/InvoiceHeader.csv");
    final File invTableItemsPath = new File("src/main/java/dataFiles/InvoiceLine.csv");
    public MainFrame(String title) throws HeadlessException {
        super(title);


        readInvFile = FileOperations.readFile(invTablePath);
        readItemsFile = FileOperations.readFile(invTableItemsPath);


        // Demo data for table 1 on the left side
        invoiceHeaderModel = new InvoiceHeaderModel();
        invTable = new JTable(invoiceHeaderModel);
        invTable.setDefaultEditor(Object.class, null);
        //invTable.setModel(invTableModel);
        invoiceHeaderModel.AddCSVData(readInvFile);

        // Demo data for table 2 on the right side
        //invItemsModel = new InvoiceLineModel();

        invItemsModel = new InvoiceLineModel();
        invItemsModel.AddCSVData(readItemsFile);
       //invItemsCols = new String[]{"No.", "Item Name", "Item Price", "Count", "Item Total"};
        //invItemsData = new String[][]{};
        // System.out.println(invItemsModel.getColumnCount());
        invItemsTable = new JTable(invItemsModel);
        //invItemsTable.setModel(new DefaultTableModel(invItemsData , invItemsCols));

        //ActionsController.createInvoice(invItemsData);



        //        Main frame setting

        //setLayout(new BorderLayout());
        setSize(1300,800);
        //setLocation(50,10);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2,0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

//        Menu bar details
        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuBar.add(file);
        loadFile = new JMenuItem("Load File",'l');
        loadFile.addActionListener(this);
        loadFile.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.ALT_DOWN_MASK));
        loadFile.setActionCommand("load");
        file.add(loadFile);
        saveFile = new JMenuItem("Save File",'s');
        saveFile.addActionListener(this);
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.ALT_DOWN_MASK));
        saveFile.setActionCommand("save");
        file.add(saveFile);
        setJMenuBar(menuBar);

//        Panels
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        Container container = new Container();
        container.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        //leftPanel.setBackground(Color.BLUE);
        //leftPanel.setBounds(0,0, getWidth() / 2, 500);
        leftPanel.setAlignmentX(SwingConstants.LEFT);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        //rightPanel.setBackground(Color.RED);
        //rightPanel.setBounds(getWidth()/2,0, 500, 500);
        rightPanel.setAlignmentX(SwingConstants.LEFT);



//        Left Panel Content

        JTableHeader tableHeader = invTable.getTableHeader();
        tableHeader.setBackground(Color.WHITE);

        JScrollPane tableSP = new JScrollPane(invTable);
        tableSP.setPreferredSize(new Dimension(getWidth() / 2 - 20, getHeight() - 240));
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        DefaultTableCellRenderer renderer;
        renderer = (DefaultTableCellRenderer) invTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        EmptyBorder labelMargin = new EmptyBorder(20,10,15,0);
        //leftPanel.setLayout(new FlowLayout());
        JLabel lbl = new JLabel("Invoices Table");
        lbl.setBorder(labelMargin);


        // Left panel Buttons
        JPanel leftPanelBtn = new JPanel(new GridLayout(1,1,30,10));

        createBtn = new JButton("Create New Invoice");
        createBtn.setActionCommand("create");
        createBtn.addActionListener(this);

        deleteBtn = new JButton("Delete Invoice");
        deleteBtn.setActionCommand("delete");
        deleteBtn.addActionListener(this);

        JLabel emptyLbl = new JLabel("");
        emptyLbl.setAlignmentX(SwingConstants.LEFT);
        emptyLbl.setBorder(labelMargin);
        leftPanelBtn.add(emptyLbl);
        leftPanelBtn.add(createBtn);

        leftPanelBtn.add(deleteBtn);
        leftPanelBtn.setAlignmentX(JLabel.CENTER);
        leftPanel.add(lbl);
        leftPanel.add(tableSP);
        leftPanel.add(leftPanelBtn);


//        Right panel

        rightPanelInvData = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        rightPanelInvData.setPreferredSize(new Dimension(getWidth() /2-50,210));

//        Invoice Number
        JLabel invNoLbl = new JLabel("Invoice Number");
        gbc.insets = new Insets(20,0,0,0);
        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanelInvData.add(invNoLbl,gbc);

        invNo = new JLabel();
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 0;
        rightPanelInvData.add(invNo,gbc);



//        Invoice Date
        JLabel invDateLbl = new JLabel("Invoice Date");
        gbc.ipady = 8;
        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanelInvData.add(invDateLbl,gbc);

        invDate = new JTextField();
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 1;

        rightPanelInvData.add(invDate,gbc);


//        Customer Name
        JLabel customerNameLbl = new JLabel("Customer Name");
        gbc.ipady = 8;
        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanelInvData.add(customerNameLbl,gbc);

        customerName = new JTextField();
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 2;
        rightPanelInvData.add(customerName,gbc);


//        Invoice Total
        JLabel invTotalLbl = new JLabel("Invoice Total");
        gbc.insets = new Insets(20,0,0,0);
        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanelInvData.add(invTotalLbl,gbc);

        invTotal = new JLabel();
        gbc.insets = new Insets(20,0,10,0);
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 3;
        rightPanelInvData.add(invTotal,gbc);

        JLabel guide = new JLabel("To add Item Click on the Table");
        guide.setForeground(Color.RED);
        gbc.insets = new Insets(20,0,0,0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        rightPanelInvData.add(guide,gbc);


// Group of Items for each invoice

        Border titlePanel = BorderFactory.createTitledBorder("Invoice Items");
        JPanel itemsList = new JPanel();
        itemsList.setBorder(titlePanel);

//        invItemsModel = new InvoiceLineModel();
//        invItemsModel.AddCSVData(readItemsFile);
//        invItemsTable = new JTable(invItemsModel);
//        //invItemsTable = new JTable(invItemsData , invItemsCols);
//        //invItemsTable.setModel(new DefaultTableModel(invItemsData , invTableCols));
//        //invTableModel = (DefaultTableModel) invTable.getModel();

        JTableHeader tableHeaderListItems = invItemsTable.getTableHeader();
        tableHeaderListItems.setBackground(Color.WHITE);

        JScrollPane tableSP2 = new JScrollPane(invItemsTable);
        tableSP2.setPreferredSize(new Dimension(getWidth() / 2 - 50, getHeight() - 400));
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        DefaultTableCellRenderer renderer2;
        renderer2 = (DefaultTableCellRenderer) invItemsTable.getTableHeader().getDefaultRenderer();
        renderer2.setHorizontalAlignment(JLabel.LEFT);

        itemsList.add(tableSP2);


        // Right panel Buttons
        JPanel rightPanelBtn = new JPanel(new GridLayout(1,1,100,10));
        EmptyBorder labelMargin2 = new EmptyBorder(20,30,15,0);
        saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setActionCommand("cancel");
        cancelBtn.addActionListener(this);

        if(readInvFile.size() <= 0 ){
            cancelBtn.setEnabled(false);
        }

        if(invDate.getText()=="" || customerName.getText()==""){
            saveBtn.setEnabled(false);
        }
        JLabel emptyLbl1 = new JLabel("");
        emptyLbl1.setAlignmentX(SwingConstants.LEFT);
        emptyLbl1.setBorder(labelMargin2);
        rightPanelBtn.add(emptyLbl1);
        rightPanelBtn.add(saveBtn);

        rightPanelBtn.add(cancelBtn);
        rightPanelBtn.setAlignmentX(JLabel.CENTER);




        rightPanel.add(rightPanelInvData);
        rightPanel.add(itemsList);
        rightPanel.add(rightPanelBtn);


        container.setLayout(new GridLayout(1,2));
        container.add(leftPanel);
        container.add(rightPanel);

        add(container);


        invTable.setRowSelectionAllowed(true);

//        The invoices' table selection listener
        invTableListener(invTable);
        invItemsListener(invItemsTable);

    if(invTable.getRowCount() >0) {
            invTable.setRowSelectionInterval(0, 0);
        }
        invItemsTable.setToolTipText("Click on the invoice items table to add new item");
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        switch (e.getActionCommand()){
            case "create":

                saveBtn.setEnabled(true);
                int index = invoiceHeaderModel.getRowCount()+1;
                int biggestNo = 1;
                for (int i = 0; i < invoiceHeaderModel.getRowCount(); i++) {
                    String mm = (String) invoiceHeaderModel.getValueAt(i,0);
                    int mmToInt = Integer.valueOf(mm);
                    if(mmToInt > biggestNo){
                        biggestNo = mmToInt;
                        index = ( biggestNo+1);
                    }
                }

                String str = Integer.toString(index);

                ArrayList<String[]> temp = new ArrayList<>() ;
                invNo.setText(String.valueOf(index));
                invDate.setText("");
                customerName.setText("");
                invTotal.setText("");
                ArrayList<String[]> temp2 = new ArrayList<>() ;
                temp2.add(new String[]{"","","","",""});
                invItemsModel.AddCSVData(temp2);
                if(index > 0){
                    invDate.setEditable(true);
                    customerName.setEditable(true);

                }


                //invItemsCols = new String[]{"No.", "Item Name", "Item Price", "Count", "Item Total"};
                invItemsData = new String[][]{
                        {},
                        {},
                        {}
                };


                //String[] row ;
                for (int i =0; i <1; i++) {
                    //System.out.println(item.length);

                    temp.add(new String[]{"","","","",""});
                }

                System.out.println(temp.size());
                //invItemsTable = new JTable(invItemsData , invItemsCols);
                invItemsModel.AddCSVData(temp);
                //invItemsTable.setModel(new DefaultTableModel(invItemsData , invItemsCols));

                break;

            case "save":
                if( invDate.getText().length() > 0 && (customerName.getText().length() >0) && invTotal.getText().length() > 0) {
                    //saveBtn.setEnabled(false);
                    System.out.println((invDate.getText()!=""));
                TableModel model = invTable.getModel();

                //System.out.println(model.getValueAt(0,0));
                ArrayList<String[]> newRow = new ArrayList<>();

                String[] newRow1 = {invNo.getText(), invDate.getText(), customerName.getText(), invTotal.getText()};

                boolean isExist = false;
                int indexExistItem = -1;
                for(int row = 0; row < model.getRowCount(); row++) {
                    String[] singleRow = new String[model.getColumnCount()];
                    for(int column = 0; column < model.getColumnCount(); column++) {
                        singleRow[column]= (String) model.getValueAt(row, column);

                    }

                    newRow.add(singleRow);

                    if(singleRow[0]== invNo.getText()){
                        isExist = true;
                        indexExistItem = row ;
                    }
                }

                if(!isExist){

                newRow.add(newRow1);
                }
                else{
                    if(indexExistItem >=0 ) {

                        newRow.set(indexExistItem, newRow1);
                    }
                }
                //System.out.println(newRow.get(indexExistItem)[1]);
                //model.setValueAt(newRow1[0],Integer.valueOf(invNo.getText())-1,0);

               ActionsController.saveInvoice(invoiceHeaderModel, newRow);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Please complete all fields");
                }
                break;

            case "delete":
                int []selectedRow = invTable.getSelectedRows();
                if (selectedRow.length > 0) {
                    ActionsController.deleteInvoice(this, invoiceHeaderModel, selectedRow[0]);
                    if (invoiceHeaderModel.getRowCount() > 0) {
                        if (invoiceHeaderModel.getRowCount() > selectedRow[0]) {

                            invTable.setRowSelectionInterval(selectedRow[0] , selectedRow[0]);

                        }
                        else{
                            invTable.setRowSelectionInterval(selectedRow[0] - 1, selectedRow[0] - 1);
                            //invoiceHeaderModel.fireTableChanged(new TableModelEvent(invoiceHeaderModel));
                        }
                    }

                }       else{
                    JOptionPane.showMessageDialog(this,
                            "No row is selected! Please select one row",
                            "Select row",
                            JOptionPane.ERROR_MESSAGE);
                }
                if(invTable.getRowCount() <=0){

                    cancelBtn.setEnabled(false);
                    saveBtn.setEnabled(false);
                    invNo.setText("");
                    invDate.setText("");
                    customerName.setText("");
                    invTotal.setText("");


                }
                //invItemsCols = new String[]{"No.", "Item Name", "Item Price", "Count", "Item Total"};
                invItemsData = new String[][]{};

                //invItemsTable = new JTable(invItemsData , invItemsCols);
                //invItemsTable.setModel(new DefaultTableModel(invItemsData , invTableCols));
                //ArrayList<String> tempModel = invTable;
                //invoiceHeaderModel.AddCSVData(readInvFile);

                if(invNo.getText().length() == 0){
                    invDate.setEditable(false);
                    customerName.setEditable(false);
                    ArrayList<String[]> temp4 = new ArrayList<>() ;
                    temp4.add(new String[]{"","","","",""});
                    invItemsModel.AddCSVData(temp4);

                }
                break;

            case "cancel":
                try {


                //ActionsController.cancelInvoice();
                selectedRow = invTable.getSelectedRows();
                if(selectedRow.length>0){
                invTable.setRowSelectionInterval(selectedRow[0], selectedRow[0]);

                //selectedRow = invTable.getSelectedRows();

                    invNo.setText((String) invTable.getValueAt(selectedRow[0],0));
                    invDate.setText((String) invTable.getValueAt(selectedRow[0],1));
                    customerName.setText((String) invTable.getValueAt(selectedRow[0],2));
                    invTotal.setText((String) invTable.getValueAt(selectedRow[0],4));

                }
                else {
                    invNo.setText("");
                    invDate.setText("");
                    customerName.setText("");
                    invTotal.setText("");
                    if(invNo.getText().length() == 0){
                        invDate.setEditable(false);
                        customerName.setEditable(false);
                        ArrayList<String[]> temp3 = new ArrayList<>() ;
                        temp3.add(new String[]{"","","","",""});
                        //invItemsModel.AddCSVData(temp2);
                        invItemsTable.setModel(new InvoiceLineModel());
                    }

                }
                } catch (ArrayIndexOutOfBoundsException ee){

                }
                break;
        }


    }


//    Listener for the main table on the left panel
    private void invTableListener(final JTable invTable){

        ListSelectionModel rowSelectionModel = invTable.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        rowSelectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {

                String selectedData = null;

                int[] selectedRow = invTable.getSelectedRows();
                int[] selectedColumns = invTable.getSelectedColumns();

                if(selectedRow.length >0){

                    invNo.setText((String) invTable.getValueAt(selectedRow[0], 0));
                    invDate.setText((String) invTable.getValueAt(selectedRow[0], 1));
                    customerName.setText((String) invTable.getValueAt(selectedRow[0], 2));
                    invTotal.setText((String) invTable.getValueAt(selectedRow[0], 3));

                    ArrayList<String[]> temp = new ArrayList<>();
                    for (String[] item : readItemsFile) {
                        //item[]
                        String[] row = item;
                        double itemTotal =  Double.valueOf(row[3]) * Integer.valueOf(row[4]);
                        String[] row1 = new String[]{row[0], row[2], row[3], row[4], String.valueOf(itemTotal)};
                        //System.out.println(invTable.getValueAt(selectedRow[0],1));
                        if(row[1].equals(invTable.getValueAt(selectedRow[0],0))){
                            temp.add(row1);
                            //System.out.println(row1[0]);

                        }
                        invItemsModel.AddCSVData(temp);
                        //System.out.println("-------------");
                    }
                    //System.out.println(readItemsFile.get(0)[1]);
                }





            }

        });

    } // end of invoice table selection method


//    Items table listener on the right panel
    private void invItemsListener(final JTable itemsTable){

        final double[] totalInvItems = {0.0};

        ListSelectionModel rowSelectionModel = itemsTable.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        rowSelectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                System.out.println(invTotal.getText().length());
                if(invTotal.getText().length()>0){
                    System.out.println(Double.valueOf(invTotal.getText()));
                    totalInvItems[0] = Double.valueOf(invTotal.getText());
                }
                else{
                    totalInvItems[0]=0.0;
                }
                //counter++;
                //String selectedData = null;
                int[] selectedRow = itemsTable.getSelectedRows();
                int[] selectedColumns = itemsTable.getSelectedColumns();

                if(selectedRow.length >0){
                //System.out.println("selected");

                    ArrayList<String[]> temp = new ArrayList<>();

                    ArrayList<String[]> dataModel = invItemsModel.Data;

                    String result1="",result2 = "",result3 = "";

                    result1 = JOptionPane.showInputDialog("Enter item name:");

                    // result1 -- Item Name
                    if(result1!=null) {
                        boolean isNum = false;
                        do{

                            try
                            {
                                // result2 -- item price
                                result2 = JOptionPane.showInputDialog("Enter item price:");
                                if(result2!=null){

                                Double.parseDouble(result2);
                                if(Double.parseDouble(result2)==0){
                                    isNum = true;

                                }else{

                                System.out.println("try");
                                isNum = false;
                                }
                                //break;
                                }
                            }

                            catch(NumberFormatException ee)
                            {
                                System.out.println("catch");
                                isNum = true;

                            }


                        }while (isNum);


                        if(result2!=null) {
                            do{

                                try
                                {
                                    // result3 -- item count
                                    result3 = JOptionPane.showInputDialog("Enter item count:");

                                    Integer.parseInt(result3);
                                    System.out.println("try");
                                    isNum = false;
                                    //break;
                                }

                                catch(NumberFormatException ee)
                                {
                                    System.out.println("catch");
                                    isNum = true;
                                    //break;
                                }


                            }while (isNum);

                        }
                    }

                    String itemTotal;
                    if(result1!=null && result2!=null && result3!=null) {

                        if(Integer.valueOf(result3) > 0 || Double.valueOf(result2) > 0) {

                            double singleItem = Integer.parseInt(result3) * Double.parseDouble(result2);
                            totalInvItems[0] += singleItem;
                            itemTotal =  String.valueOf(totalInvItems[0]);
                            totalInvItems[0]=0.0;
                            invTotal.setText(itemTotal);
                            String[] row1 = new String[]{String.valueOf(1), result1, result2, result3,String.valueOf(singleItem)};
                            //System.out.println(invTable.getValueAt(selectedRow[0],1));
                            System.out.println(dataModel.get(0)[1]);
                            if (dataModel.get(0)[1] == "") {
                                temp.add(row1);
                            } else {
                                row1[0] = String.valueOf(dataModel.size() + 1);
                                temp.addAll(dataModel);
                                temp.add(row1);
                            }
                            invItemsModel.AddCSVData(temp);
                        }
                        else{
                            JOptionPane.showMessageDialog(invItemsTable,
                                    "Sorry, the item will not added, zeros or minus data not acceptable",
                                    "Value rejected",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }





            }

        });

    } // end of Items table selection method


}
