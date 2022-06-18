package org.invoice_generator;

import org.invoice_generator.model.FileOperations;
import org.invoice_generator.view.MainFrame;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        MainFrame mainFrame = new MainFrame("Design Preview [NewJFrame");
        mainFrame.setVisible(true);

        final File invTablePath = new File("src/main/java/dataFiles/InvoiceHeader.csv");
        final File invTableItemsPath = new File("src/main/java/dataFiles/InvoiceLine.csv");

        final String reportPath = String.valueOf(new File("Report.txt"));
//        Print all invoices with items on the console
        ArrayList<String> report = test(invTablePath,invTableItemsPath,mainFrame );

        createFile(reportPath, report);
        for ( String row : report) {
            System.out.println(row);
        }
    }


    static  ArrayList<String> test(File invFilePath, File itemsFilePath, Component component){

        ArrayList<String[]> invFileData = new ArrayList<>();
        ArrayList<String[]> itemsFileData = new ArrayList<>();

        invFileData = FileOperations.readFile(invFilePath, component);
        itemsFileData = FileOperations.readFile(itemsFilePath, component);

        ArrayList<String> report = new ArrayList<>();
        int index=0;
        for ( String[] invoice: invFileData) {

            report.add(index++,"Invoice: "+ invoice[0]);
            report.add(index++,"{");
            report.add(index++,"Date: "+ invoice[1] + ", Customer name: "+ invoice[2]);

            for ( String[] item: itemsFileData) {
                if (item[1].equals(invoice[0])){
                    report.add(index++,item[2]+", "+item[3]+", "+item[4]);

                }
            }

            report.add(index++,"}\n");

            System.out.println("");
        }
        return report;
    }

    private static void createFile(String file, ArrayList<String> arrData)
            throws IOException {
        FileWriter writer = new FileWriter(file);
        int size = arrData.size();
        for (int i=0;i<size;i++) {
            String str = arrData.get(i).toString();
            writer.write(str);
            if(i < size-1) writer.write("\n");
        }
        writer.close();
    }

}
