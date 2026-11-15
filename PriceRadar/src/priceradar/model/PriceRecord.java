package priceradar.model;

import java.time.LocalDateTime;

public class PriceRecord {
    private String seller;
    private double totalPrice;
    private LocalDateTime time;

    public PriceRecord(String seller, double totalPrice) {
        this.seller = seller;
        this.totalPrice = totalPrice;
        this.time = LocalDateTime.now();
    }

    public String getSeller() { return seller; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getTime() { return time; }
}
