package org.invoice_generator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class FileOperations {
    public static ArrayList<String[]> readFile(File DataFile){

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
        } // end of try
        catch (Exception e) {
            String errMsg = e.getMessage();
            System.out.println("File not found:" + errMsg);
        } // end of Catch

        return data;
    }

    void writeFile(InvoiceHeader[] data){

    }
}
