package priceradar.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private double targetPrice = -1;
    private List<SellerOffer> offers = new ArrayList<>();
    private List<PriceRecord> history = new ArrayList<>();

    public Product(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public double getTargetPrice() { return targetPrice; }
    public List<SellerOffer> getOffers() { return offers; }
    public List<PriceRecord> getHistory() { return history; }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public void addOffer(SellerOffer offer) {
        offers.add(offer);
        history.add(new PriceRecord(offer.getSeller(), offer.totalPrice()));
    }

    public double lowestTotalPrice() {
        double min = Double.MAX_VALUE;
        for (SellerOffer o : offers)
            min = Math.min(min, o.totalPrice());
        return min;
    }
}
