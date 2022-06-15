package org.invoice_generator.model;

public class InvoiceLine {
    String itemName;
    double itemPrice;

    public InvoiceLine(String itemName, double itemPrice, int count) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;
}
