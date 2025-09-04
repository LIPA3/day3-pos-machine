package utils;

import java.util.List;

public class Receipt {
    public List<ReceiptItem> receiptItems;
    public int totalPrice;

    public Receipt(List<ReceiptItem> receiptItems, int totalPrice) {
        this.receiptItems = receiptItems;
        this.totalPrice = totalPrice;
    }
}
