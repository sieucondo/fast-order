package model;

public class Product {
    private int id;
    private int imageId;
    private String productName;
    private double price;

    public Product(int id, int imageId, String productName, double price) {
        this.id = id;
        this.imageId = imageId;
        this.productName = productName;
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
