package org.invoice_generator;

import org.invoice_generator.model.FileOperations;
import org.invoice_generator.view.MainFrame;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MainFrame mainFrame = new MainFrame("Design Preview [NewJFrame");
        mainFrame.setVisible(true);

        final File invTablePath = new File("src/main/java/dataFiles/InvoiceHeader.csv");
        final File invTableItemsPath = new File("src/main/java/dataFiles/InvoiceLine.csv");

//        Print all invoices with items on the console
        test(invTablePath,invTableItemsPath,mainFrame );
    }


    static  void test(File invFilePath, File itemsFilePath, Component component){

        ArrayList<String[]> invFileData = new ArrayList<>();
        ArrayList<String[]> itemsFileData = new ArrayList<>();

        invFileData = FileOperations.readFile(invFilePath, component);
        itemsFileData = FileOperations.readFile(itemsFilePath, component);

        System.out.println("");
        for ( String[] invoice: invFileData) {
            System.out.println("Invoice: "+ invoice[0]);
            System.out.println("{");
            System.out.println("Date: "+ invoice[1] + ", Customer name: "+ invoice[2]);

            for ( String[] item: itemsFileData) {
                if (item[1].equals(invoice[0])){
                    System.out.println(item[2]+", "+item[3]+", "+item[4]);
                }
            }

            System.out.println("}");
            System.out.println("");
        }
    }
}
