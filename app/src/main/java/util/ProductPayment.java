package util;

import java.util.ArrayList;

import model.Cart;
import model.CartItem;

public class ProductPayment {
    private static Cart bill = new Cart(new ArrayList<CartItem>());

    public static Cart getBill() {
        if(bill == null){
            bill = new Cart(new ArrayList<CartItem>());
        }
        return bill;
    }
}
