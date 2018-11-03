package com.android.fastorder;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import adapter.PaymentAdapter;
import adapter.ProductAdapter;
import model.Cart;
import model.CartItem;
import model.Product;
import util.ProductCart;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView listViewProduct;
    private ProductAdapter productAdapter;
    private List<Product> productCartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        productCartList = new ArrayList<>();
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        switch (menuItem.getItemId()) {
                            case R.id.nav_food:
                                reloadAllData(getListData("Food"));
                                break;
                            case R.id.nav_drink:
                                reloadAllData(getListData("Drink"));
                                break;
                            case R.id.nav_sale:
                                reloadAllData(getListData("Sale"));
                                break;
                        }
                        return true;
                    }
                });

        listViewProduct = findViewById(R.id.listProduct);
        List<Product> listProduct = getListData("All");
        productAdapter = new ProductAdapter(this, listProduct);
        listViewProduct.setAdapter(productAdapter);
    }


    //Dùng api để load món theo category
    private List<Product> getListData(String type) {

        List<Product> list = new ArrayList<>();
        switch (type) {
            case "Food":
                for (int i = 0; i < 50; i++) {
                    list.add(new Product(i, i, "Food_" + i, 1000));
                }
                break;
            case "Drink":
                for (int i = 50; i < 100; i++) {

                    list.add(new Product(i, i, "Drink_" + i, 2000));
                }
                break;
            default:
                for (int i = 100; i < 150; i++) {

                    list.add(new Product(i, i, "Product_" + i, 999));
                }
        }
        return list;
    }

    private void reloadAllData(List<Product> newProductList) {
        productAdapter.getListData().clear();
        productAdapter.setListData(newProductList);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.nav_cart  :
                intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_bill  :
                intent = new Intent(this, PaymentActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    public void clickToAddCart(View view) {
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        Product selectedProduct = (Product) listViewProduct.getItemAtPosition(position);

        Cart cart = ProductCart.getCart();

        CartItem product = new CartItem(selectedProduct, 1);

        List<CartItem> listProduct = cart.getListProduct();

        if (listProduct.contains(product)) {
            int index = listProduct.indexOf(product);
            int quantity = listProduct.get(index).getQuantity();
            quantity += 1;
            listProduct.get(index).setQuantity(quantity);
        } else {
            listProduct.add(product);
        }
        cart.setCartInfo();
        Toast.makeText(HomeActivity.this, "Added :" + " " + selectedProduct.getProductName() + " Successful!", Toast.LENGTH_SHORT).show();
    }
}