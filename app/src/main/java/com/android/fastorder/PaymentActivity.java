package com.android.fastorder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import adapter.CartAdapter;
import adapter.PaymentAdapter;
import model.Cart;
import model.CartItem;
import model.Product;
import util.ProductCart;
import util.ProductPayment;


public class PaymentActivity extends AppCompatActivity {

    PaymentAdapter paymentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar cartToolbar = findViewById(R.id.orderToolbar);
        setSupportActionBar(cartToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);


        ListView listViewProduct = findViewById(R.id.listItemPayment);//Tao listView
        paymentAdapter = new PaymentAdapter(getListData(), this);//Truyen list vao adapter.
        listViewProduct.setAdapter(paymentAdapter);//Truyen adapter vao listView
        updatePaymentInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<CartItem> getListData() {
        Cart cart = ProductPayment.getBill();
        List<CartItem> listProducts = cart.getListProduct();
        return listProducts;
    }

    public void updatePaymentInfo() {
        NumberFormat formatter = new DecimalFormat("#,###");
        TextView txtTotalItem = findViewById(R.id.txtPaymentTotalQuantity);
        TextView txtTotalPrice = findViewById(R.id.txtPaymentTotalPrice);
        Cart cart = ProductPayment.getBill();
        String txtProduct;
        if (cart.getTotalQuantity() > 1) {
            txtProduct = "Products";
        } else {
            txtProduct = "Product";
        }
        txtTotalItem.setText(cart.getTotalQuantity() + " " + txtProduct);
        txtTotalPrice.setText(formatter.format(cart.getTotalPrice()) + " đ");
    }
}
