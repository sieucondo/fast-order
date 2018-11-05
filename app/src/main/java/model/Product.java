package model;


import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String imageUrl;
    private String productName;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Product(int id, String imageUrl, String productName, int price) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.id == (((Product)obj).getId())) {
            return true;
        }

        return false;
    }
}
