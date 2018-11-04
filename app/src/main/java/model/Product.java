package model;


import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String imageId;
    private String productName;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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


    public Product(int id, String imageId, String productName, int price) {
        this.id = id;
        this.imageId = imageId;
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
