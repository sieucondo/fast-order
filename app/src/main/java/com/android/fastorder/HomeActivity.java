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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.ProductAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Cart;
import model.CartItem;
import model.Product;
import model.Table;
import model.Wifi;
import util.ProductCart;
import util.TableInfo;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView listViewProduct;
    private ProductAdapter productAdapter;
    private Table table = TableInfo.getTableInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(table.getStoreName());
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView txtRestaurantName = header.findViewById(R.id.txtRestaurantName);
        txtRestaurantName.setText(table.getStoreName());

        Wifi wifi = TableInfo.getWifi();
        TextView wifiName = header.findViewById(R.id.txtWifiName);
        wifiName.setText("Wi-fi Name: " + wifi.getWifiName());
        TextView wifiPass = header.findViewById(R.id.txtWifiPass);
        wifiPass.setText("Wi-fi Password: " + wifi.getWifiPassword());

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
                                getListData(2);
                                break;
                            case R.id.nav_drink:
                                getListData(1);
                                break;
                            case R.id.nav_all:
                                getListData(0);
                                break;
                        }
                        return true;
                    }
                });

        listViewProduct = findViewById(R.id.listProduct);
        productAdapter = new ProductAdapter(this, new ArrayList<Product>());
        listViewProduct.setAdapter(productAdapter);
        getListData(0);
    }

    //Dùng api để load món theo category
    private void getListData(int type) {
        String ip = getResources().getString(R.string.ip_address);
        String URL = "http://" + ip + ":3000/products-type/" + table.getTableKey() + "&" + type;
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        JsonArrayRequest objectRequest2 = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Product> productCartList = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject js = response.getJSONObject(i);
                                Product product = new Product(
                                        js.getInt("id"),
                                        js.getString("ImgPath"),
                                        js.getString("ProductName"),
                                        js.getInt("ProductPrice")
                                );
                                productCartList.add(product);

                            } catch (JSONException e) {
                                Log.e("HomeError", e.toString());
                            }
                        }
                        reloadAllData(productCartList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HomeError", error.toString());
                    }
                }
        );
        requestQueue2.add(objectRequest2);
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
            case R.id.nav_cart:
                intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_bill:
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