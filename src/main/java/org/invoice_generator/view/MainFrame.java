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
    public MainFrame(String title) throws HeadlessException {
        super(title);

 //        Main frame setting

        //setLayout(new BorderLayout());
        setSize(1400,800);
        setLocation(50,10);
        System.out.println(getWidth());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //leftPanel.setBackground(Color.BLUE);
        leftPanel.setBounds(0,0, getWidth() / 2, getHeight());
        leftPanel.setAlignmentX(SwingConstants.LEFT);
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.RED);
        rightPanel.setBounds(getWidth()/2,0, getWidth() / 2, getHeight());

        JButton createBtn;
        JButton deleteBtn;
        JButton saveBtn;
        JButton cancelBtn;


     
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

        EmptyBorder labelMargin = new EmptyBorder(20,10,10,0);
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

        add(leftPanel);
        add(rightPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
