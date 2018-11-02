package util;

import java.util.ArrayList;

import model.Cart;
import model.CartItem;

public class ProductCart {
   private static  Cart cart = new Cart(new ArrayList<CartItem>());

    public static Cart getCart() {
        if(cart == null){
            cart = new Cart(new ArrayList<CartItem>());
        }
        return cart;
    }
}
