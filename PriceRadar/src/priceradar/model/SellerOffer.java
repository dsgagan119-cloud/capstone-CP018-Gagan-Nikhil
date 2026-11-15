package priceradar.model;

public class SellerOffer {
    private String seller;
    private double price;
    private double deliveryCharge;
    private int deliveryDays;

    public SellerOffer(String seller, double price, double deliveryCharge, int deliveryDays) {
        this.seller = seller;
        this.price = price;
        this.deliveryCharge = deliveryCharge;
        this.deliveryDays = deliveryDays;
    }

    public String getSeller() { return seller; }
    public double getPrice() { return price; }
    public double getDeliveryCharge() { return deliveryCharge; }
    public int getDeliveryDays() { return deliveryDays; }

    public double totalPrice() {
        return price + deliveryCharge;
    }

    public void update(double price, double deliveryCharge, int deliveryDays) {
        this.price = price;
        this.deliveryCharge = deliveryCharge;
        this.deliveryDays = deliveryDays;
    }
}
