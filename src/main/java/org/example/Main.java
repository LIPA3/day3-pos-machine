import utils.Item;
import utils.Receipt;
import utils.ReceiptItem;

import java.util.*;

public class Main {
    public static List<Item> loadAllItems() {
        return Arrays.asList(
                new Item("ITEM000000", "Coca-Cola", 3),
                new Item("ITEM000001", "Sprite", 3),
                new Item("ITEM000004", "Battery", 2)
        );
    }
    public static List<Item> decodeToItems(List<String> barcodes) {
        List<Item> allItems = loadAllItems();
        Map<String, Item> itemMap = new LinkedHashMap<>();
        if(barcodes == null || barcodes.size() == 0) {
            throw new IllegalArgumentException("Barcodes list cannot be null or empty");
        }
        for (Item item : allItems) {
            itemMap.put(item.barcode, item);
        }
        List<Item> items = new ArrayList<>();
        for (String barcode : barcodes) {
            items.add(itemMap.get(barcode));
        }
        return items;
    }
    public static List<ReceiptItem> calculateItemsCost(List<Item> items) {
        Map<String, Integer> itemCountMap = new HashMap<>();
        for (Item item : items) {
            if (itemCountMap.containsKey(item.barcode)) {
                itemCountMap.put(item.barcode, itemCountMap.get(item.barcode) + 1);
            } else {
                itemCountMap.put(item.barcode, 1);
            }
        }

        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
            Item item = items.stream().filter(i -> i.barcode.equals(entry.getKey())).findFirst().get();
            receiptItems.add(new ReceiptItem(item.name, entry.getValue(), item.price, entry.getValue() * item.price));
        }
        return receiptItems;
    }
    public static int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return receiptItems.stream().mapToInt(item -> item.subTotal).sum();
    }
    public static String printReceipt(List<String> barcodes) {
        List<Item> items = decodeToItems(barcodes);
        List<ReceiptItem> receiptItems = calculateItemsCost(items);
        int totalPrice = calculateTotalPrice(receiptItems);

        Receipt receipt = new Receipt(receiptItems, totalPrice);

        return renderReceipt(receipt);
    }

    public static String renderReceipt(Receipt receipt) {
        StringBuilder receiptString = new StringBuilder();
        receiptString.append("***<store earning no money>Receipt***\n");

        for (ReceiptItem item : receipt.receiptItems) {
            receiptString.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    item.name, item.quantity, item.unitPrice, item.subTotal));
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