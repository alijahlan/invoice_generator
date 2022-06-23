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
public class App {
    public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame("Design Preview [NewJFrame");
        mainFrame.setVisible(true);


        final String reportPath = String.valueOf(new File("Report.txt"));
//        Print all invoices with items on the console
        ArrayList<String> report = FileOperations.test(mainFrame);

        createFile(reportPath, report);
        for (String row : report) {
            System.out.println(row);
        }
    }


    private static void createFile(String file, ArrayList<String> arrData)
            throws IOException {
        FileWriter writer = new FileWriter(file);
        int size = arrData.size();
        for (int i = 0; i < size; i++) {
            String str = arrData.get(i).toString();
            writer.write(str);
            if (i < size - 1) writer.write("\n");
        }
        writer.close();
    }

}



