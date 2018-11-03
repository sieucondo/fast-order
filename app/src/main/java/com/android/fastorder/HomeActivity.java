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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.ProductAdapter;
import com.android.fastorder.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Product;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView listViewProduct;
    private ProductAdapter productAdapter;
    private List<Product> productCartList;
    private ArrayList<String> listDrink = new ArrayList<String>() ;
    private ArrayList<Integer> listPriceDrink = new ArrayList<Integer>();

    private ArrayList<String> listFood = new ArrayList<String>() ;
    private ArrayList<Integer> listPriceFood = new ArrayList<Integer>();


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
                        // For example, swa/p UI fragments here

                        return true;
                    }
                });

//        mDrawerLayout.addDrawerListener(
//                new DrawerLayout.DrawerListener() {
//                    @Override
//                    public void onDrawerSlide(View drawerView, float slideOffset) {
//                        // Respond when the drawer's position changes
//                    }
//
//                    @Override
//                    public void onDrawerOpened(View drawerView) {
//                        // Respond when the drawer is opened
//                    }
//
//                    @Override
//                    public void onDrawerClosed(View drawerView) {
//                        // Respond when the drawer is closed
//                    }
//
//                    @Override
//                    public void onDrawerStateChanged(int newState) {
//                        // Respond when the drawer motion state changes
//                    }
//                }
//        );

        listViewProduct = findViewById(R.id.listProduct);
        List<Product> listProduct = getListData("All");
        productAdapter = new ProductAdapter(this, listProduct);
        listViewProduct.setAdapter(productAdapter);

        //lấy đồ uống từ api
        String URL = "http://192.168.1.10:3000/products2/SD0001F01T01&1";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest objectRequest =new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i<response.length();i++){
                            try {
                                JSONObject js = response.getJSONObject(i);
//                                Log.e("Rest Response",String.valueOf(a));
                                String name = js.getString("ProductName");
                                Integer price = js.getInt("ProductPrice");
                                listDrink.add(name);
                                listPriceDrink.add(price);
                                Log.e("Rest Response",String.valueOf(js));
                                Log.e("Rest Response",name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Rest Response",error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
        Log.e("After Response",String.valueOf(listDrink));
        Log.e("After Response",String.valueOf(listPriceDrink));


        // lấy đồ ăn từ api
        String URL1 = "http://192.168.1.10:3000/products2/SF0001F01T01&2";
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        JsonArrayRequest objectRequest1 =new JsonArrayRequest(
                Request.Method.GET,
                URL1,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i<response.length();i++){
                            try {
                                JSONObject js = response.getJSONObject(i);
//                                Log.e("Rest Response",String.valueOf(a));
                                String name = js.getString("ProductName");

                                Integer price = js.getInt("ProductPrice");

                                listFood.add(name);
                                listPriceFood.add(price);
//                                Log.e("Rest Response",String.valueOf(a));
                                Log.e("Rest1 Response",name);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Rest Response",error.toString());
                    }
                }
        );
        requestQueue1.add(objectRequest1);
        Log.e("After Response",String.valueOf(listFood));

    }

    //Dùng api để load món theo category
    private List<Product> getListData(String type) {
        String productName = "";







        List<Product> list = new ArrayList<>();
        switch (type) {
            case "Food":
                for (int i = 0; i < listFood.size(); i++) {
                    list.add(new Product(i, i,  String.valueOf(listFood.get(i)),Integer.valueOf(listPriceFood.get(i)), 0));
                }
                break;
            case "Drink":
                for (int i = 0; i < listDrink.size(); i++) {

                    list.add(new Product(i, i,String.valueOf(listDrink.get(i)), Integer.valueOf(listPriceDrink.get(i)), 0));
                }
                break;
            default:
                for (int i = 0; i < 50; i++) {

                    list.add(new Product(i, i, "Product" + i, 999, 0));
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
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Them menu vao actionBar
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    public void clickToViewCart(MenuItem item) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("PRODUCT", (Serializable) productCartList);
        startActivity(intent);
    }

    public void clickToAddCart(View view) {
        View parentRow = (View) view.getParent();
        ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        Product selectedProduct = (Product) listViewProduct.getItemAtPosition(position);
        productCartList.add(selectedProduct);
        Toast.makeText(HomeActivity.this, "Added :" + " " + selectedProduct.getProductName() + " Successful!", Toast.LENGTH_SHORT).show();
    }
}
