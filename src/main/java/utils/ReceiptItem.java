package utils;

public class ReceiptItem {
    public String name;
    public int quantity;
    public int unitPrice;
    public int subTotal;

    public ReceiptItem(String name, int quantity, int unitPrice, int subTotal) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }
}
