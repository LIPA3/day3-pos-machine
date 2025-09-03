package utils;

import java.util.List;

public class Receipt {
    public List<ReceiptProduct> receiptProducts;
    public int totalPrice;

    public Receipt(List<ReceiptProduct> receiptProducts, int totalPrice) {
        this.receiptProducts = receiptProducts;
        this.totalPrice = totalPrice;
    }
}
