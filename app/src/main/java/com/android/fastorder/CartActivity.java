package com.android.fastorder;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import adapter.CartAdapter;
import model.Cart;
import model.CartItem;
import util.ProductCart;

public class CartActivity extends AppCompatActivity {
    CartAdapter productCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar cartToolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(cartToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        ListView listViewProduct =  findViewById(R.id.listProductCart);
        productCartAdapter = new CartAdapter(getListData(), this);
        listViewProduct.setAdapter(productCartAdapter);

        updateOrderInfo();
    }

    private List<CartItem> getListData() {
        Cart cart = ProductCart.getCart();
        List<CartItem> listProducts = cart.getListProduct();
        return listProducts;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickToOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void clickToRemove(View view){
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);

        productCartAdapter.getListCart().remove(position);

        Cart cart = ProductCart.getCart();
        cart.setListProduct(productCartAdapter.getListCart());
        productCartAdapter.notifyDataSetChanged();

        updateOrderInfo();
    }

    public void updateOrderInfo(){
        NumberFormat formatter = new DecimalFormat("#,###");
        TextView txtTotalItem = findViewById(R.id.txtTotalProduct);
        TextView txtTotalPrice = findViewById(R.id.txtTotalPrice);

        Cart cart = ProductCart.getCart();

        String product;
         if(cart.getTotalQuantity() > 1){
             product = "Products";
         } else{
             product = "Product";
         }

        txtTotalItem.setText(cart.getTotalQuantity() + " " + product);
        txtTotalPrice.setText(formatter.format(cart.getTotalPrice()) + " đ");
    }

}
