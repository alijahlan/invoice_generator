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

public class NewItemDialog extends JDialog {
    String itemNo;
    String itemName;
    double itemPrice;
    int itemCount;
    double itemTotal;


    public String[] itemData;
    JLabel itemNoLbl = new JLabel();
    JTextField itemNameJTF = new JTextField();
    JTextField itemPriceJTF = new JTextField();
    JTextField itemCountJTF = new JTextField();
    JLabel itemTotalLbl = new JLabel();

    JButton okBtn = new JButton("Save");
    JButton cancelBtn = new JButton("Cancel");

    JPanel dialogPanel;

    public NewItemDialog(final Frame owner, String title) {
        super(owner, title);
        itemNoLbl.setText(String.valueOf(itemNo));
        dialogPanel = new JPanel(new GridLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialogPanel.setPreferredSize(new Dimension(470, 260));
        dialogPanel.setLayout(new GridBagLayout());

        setBounds(600, 100, 500, 300);
        Container dialogContent = getContentPane();
        dialogContent.setLayout(new FlowLayout());
        setModal(true);
        //setAlwaysOnTop(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);


        add(drawFormItems());

        //okBtn.setEnabled(false);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isNum = false;
                String[] item = {String.valueOf(itemNoLbl.getText()),
                        itemNameJTF.getText(), itemPriceJTF.getText(),
                        itemCountJTF.getText(), String.valueOf(itemTotal)};

                // result1 -- Item Name
                if (itemNameJTF.getText() != null) {



                        try {
                            // result2 -- item price
                            //result2 = JOptionPane.showInputDialog("Enter item price:");
                            if (itemPriceJTF.getText() != null) {

                                Double.parseDouble(itemPriceJTF.getText());
                                if (Double.parseDouble(itemPriceJTF.getText()) == 0) {
                                    isNum = false;

                                } else {
                                    isNum = true;
                                }
                                //break;
                            }
                        } catch (NumberFormatException ee) {
                            isNum = false;

                        }

                    if (itemPriceJTF.getText() != null) {


                            try {
                                // result3 -- item count
                                //result3 = JOptionPane.showInputDialog("Enter item count:");

                                Integer.parseInt(itemCountJTF.getText());

                                isNum = true;
                                //break;
                            } catch (NumberFormatException ee) {

                                isNum = false;
                                //break;
                            }
                    }
                }


                if (isNum){

                itemData = item;

                NewItemDialog.this.setVisible(false);
                } else{
                    JOptionPane.showMessageDialog(owner,"Incorrect data");
                }

            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewItemDialog.this.setVisible(false);
            }
        });

    }

    private JPanel drawFormItems() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;


        gbc.insets = new Insets(0, 0, 0, 0);

//        gbc.weightx = 0.05;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        dialogPanel.add(new Label("Item Number"), gbc);
//
//        gbc.insets = new Insets(0, 0, 0, 0);
//        gbc.weightx = 0.95;
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        dialogPanel.add(itemNoLbl, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(new Label("Item Name"), gbc);

        gbc.ipady = 8;
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialogPanel.add(itemNameJTF, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogPanel.add(new Label("Item price"), gbc);

        gbc.ipady = 8;
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 2;
        dialogPanel.add(itemPriceJTF, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialogPanel.add(new Label("Item count"), gbc);

        gbc.ipady = 8;
        gbc.weightx = 0.95;
        gbc.gridx = 1;
        gbc.gridy = 3;
        dialogPanel.add(itemCountJTF, gbc);

//        gbc.weightx = 0.05;
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        dialogPanel.add(new Label("Total"), gbc);
//
//        gbc.insets = new Insets(0, 0, 0, 0);
//        gbc.weightx = 0.95;
//        gbc.gridx = 1;
//        gbc.gridy = 4;
//        dialogPanel.add(itemTotalLbl, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout());

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 5;

        dialogPanel.add(btnPanel, gbc);

        return dialogPanel;
    }

}
