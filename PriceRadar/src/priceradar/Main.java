package priceradar;

import priceradar.model.Product;
import priceradar.model.SellerOffer;
import priceradar.service.PriceRadarService;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static PriceRadarService radar = new PriceRadarService();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== PRICERADAR =====");
            System.out.println("1. Add Product");
            System.out.println("2. Show Cheapest Price");
            System.out.println("3. Update Seller Price");
            System.out.println("4. Price History");
            System.out.println("5. Set Target Price");
            System.out.println("6. Search Products");
            System.out.println("7. Sort Products");
            System.out.println("8. Exit");

            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> showCheapest();
                case 3 -> updatePrice();
                case 4 -> history();
                case 5 -> targetPrice();
                case 6 -> search();
                case 7 -> sort();
                case 8 -> System.out.println("Thank you for using PriceRadar!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 8);
    }

    static void addProduct() {
        String name = readText("Product name: ");
        Product p = new Product(name);

        System.out.println("Enter 3 seller offers:");
        for (int i = 1; i <= 3; i++) {
            String seller = readText("Seller " + i + " name: ");
            double price = readDouble("Price: ");
            double delivery = readDouble("Delivery charge: ");
            int days = readInt("Delivery days: ");
            p.addOffer(new SellerOffer(seller, price, delivery, days));
        }

        radar.addProduct(p);
        System.out.println("Product added successfully.");
    }

    static void showCheapest() {
        Product p = findProduct();
        if (p != null) radar.showCheapest(p);
    }

    static void updatePrice() {
        Product p = findProduct();
        if (p == null) return;

        String seller = readText("Seller name: ");
        double price = readDouble("New price: ");
        double delivery = readDouble("New delivery charge: ");
        int days = readInt("New delivery days: ");

        if (radar.updateOffer(p, seller, price, delivery, days))
            System.out.println("Price updated.");
        else
            System.out.println("Seller not found.");
    }

    static void history() {
        Product p = findProduct();
        if (p != null) radar.showHistory(p);
    }

    static void targetPrice() {
        Product p = findProduct();
        if (p == null) return;

        double target = readDouble("Target total price: ");
        p.setTargetPrice(target);
        radar.checkAlert(p);
    }

    static void search() {
        String word = readText("Search name: ");
        radar.search(word);
    }

    static void sort() {
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Delivery days");
        int choice = readInt("Sort by: ");
        radar.sort(choice);
    }

    static Product findProduct() {
        String name = readText("Product name: ");
        Product p = radar.find(name);
        if (p == null) System.out.println("Product not found.");
        return p;
    }

    static String readText(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    static double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid price.");
            }
        }
    }
}
