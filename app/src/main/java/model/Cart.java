package model;

import java.util.LinkedList;
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


    }

    public void setCartInfo(){
        int totalQuantity = 0;
        int totalPrice = 0;
        if(this.listProduct != null){
            for (CartItem p: listProduct) {
                totalQuantity += p.getQuantity();
                totalPrice += p.getTotalPrices();
            }
        }

        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public void clear(){
        setListProduct(new LinkedList<CartItem>());
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
