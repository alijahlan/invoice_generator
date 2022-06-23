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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainFrame extends JFrame implements ActionListener {


    final File invTablePath = new File("src/main/java/dataFiles/InvoiceHeader.csv");
    final File invTableItemsPath = new File("src/main/java/dataFiles/InvoiceLine.csv");
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
    JLabel guideLbl;
    String[] invTableCols;
    String[][] invTableData;
    String[] invItemsCols;
    String[][] invItemsData;
    JPanel rightPanelInvData;
    DefaultTableModel invTableModel;
    InvoiceHeaderModel invoiceHeaderModel;
    InvoiceLineModel invItemsModel;
    ArrayList<String[]> readInvFile;
    ArrayList<String[]> readItemsFile;

    JScrollPane tableSP2;
    JPanel rightPanelBtn;


    public MainFrame(String title) throws HeadlessException {
        super(title);
        initApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        switch (e.getActionCommand()) {
            case "load":
//                JFileChooser fc = new JFileChooser();
//                int result = fc.showOpenDialog(this);
//                if(result == JFileChooser.APPROVE_OPTION) {
//                    String path = fc.getSelectedFile().getPath();
//                    readInvFile = FileOperations.readFile(new File(path), this);
//                    invoiceHeaderModel.AddCSVData(readInvFile);
//                }

                readInvFile = FileOperations.readFile(invTablePath, this);
                readItemsFile = FileOperations.readFile(invTableItemsPath, this);

                invoiceHeaderModel.AddCSVData(readInvFile);
                invItemsModel.AddCSVData(readItemsFile);

                JOptionPane.showMessageDialog(this, "Loading the last update completed successfully");
                break;

            case "create":
                ActionsController.dateFormatter(invDate);
                invItemsTable.setEnabled(true);
                tableSP2.setVisible(true);
                rightPanelBtn.setVisible(true);
                invNo.setEnabled(true);
                invDate.setEnabled(true);
                customerName.setEnabled(true);
                invTotal.setEnabled(true);

                guideLbl.setFont(new Font("", Font.BOLD, 12));
                guideLbl.setText("To add a new Item click on the following table ");

                saveBtn.setEnabled(true);
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

                String str = Integer.toString(index);

                ArrayList<String[]> temp = new ArrayList<>();
                invNo.setText(String.valueOf(index));
                invDate.setText("dd-mm-yyyy");
                invDate.setForeground(Color.GRAY);
                customerName.setText("");
                invTotal.setText("");
                ArrayList<String[]> temp2 = new ArrayList<>();
                temp2.add(new String[]{"", "", "", "", ""});
                invItemsModel.AddCSVData(temp2);
                if (index > 0) {
                    invDate.setEditable(true);
                    customerName.setEditable(true);

                }

                invItemsData = new String[][]{{}, {}, {}};


                //String[] row ;
                for (int i = 0; i < 1; i++) {

                    temp.add(new String[]{"", "", "", "", ""});
                }

                invItemsModel.AddCSVData(temp);
                break;


            case "delete":
                int[] selectedRow;
                selectedRow = invTable.getSelectedRows();
                invTable.clearSelection();
                invTable.setRowSelectionInterval(selectedRow[0],selectedRow[0]);
                if (selectedRow.length > 0) {
                    ArrayList<String[]> itemsAfterDelete = ActionsController.deleteInvoice(this, invoiceHeaderModel, readItemsFile, selectedRow[0]);

                    String invTablePath = "src/main/java/dataFiles/InvoiceHeader.csv";
                    String invTableItemsPath = "src/main/java/dataFiles/InvoiceLine.csv";
                    readItemsFile = itemsAfterDelete;
                    ArrayList<String[]> newArrayList = new ArrayList<>();

                    FileOperations.writeFiles(invTablePath, invTableItemsPath, readInvFile, itemsAfterDelete, this);
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
                    JOptionPane.showMessageDialog(this, "No row is selected! Please select one row", "Select row", JOptionPane.ERROR_MESSAGE);
                }
                if (invTable.getRowCount() <= 0) {

                    cancelBtn.setEnabled(false);
                    saveBtn.setEnabled(false);
                    invNo.setText("");
                    invDate.setText("");
                    customerName.setText("");
                    invTotal.setText("");


                }
                invItemsData = new String[][]{};

                if (invNo.getText().length() == 0) {
                    invDate.setEditable(false);
                    customerName.setEditable(false);
                    ArrayList<String[]> temp4 = new ArrayList<>();
                    temp4.add(new String[]{"", "", "", "", ""});
                    invItemsModel.AddCSVData(temp4);

                }


                break;
// end of delete --- in case the user click on delete button


            case "save":
                int[] selectedRowSave = invTable.getSelectedRows();

                String vDateStr = invDate.getText();
                DateFormat formatter;
                formatter = new SimpleDateFormat("dd-mm-yyyy");
                Date dateTemp = null;
                try {
                    dateTemp = (Date) formatter.parse(vDateStr);
                } catch (ParseException ex) {

                }
                //System.out.println("output: " + dateTemp);

                if (dateTemp != null){
                    if (invDate.getText().length() > 0 && (customerName.getText().length() > 0) && invTotal.getText().length() > 0) {

                        TableModel invTableModel = invTable.getModel();
                        TableModel invItemsTableModel = invItemsTable.getModel();
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
                                indexExistItem = row+1;
                                break;

                            } else {
                                isExist = false;
                                //System.out.println("not exist");
                                indexExistItem=-1;
                                //break;
                            }
                        }

                        if (!isExist) {

                            newRow.add(newRowTemp);

                        } else {
                            if (indexExistItem > 0) {
                                ArrayList<String[]> arrayListTemp = new ArrayList<>();
                                newRow.set(indexExistItem-1, newRowTemp);

                            }
                        }
                        ArrayList<String[]> readInvFile1 = new ArrayList<>();


                        ActionsController.saveInvoice(invoiceHeaderModel, invItemsModel, readInvFile, readItemsFile, newRowTemp, this);
                        if (indexExistItem > 0) {
                            invTable.setRowSelectionInterval(indexExistItem-1, indexExistItem-1);
                        } else {
                            invTable.setRowSelectionInterval(invTable.getRowCount() - 1, invTable.getRowCount() - 1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please complete all fields");
                    }
        } else{
                    JOptionPane.showMessageDialog(this, "Accepted date format ( dd-mm-yyyy )","Date format not valid!!", JOptionPane.ERROR_MESSAGE);

                    invDate.grabFocus();
                    invDate.setText("");
                }
                break;


            case "cancel":
                try {


                    selectedRow = invTable.getSelectedRows();
                    if (selectedRow.length > 0) {

                        if (invTable.getRowCount() - 1 > selectedRow[0]) {
                            invTable.setRowSelectionInterval(selectedRow[0] + 1, selectedRow[0] + 1);
                            invTable.setRowSelectionInterval(selectedRow[0], selectedRow[0]);

                        } else if (invTable.getRowCount() - 1 == selectedRow[0]) {

                            //invTable.setRowSelectionAllowed(false);
                            if (invTable.getRowCount() > 1) {

                                invTable.setRowSelectionInterval(selectedRow[0] - 1, selectedRow[0] - 1);
                                invTable.setRowSelectionInterval(selectedRow[0], selectedRow[0]);
                            } else {
                                invTable.clearSelection();
                                invTable.setRowSelectionInterval(selectedRow[0], selectedRow[0]);
                            }

                        }

                        invNo.setText((String) invTable.getValueAt(selectedRow[0], 0));
                        invDate.setText((String) invTable.getValueAt(selectedRow[0], 1));
                        customerName.setText((String) invTable.getValueAt(selectedRow[0], 2));
                        invTotal.setText((String) invTable.getValueAt(selectedRow[0], 4));
                        invTableListener(invTable);
                        invItemsModel.AddCSVData((ArrayList<String[]>) invTable.getModel());

                    } else {
                        invNo.setText("");
                        invDate.setText("");
                        customerName.setText("");
                        invTotal.setText("");
                        if (invNo.getText().length() == 0) {
                            invDate.setEditable(false);
                            customerName.setEditable(false);
                            ArrayList<String[]> temp3 = new ArrayList<>();
                            temp3.add(new String[]{"", "", "", "", ""});
                            //invItemsModel.AddCSVData(temp2);
                            invItemsTable.setModel(new InvoiceLineModel());

                        }

                    }
                } catch (ArrayIndexOutOfBoundsException ee) {

                }
                break;
        }


    }


    //    Listener for the main table on the left panel
    private void invTableListener(final JTable invTable) {

        ListSelectionModel rowSelectionModel = invTable.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        rowSelectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                invDate.setForeground(Color.black);
                String selectedData = null;

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
                    guideLbl.setFont(new Font("",Font.BOLD,12));

                    //guideLbl.setText("You can't add new item");

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
                       //rightPanelInvData.setVisible(false);
                       invTable.clearSelection();

                       guideLbl.setFont(new Font("",Font.BOLD,15));
                       //guideLbl.setText("To show invoices details select an Invoice from the right table");


                }

            }

        });

    } // end of invoice table selection method


    //    Items table listener on the right panel
    private void invItemsListener(final JTable itemsTable) {

        final double[] totalInvItems = {0.0};

        ListSelectionModel rowSelectionModel = itemsTable.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        rowSelectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (invTotal.getText().length() > 0) {
                    totalInvItems[0] = Double.valueOf(invTotal.getText());
                } else {
                    totalInvItems[0] = 0.0;
                }
                //counter++;
                //String selectedData = null;
                int[] selectedRow = itemsTable.getSelectedRows();
                int[] selectedColumns = itemsTable.getSelectedColumns();

                if (selectedRow.length > 0) {
                    ArrayList<String[]> temp = new ArrayList<>();

                    ArrayList<String[]> dataModel = invItemsModel.Data;

                    String result1 = "", result2 = "", result3 = "";

                    result1 = JOptionPane.showInputDialog("Enter item name:");

                    // result1 -- Item Name
                    if (result1 != null) {
                        boolean isNum = false;
                        do {

                            try {
                                // result2 -- item price
                                result2 = JOptionPane.showInputDialog("Enter item price:");
                                if (result2 != null) {

                                    Double.parseDouble(result2);
                                    if (Double.parseDouble(result2) == 0) {
                                        isNum = true;

                                    } else {
                                        isNum = false;
                                    }
                                    //break;
                                }
                            } catch (NumberFormatException ee) {
                                isNum = true;

                            }


                        } while (isNum);


                        if (result2 != null) {
                            do {

                                try {
                                    // result3 -- item count
                                    result3 = JOptionPane.showInputDialog("Enter item count:");

                                    Integer.parseInt(result3);

                                    isNum = false;
                                    //break;
                                } catch (NumberFormatException ee) {

                                    isNum = true;
                                    //break;
                                }


                            } while (isNum);

                        }
                    }

                    String itemTotal;
                    if (result1 != null && result2 != null && result3 != null) {

                        if (Integer.valueOf(result3) > 0 || Double.valueOf(result2) > 0) {

                            double singleItem = Integer.parseInt(result3) * Double.parseDouble(result2);
                            totalInvItems[0] += singleItem;
                            itemTotal = String.valueOf(totalInvItems[0]);
                            totalInvItems[0] = 0.0;
                            invTotal.setText(itemTotal);
                            String[] row1 = new String[]{String.valueOf(1), result1, result2, result3, String.valueOf(singleItem)};
                            if (dataModel.get(0)[1] == "") {
                                temp.add(row1);
                            } else {
                                row1[0] = String.valueOf(dataModel.size() + 1);
                                temp.addAll(dataModel);
                                temp.add(row1);
                            }
                            invItemsModel.AddCSVData(temp);
                        } else {
                            JOptionPane.showMessageDialog(invItemsTable, "Sorry, the item will not added, zeros or minus data not acceptable", "Value rejected", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }


            }

        });

    } // end of Items table selection method

private void initApp(){

    readInvFile = FileOperations.readFile(invTablePath, this);
    readItemsFile = FileOperations.readFile(invTableItemsPath, this);


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
    invItemsTable = new JTable(invItemsModel);


    //        Main frame setting

    setLayout(new GridLayout());
    setSize(1300, 800);
    //setLocation(50,10);
    Toolkit toolkit = getToolkit();
    Dimension size = toolkit.getScreenSize();
    setLocation(size.width / 2 - getWidth() / 2, 0);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);

//        Menu bar details
    menuBar = new JMenuBar();
    file = new JMenu("File");
    menuBar.add(file);
    loadFile = new JMenuItem("Load File", 'l');
    loadFile.addActionListener(this);
    loadFile.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.ALT_DOWN_MASK));
    loadFile.setActionCommand("load");
    file.add(loadFile);
    saveFile = new JMenuItem("Save File", 's');
    saveFile.addActionListener(this);
    saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.ALT_DOWN_MASK));
    saveFile.setActionCommand("save");
    file.add(saveFile);
    setJMenuBar(menuBar);

//        Panels

    Container container = new Container();
    container.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    leftPanel.setAlignmentX(SwingConstants.LEFT);

    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
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

    EmptyBorder labelMargin = new EmptyBorder(20, 10, 15, 0);
    JLabel lbl = new JLabel("Invoices Table");
    lbl.setBorder(labelMargin);


    // Left panel Buttons
    JPanel leftPanelBtn = new JPanel(new GridLayout(1, 1, 30, 10));

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
    rightPanelInvData.setPreferredSize(new Dimension(getWidth() / 2 - 50, 210));

//        Invoice Number
    JLabel invNoLbl = new JLabel("Invoice Number");
    gbc.insets = new Insets(20, 0, 0, 0);
    gbc.weightx = 0.05;
    gbc.gridx = 0;
    gbc.gridy = 0;
    rightPanelInvData.add(invNoLbl, gbc);

    invNo = new JLabel();
    gbc.weightx = 0.95;
    gbc.gridx = 1;
    gbc.gridy = 0;
    rightPanelInvData.add(invNo, gbc);


//        Invoice Date
    JLabel invDateLbl = new JLabel("Invoice Date");
    gbc.ipady = 8;
    gbc.weightx = 0.05;
    gbc.gridx = 0;
    gbc.gridy = 1;
    rightPanelInvData.add(invDateLbl, gbc);

    invDate = new JTextField("dd-mm-yyyy");
    invDate.setForeground(Color.GRAY);
    invDate.addActionListener(this);


    //invDate.addFocusListener();
/*        Pattern regxDate = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher matchDate = regxDate.matcher(invDate.getText());*/

    gbc.weightx = 0.95;
    gbc.gridx = 1;
    gbc.gridy = 1;

    rightPanelInvData.add(invDate, gbc);


//        Customer Name
    JLabel customerNameLbl = new JLabel("Customer Name");
    gbc.ipady = 8;
    gbc.weightx = 0.05;
    gbc.gridx = 0;
    gbc.gridy = 2;
    rightPanelInvData.add(customerNameLbl, gbc);

    customerName = new JTextField();
    gbc.weightx = 0.95;
    gbc.gridx = 1;
    gbc.gridy = 2;
    rightPanelInvData.add(customerName, gbc);


//        Invoice Total
    JLabel invTotalLbl = new JLabel("Invoice Total");
    gbc.insets = new Insets(20, 0, 0, 0);
    gbc.weightx = 0.05;
    gbc.gridx = 0;
    gbc.gridy = 3;
    rightPanelInvData.add(invTotalLbl, gbc);

    invTotal = new JLabel();
    gbc.insets = new Insets(20, 0, 10, 0);
    gbc.weightx = 0.95;
    gbc.gridx = 1;
    gbc.gridy = 3;
    rightPanelInvData.add(invTotal, gbc);

    guideLbl = new JLabel("To add a new Item to the current invoice click on the following table");
    guideLbl.setForeground(Color.RED);
    gbc.insets = new Insets(20, 0, 0, 0);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 4;
    rightPanelInvData.add(guideLbl, gbc);


// Group of Items for each invoice

    Border titlePanel = BorderFactory.createTitledBorder("Invoice Items");
    JPanel itemsList = new JPanel();
    itemsList.setBorder(titlePanel);


    JTableHeader tableHeaderListItems = invItemsTable.getTableHeader();
    tableHeaderListItems.setBackground(Color.WHITE);

    tableSP2 = new JScrollPane(invItemsTable);
    tableSP2.setPreferredSize(new Dimension(getWidth() / 2 - 50, getHeight() - 400));
    leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    DefaultTableCellRenderer renderer2;
    renderer2 = (DefaultTableCellRenderer) invItemsTable.getTableHeader().getDefaultRenderer();
    renderer2.setHorizontalAlignment(JLabel.LEFT);

    itemsList.add(tableSP2);


    // Right panel Buttons
    rightPanelBtn = new JPanel(new GridLayout(1, 1, 100, 10));
    EmptyBorder labelMargin2 = new EmptyBorder(20, 30, 15, 0);
    saveBtn = new JButton("Add Item");
    saveBtn.setActionCommand("save");
    saveBtn.addActionListener(this);

    cancelBtn = new JButton("Cancel");
    cancelBtn.setActionCommand("cancel");
    cancelBtn.addActionListener(this);

    if (readInvFile.size() < 0) {
        cancelBtn.setEnabled(false);
    }

    if (invDate.getText() == "" || customerName.getText() == "") {
        saveBtn.setEnabled(false);
    }else{
        saveBtn.setEnabled(true);
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


    container.setLayout(new GridLayout(1, 2));
    container.add(leftPanel);
    container.add(rightPanel);

    add(container);


    invTable.setRowSelectionAllowed(true);

//        The invoices' table selection listener
    invTableListener(invTable);
    invItemsListener(invItemsTable);

    if (invTable.getRowCount() > 0) {
        invTable.setRowSelectionInterval(0, 0);
    }
    invItemsTable.setToolTipText("Click on the invoice items table to add new item");
}

}
