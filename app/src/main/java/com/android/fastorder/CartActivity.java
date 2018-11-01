package com.android.fastorder;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import adapter.ProductCartAdapter;
import model.Product;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar cartToolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(cartToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        List<Product> listProduct = getListData();
        ListView listViewProduct =  findViewById(R.id.listProductCart);
        ProductCartAdapter productCartAdapter = new ProductCartAdapter(this, listProduct);
        listViewProduct.setAdapter(productCartAdapter);

    }

    private List<Product> getListData() {
        List<Product> list = new ArrayList<>();

        Intent intent = getIntent();
        if(intent.getSerializableExtra("PRODUCT") != null){
            list = (List<Product>) intent.getSerializableExtra("PRODUCT");
        }
        return list;
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
}
