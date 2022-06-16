package org.invoice_generator.model;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class FileOperations {
    public static ArrayList<String[]> readFile(File DataFile, Component parent){

        final ArrayList<String[]> data = new ArrayList<> ();
        //DataFile = new File("src/main/java/dataFiles/InvoiceHeader.csv");
        String[] OneRow;
        try {
            BufferedReader brd = new BufferedReader(new FileReader(DataFile));
            while (brd.ready()) {
                String st = brd.readLine();
                OneRow = st.split(",|\\s|;");

                data.add(OneRow);
                //System.out.println(Arrays.toString(OneRow));
            } // end of while
        } catch (FileNotFoundException e){

        } catch (ArrayIndexOutOfBoundsException | IOException e){
            String errMsg = e.getMessage();
            JOptionPane.showMessageDialog(parent,"File format not acceptable : " + errMsg);
        }
        // end of Catch

        return data;
    }

    void writeFiles(InvoiceHeader[] data){

    }


    public static void writeFiles(String invFilePath,String itemsFilePath, ArrayList<String[]> invTable,ArrayList<String[]> invItems )
    {

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(invFilePath);
        File file2 = new File(itemsFilePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                                             CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);

            FileWriter outputFile2 = new FileWriter(file2);
            CSVWriter writer2 = new CSVWriter(outputFile2, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);

            writer.writeAll(invTable);
            writer2.writeAll(invItems);

            // closing writer connection
            writer.close();
            writer2.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }
}
