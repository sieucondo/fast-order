package com.android.fastorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.TimeoutError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                                reloadAllData(getListData(2));
                                break;
                            case R.id.nav_drink:
                                reloadAllData(getListData(1));
                                break;
                            case R.id.nav_sale:
                                reloadAllData(getListData(0));
                                break;
                        }
                        return true;
                    }
                });

        listViewProduct = findViewById(R.id.listProduct);
        List<Product> listProduct = getListData(0);
        productAdapter = new ProductAdapter(this, listProduct);
        listViewProduct.setAdapter(productAdapter);
    }


    //Dùng api để load món theo category
    private List<Product> getListData(int type) {
        String tableKey = "SD0001F01T01";
        final List<Product> list = new ArrayList<>();
        String URL = "http://192.168.202.1:3000/products-type/SD0001F01T01&1";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject js = response.getJSONObject(i);
                                Log.e("Rest Response 2",String.valueOf(js));
                                Product product = new Product(
                                        js.getInt("id"),
                                        js.getInt("id"),
                                        js.getString("ProductName"),
                                        js.getInt("ProductPrice")
                                );
                               list.add(product);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError ) {
                            // Is thrown if there's no network connection or server is down
                            Log.e("Error" , "Time out");
                            // We return to the last fragment
                            if (getFragmentManager().getBackStackEntryCount() != 0) {
                                getFragmentManager().popBackStack();
                            }
                        } else if (error instanceof NoConnectionError) {
                            // Is thrown if there's no network connection or server is down
                            Toast.makeText(HomeActivity.this, "No connection",
                                    Toast.LENGTH_LONG).show();
                            // We return to the last fragment
                            if (getFragmentManager().getBackStackEntryCount() != 0) {
                                getFragmentManager().popBackStack();
                            }
                        }
                    }
                }
        );
        objectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(objectRequest);
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