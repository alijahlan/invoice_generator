package org.invoice_generator.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewInvoiceDialog extends JDialog {
    String invNo;
    String invDate;
    String invCustomerName;
    public String invTotal="0";
    String[] invoiceData;


    JLabel invNoLbl = new JLabel();
    JTextField invDateJTF = new JTextField("dd-mm-yyyy");
    JTextField customerNameJTF = new JTextField();
    JButton okBtn = new JButton("Save");
    JButton cancelBtn = new JButton("Cancel");
    JPanel dialogPanel;
    public NewInvoiceDialog(final Frame owner, String title, final int invNo) {
        super(owner, title);
        invNoLbl.setText(String.valueOf(invNo));
        //invDateJTF.setForeground(Color.GRAY);
        setInvNo(String.valueOf(invNo));


        dialogPanel = new JPanel();
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialogPanel.setPreferredSize(new Dimension(470,195));
        dialogPanel.setLayout(new GridBagLayout());

        setBounds(300,100, 500,230 );
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        setModal (true);
        setAlwaysOnTop (true);
        setModalityType (ModalityType.APPLICATION_MODAL);
        setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;


        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(new Label("Invoice Number"),gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialogPanel.add(invNoLbl,gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(new Label("Invoice Date \"dd-mm-yyyy\""),gbc);

        gbc.ipady = 8;
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialogPanel.add(invDateJTF,gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogPanel.add(new Label("Customer name"),gbc);

        gbc.ipady = 8;
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 2;
        dialogPanel.add(customerNameJTF,gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;

        dialogPanel.add(btnPanel,gbc);

        add(dialogPanel);

        okBtn.setEnabled(false);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setInvDate(invDateJTF.getText());
                setInvCustomerName(customerNameJTF.getText());
                NewInvoiceDialog.this.setVisible(false);

                String[] inv= {String.valueOf(invNo),invDateJTF.getText(),customerNameJTF.getText(),invTotal};
                setInvoiceData(inv);

            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setInvDate("dd-mm-yyyy");
                setInvCustomerName("");
                NewInvoiceDialog.this.setVisible(false);
            }
        });


        invDateJTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                invDateJTF.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String vDateStr = invDateJTF.getText();
                DateFormat formatter;
                formatter = new SimpleDateFormat("dd-mm-YYYY");
                Date dateTemp = null;
                try {
                    dateTemp = (Date) formatter.parse(vDateStr);
                } catch (ParseException ex) {

                }

                if (dateTemp == null) {
                    //JOptionPane.showMessageDialog(owner, "Accepted date format ( dd-mm-yyyy )", "Date format not valid!!", JOptionPane.ERROR_MESSAGE);
                    //setVisible(true);
                    invDateJTF.grabFocus();
                    invDateJTF.setText("dd-mm-yyyy");
                    okBtn.setEnabled(false);
                } else {
                    okBtn.setEnabled(true);
                }

            }
        });
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getInvCustomerName() {
        return invCustomerName;
    }

    public void setInvCustomerName(String invCustomerName) {
        this.invCustomerName = invCustomerName;
    }

    public String[] getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(String[] invoiceData) {
        this.invoiceData = invoiceData;
    }
}
