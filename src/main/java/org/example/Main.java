import utils.Product;
import utils.Receipt;
import utils.ReceiptProduct;

import java.util.*;

public class Main {
    public static List<Product> loadAllProducts() {
        return Arrays.asList(
                new Product("ITEM000000", "Coca-Cola", 3),
                new Product("ITEM000001", "Sprite", 3),
                new Product("ITEM000004", "Battery", 2)
        );
    }
    public static List<Product> decodeToProducts(List<String> barcodes) {
        List<Product> allProducts = loadAllProducts();
        Map<String, Product> productMap = new HashMap<>();
        for (Product product : allProducts) {
            productMap.put(product.barcode, product);
        }

        List<Product> products = new ArrayList<>();
        for (String barcode : barcodes) {
            products.add(productMap.get(barcode));
        }
        return products;
    }
    public static List<ReceiptProduct> calculateProductsCost(List<Product> products) {
        Map<String, Integer> productCountMap = new HashMap<>();
        for (Product product : products) {
            if (productCountMap.containsKey(product.barcode)) {
                productCountMap.put(product.barcode, productCountMap.get(product.barcode) + 1);
            } else {
                productCountMap.put(product.barcode, 1);
            }
        }

        List<ReceiptProduct> receiptProducts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            Product product = products.stream().filter(i -> i.barcode.equals(entry.getKey())).findFirst().get();
            receiptProducts.add(new ReceiptProduct(product.name, entry.getValue(), product.price, entry.getValue() * product.price));
        }
        return receiptProducts;
    }
    public static int calculateTotalPrice(List<ReceiptProduct> receiptItems) {
        return receiptItems.stream().mapToInt(item -> item.subTotal).sum();
    }
    public static String printReceipt(List<String> barcodes) {
        List<Product> products = decodeToProducts(barcodes);
        List<ReceiptProduct> receiptProducts = calculateProductsCost(products);
        int totalPrice = calculateTotalPrice(receiptProducts);

        Receipt receipt = new Receipt(receiptProducts, totalPrice);

        return renderReceipt(receipt);
    }

    public static String renderReceipt(Receipt receipt) {
        StringBuilder receiptString = new StringBuilder();
        receiptString.append("***<store earning no money>Receipt***\n");

        for (ReceiptProduct product : receipt.receiptProducts) {
            receiptString.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    product.name, product.quantity, product.unitPrice, product.subTotal));
        }

        receiptString.append("----------------------\n");
        receiptString.append(String.format("Total: %d (yuan)\n", receipt.totalPrice));
        receiptString.append("**********************");

        return receiptString.toString();
    }
    public static void main(String[] args) {
        List<String> barcodeList = Arrays.asList(
                "ITEM000000",
                "ITEM000000",
                "ITEM000000",
                "ITEM000000",
                "ITEM000001",
                "ITEM000001",
                "ITEM000004",
                "ITEM000004",
                "ITEM000004"
        );

        System.out.println(printReceipt(barcodeList));

    }
}