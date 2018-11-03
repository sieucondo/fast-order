package model;

public class Product {
    private int id;
    private int storeId;
    private int imageId;
    private String productName;
    private int price;
    private int category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Product(int id, int imageId, String productName, int price, int category) {
        this.id = id;
        this.imageId = imageId;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public Product() {
    }

}