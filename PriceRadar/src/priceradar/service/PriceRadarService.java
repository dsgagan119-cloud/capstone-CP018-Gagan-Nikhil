package priceradar.service;

import priceradar.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceRadarService {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p) {
        products.add(p);
    }

    public Product find(String name) {
        for (Product p : products)
            if (p.getName().equalsIgnoreCase(name))
                return p;
        return null;
    }

    public void showCheapest(Product p) {
        SellerOffer best = p.getOffers().get(0);

        for (SellerOffer o : p.getOffers())
            if (o.totalPrice() < best.totalPrice())
                best = o;

        System.out.println("\nCheapest seller: " + best.getSeller());
        System.out.println("Product price: ₹" + best.getPrice());
        System.out.println("Delivery: ₹" + best.getDeliveryCharge());
        System.out.println("Total: ₹" + best.totalPrice());
        System.out.println("Delivery days: " + best.getDeliveryDays());
    }

    public boolean updateOffer(Product p, String seller, double price,
                               double delivery, int days) {
        for (SellerOffer o : p.getOffers()) {
            if (o.getSeller().equalsIgnoreCase(seller)) {
                o.update(price, delivery, days);
                p.getHistory().add(new PriceRecord(seller, o.totalPrice()));
                checkAlert(p);
                return true;
            }
        }
        return false;
    }

    public void showHistory(Product p) {
        System.out.println("\n--- PRICE HISTORY ---");
        double lowest = Double.MAX_VALUE;

        for (PriceRecord r : p.getHistory()) {
            System.out.println(r.getSeller() + " | ₹" +
                    r.getTotalPrice() + " | " + r.getTime());

            lowest = Math.min(lowest, r.getTotalPrice());
        }

        System.out.println("Lowest price ever: ₹" + lowest);
    }

    public void checkAlert(Product p) {
        if (p.getTargetPrice() < 0) return;

        for (SellerOffer o : p.getOffers()) {
            if (o.totalPrice() <= p.getTargetPrice()) {
                System.out.println("ALERT: " + o.getSeller() +
                        " reached target price ₹" + o.totalPrice());
            }
        }
    }

    public void search(String word) {
        boolean found = false;
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(word.toLowerCase())) {
                System.out.println(p.getName());
                found = true;
            }
        }
        if (!found) System.out.println("No products found.");
    }

    public void sort(int choice) {
        if (choice == 1)
            products.sort(Comparator.comparing(Product::getName));
        else if (choice == 2)
            products.sort(Comparator.comparing(Product::lowestTotalPrice));
        else if (choice == 3)
            products.sort(Comparator.comparingInt(
                    p -> p.getOffers().stream()
                            .mapToInt(SellerOffer::getDeliveryDays)
                            .min().orElse(0)));
        else {
            System.out.println("Invalid sort choice.");
            return;
        }

        System.out.println("\n--- SORTED PRODUCTS ---");
        for (Product p : products)
            System.out.println(p.getName() + " | ₹" +
                    p.lowestTotalPrice());
    }
}
