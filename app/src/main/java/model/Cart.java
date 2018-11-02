package model;

import java.util.List;

public class Cart {
   private List<CartItem> listProduct;
   private int totalQuantity;
   private int totalPrice;

    public Cart(List<CartItem> listProduct) {
        this.listProduct = listProduct;
    }

    public List<CartItem> getListProduct() {

        return listProduct;
    }

    public void setListProduct(List<CartItem> listProduct) {
        this.listProduct = listProduct;
        int totalQuantity = 0;
        int totalPrice = 0;
        for (CartItem p: listProduct) {
            totalQuantity += p.getQuantity();
            totalPrice += p.getTotalPrices();
        }
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;

    }

    public int getTotalQuantity() {


        return totalQuantity;
    }


    public int getTotalPrice() {

        return totalPrice;
    }


}
