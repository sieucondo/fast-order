package com.android.fastorder;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import model.Product;

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
    }

    //Dùng api để load món theo category
    private List<Product> getListData(String type) {

        List<Product> list = new ArrayList<>();
        switch (type) {
            case "Food":
                for (int i = 0; i < 50; i++) {
                    list.add(new Product(i, i, "Food_" + i, 999, 0));
                }
                break;
            case "Drink":
                for (int i = 0; i < 50; i++) {

                    list.add(new Product(i, i, "Drink_" + i, 999, 0));
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
