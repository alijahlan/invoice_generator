package org.invoice_generator.view;

import org.invoice_generator.controller.ActionHandler;
import org.invoice_generator.controller.ActionsController;
import org.invoice_generator.controller.TableHandler;
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
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainFrame extends JFrame {


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

    private void initApp() {


        readInvFile = FileOperations.readFile(invTablePath, this);
        readItemsFile = FileOperations.readFile(invTableItemsPath, this);


        // Demo data for table 1 on the left side
        invoiceHeaderModel = new InvoiceHeaderModel();
        invTable = new JTable(invoiceHeaderModel);
        invTable.setDefaultEditor(Object.class, null);
        invoiceHeaderModel.AddCSVData(readInvFile);

        // Demo data for table 2 on the right side


        invItemsModel = new InvoiceLineModel();
        invItemsModel.AddCSVData(readItemsFile);
        invItemsTable = new JTable(invItemsModel);
        invItemsTable.setDefaultEditor(Object.class, null);

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
        loadFile.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.ALT_DOWN_MASK));
        loadFile.setActionCommand("load");
        file.add(loadFile);
        saveFile = new JMenuItem("Save File", 's');
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.ALT_DOWN_MASK));
        saveFile.setActionCommand("saveAll");
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


        deleteBtn = new JButton("Delete Invoice");
        deleteBtn.setActionCommand("delete");


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

        guideLbl = new JLabel("");
        guideLbl.setForeground(Color.RED);
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        rightPanelInvData.add(guideLbl, gbc);

        saveBtn = new JButton("Add Item");
        saveBtn.setActionCommand("save");

        cancelBtn = new JButton("Delete item");
        cancelBtn.setActionCommand("cancel");

// Group of Items for each invoice

        Border titlePanel = BorderFactory.createTitledBorder("Invoice Items");
        JPanel itemsList = new JPanel();
        itemsList.setBorder(titlePanel);


        JTableHeader tableHeaderListItems = invItemsTable.getTableHeader();
        tableHeaderListItems.setBackground(Color.WHITE);

        tableSP2 = new JScrollPane(invItemsTable);
        tableSP2.setPreferredSize(new Dimension(getWidth() / 2 - 50, getHeight() - 400));
        leftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        itemsList.add(tableSP2);

        ActionHandler actionHandler = new ActionHandler(readInvFile, readItemsFile, this, invoiceHeaderModel, invItemsModel, invTable, invItemsTable, cancelBtn, saveBtn, invNo, invDate, customerName, invTotal);
        createBtn.addActionListener(actionHandler);
        deleteBtn.addActionListener(actionHandler);
        invDate.addActionListener(actionHandler);
        loadFile.addActionListener(actionHandler);
        saveFile.addActionListener(actionHandler);
        saveBtn.addActionListener(actionHandler);
        cancelBtn.addActionListener(actionHandler);


        // Right panel Buttons
        rightPanelBtn = new JPanel(new GridLayout(1, 1, 100, 10));
        EmptyBorder labelMargin2 = new EmptyBorder(20, 30, 15, 0);


        cancelBtn.setEnabled(false);
        if (readInvFile.size() < 0) {
            cancelBtn.setEnabled(false);
        }

        if (invDate.getText() == "" || customerName.getText() == "") {
            saveBtn.setEnabled(false);
        } else {
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
        ListSelectionModel rowSelectionModel = invTable.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        rowSelectionModel.addListSelectionListener(new TableHandler(cancelBtn, invNo, invDate, customerName,
                invTotal, guideLbl, invTable, tableSP2,
                rightPanelBtn, invItemsTable, invItemsModel, readItemsFile));

        //invItemsListener(invItemsTable, newItemDialog.itemData);

        if (invTable.getRowCount() > 0) {
            invTable.setRowSelectionInterval(0, 0);
        }



        invItemsTable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cancelBtn.setEnabled(true);
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (invItemsModel.getRowCount() < 1) {
                    cancelBtn.setEnabled(false);
                }

            }
        });


    }

}
