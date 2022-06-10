package org.invoice_generator.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame implements ActionListener {

//    Menu bar params
    JMenuBar menuBar;
    JMenu file;
    JMenuItem loadFile;
    JMenuItem saveFile;
    JTable invTable;

    JButton createBtn;
    JButton deleteBtn;
    JButton saveBtn;
    JButton cancelBtn;

    JTextField invDate;
    JTextField customerName;


    public MainFrame(String title) throws HeadlessException {
        super(title);

 //        Main frame setting

        //setLayout(new BorderLayout());
        setSize(1300,750);
        //setLocation(50,10);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2,
                size.height/2 - getHeight()/2);
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
        leftPanel.setBounds(0,0, getWidth() / 2, 500);
        leftPanel.setAlignmentX(SwingConstants.LEFT);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        //rightPanel.setBackground(Color.RED);
        //rightPanel.setBounds(getWidth()/2,0, 500, 500);
        rightPanel.setAlignmentX(SwingConstants.LEFT);


        String[] cols = {"No.","Date","Customer","Total"};
        String[][] data = {
                {"1","555","Ali","222"},
                {"2","555","Ali","222"},
                {"3","555","Ali","222"},
                {"4","555","Ali","222"}
        };

//        Left Panel Content
        invTable = new JTable(data , cols);
        JTableHeader tableHeader = invTable.getTableHeader();
        tableHeader.setBackground(Color.WHITE);

        JScrollPane tableSP = new JScrollPane(invTable);
        tableSP.setPreferredSize(new Dimension(getWidth() / 2 - 20, getHeight() - 200));
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
        createBtn.addActionListener(this);

        deleteBtn = new JButton("Delete Invoice");
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

        JPanel rightPanelInvData = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        rightPanelInvData.setPreferredSize(new Dimension(getWidth() /2-50,190));

//        Invoice Number
        JLabel invNoLbl = new JLabel("Invoice Number");
        gbc.insets = new Insets(20,0,0,0);
        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanelInvData.add(invNoLbl,gbc);

        JLabel invNo = new JLabel("23");
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

        JLabel invTotal = new JLabel("254.70");
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 3;
        rightPanelInvData.add(invTotal,gbc);



        rightPanel.add(rightPanelInvData);







        container.setLayout(new GridLayout(1,2));
        container.add(leftPanel);
        container.add(rightPanel);

        add(container);



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
