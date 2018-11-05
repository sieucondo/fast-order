package com.android.fastorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import adapter.CartAdapter;
import adapter.PaymentAdapter;
import model.Cart;
import model.CartItem;
import model.Product;
import model.Table;
import util.ProductCart;
import util.ProductPayment;
import util.TableInfo;


public class PaymentActivity extends AppCompatActivity {

    PaymentAdapter paymentAdapter;
    private Table table = TableInfo.getTableInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        TextView txtTable = findViewById(R.id.txtPaymentTableInfo);
        txtTable.setText("Your Table: " + table.getTableName());
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
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
        txtTotalItem.setText("Total: " + cart.getTotalQuantity() + " " + txtProduct);
        txtTotalPrice.setText(formatter.format(cart.getTotalPrice()) + " đ");
    }
    ProgressDialog  progressDialog;

    public void clickToPayment(View view) {
        if (ProductPayment.getBill().getListProduct().size() > 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Payment");
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            String ip = getResources().getString(R.string.ip_address);
            String URL2 = "http://" + ip + ":3000/bill/" + table.getTableKey();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest objectRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    URL2,
                    null,
                    new Response.Listener<JSONArray>() {
                        int bId = 0;

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONObject js = response.getJSONObject(0);
                                bId = js.getInt("billId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CreateBill(bId);
                            Toast.makeText(PaymentActivity.this, "Thanks For Use Our Service :)", Toast.LENGTH_SHORT).show();
                            ProductPayment.getBill().getListProduct().clear();
                            ProductPayment.getBill().setCartInfo();
                            progressDialog.dismiss();
                            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("CartError clickToOrder", error.toString());
                            progressDialog.dismiss();
                        }
                    }
            );
            requestQueue.add(objectRequest);
        } else {
            Toast.makeText(PaymentActivity.this, "Please Order Something!", Toast.LENGTH_SHORT).show();

        }
    }

    public void CreateBill(int billId) {

        Cart cart = ProductPayment.getBill();


        for (int i = 0; i < cart.getListProduct().size(); i++) {

            int productId = cart.getListProduct().get(i).getProduct().getId();
            int quantity = cart.getListProduct().get(i).getQuantity();

            String ip = getResources().getString(R.string.ip_address);
            String URL2 = "http://" + ip + ":3000/bills/" + billId + "&" + productId + "&" + quantity;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest objectRequest = new JsonArrayRequest(
                    Request.Method.POST,
                    URL2,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.toString());
                            progressDialog.dismiss();
                        }
                    }
            );
            requestQueue.add(objectRequest);
        }
    }
}
