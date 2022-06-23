package org.invoice_generator.model;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

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
                JOptionPane.showMessageDialog(parent, "Sorry, File not found, please select valid file.");
        } catch (ArrayIndexOutOfBoundsException | IOException e){
            String errMsg = e.getMessage();
            JOptionPane.showMessageDialog(parent,"File format not acceptable : " + errMsg);
        }
        // end of Catch

        return data;
    }


    public static void writeFiles(String invFilePath,String itemsFilePath, ArrayList<String[]> invTable,ArrayList<String[]> invItems, Component parent )
    {

        // first create file object for file placed at location
        // specified by filepath
        File file = new File(invFilePath);
        File file2 = new File(itemsFilePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                                             CSVWriter.NO_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);

            FileWriter outputFile2 = new FileWriter(file2);
            CSVWriter writer2 = new CSVWriter(outputFile2, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);

            writer.writeAll(invTable);
            writer2.writeAll(invItems);

            // closing writer connection
            writer.close();
            writer2.close();

        }catch (FileNotFoundException ee){
            JOptionPane.showMessageDialog(parent, "Sorry, File not found, please select valid file.");
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }


    public static  ArrayList<String> test(Component component){

        final File invTablePath = new File("src/main/java/dataFiles/InvoiceHeader.csv");
        final File invTableItemsPath = new File("src/main/java/dataFiles/InvoiceLine.csv");

        ArrayList<String[]> invFileData = new ArrayList<>();
        ArrayList<String[]> itemsFileData = new ArrayList<>();

        invFileData = FileOperations.readFile(invTablePath, component);
        itemsFileData = FileOperations.readFile(invTableItemsPath, component);

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

    public  static ArrayList<String[]> handleWhiteSpace(ArrayList<String[]> readInvFile){
                ArrayList<String[]> dataFile = new ArrayList<>();
                String invoice[] = null;
        for ( int iRow=0; iRow < readInvFile.size(); iRow++) {
                invoice = readInvFile.get(iRow);
            if (invoice[2].contains("-")) {
                invoice[2] = invoice[2].replace("-", " ");

            }
            dataFile.set(iRow, invoice);
        }
return dataFile;
    }


}
