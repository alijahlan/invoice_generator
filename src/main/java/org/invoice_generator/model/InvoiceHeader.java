package org.invoice_generator.model;

import java.util.Date;

public class InvoiceHeader {
    int invoiceNumber;
    Date invoiceDate;
    String customerName;
    double invoiceTotal;
    InvoiceLine [] InvoiceLines ;

    private final String[] columnNames = {"No.", "Date", "Customer", "Total"};

}
