package model;


import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int imageId;
    private String productName;
    private int price;
    private int quantitive;

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

    public int getQuantitive() {
        return quantitive;
    }

    public void setQuantitive(int quantitive) {
        this.quantitive = quantitive;
    }

    public Product(int id, int imageId, String productName, int price, int quantitive) {
        this.id = id;
        this.imageId = imageId;
        this.productName = productName;
        this.price = price;
        this.quantitive = quantitive;
    }

//    public Product(Parcel in) {
//        id = in.readInt();
//        imageId = in.readInt();
//        productName = in.readString();
//        price = in.readInt();
//        quantitive = in.readInt();
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeInt(imageId);
//        dest.writeString(productName);
//        dest.writeInt(price);
//        dest.writeInt(quantitive);
//    }
//
//    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>()
//    {
//        public Product createFromParcel(Parcel in)
//        {
//            return new Product(in);
//        }
//        public Product[] newArray(int size)
//        {
//            return new Product[size];
//        }
//    };

}
