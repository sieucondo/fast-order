package com.android.fastorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import model.Table;
import util.TableInfo;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickToScanQrCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(ScanQrActivity.class);
        integrator.initiateScan();
    }

    ProgressDialog progressDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (result.getContents() != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Scanning");
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            getResult(result.getContents());
        }
    }

    public void getResult(final String result) {
        final String ip = getResources().getString(R.string.ip_address);
        String URL2 = "http://" + ip + ":3000/table/" + result;
        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        JSONObject js = response.getJSONObject(0);
                        TableInfo.setTable(new Table(
                                js.getInt("TableId"),
                                js.getString("TableKey"),
                                js.getString("TableName"),
                                js.getInt("StoreId"),
                                js.getString("StoreName")));
                        checkTableStatus(js.getString("TableKey"));

                    } else {
                        Toast.makeText(MainActivity.this, "Invalid QR Code Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        JsonArrayRequest objectRequest2 = new JsonArrayRequest(
                Request.Method.GET,
                URL2,
                null,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                        Log.e("Error", error.toString());
                    }
                }
        );
        requestQueue2.add(objectRequest2);
    }

    public void checkTableStatus(final String tableKey) {
        final String ip = getResources().getString(R.string.ip_address);
        String URL2 = "http://" + ip + ":3000/table-status/" + tableKey;
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.length() > 0) {
                        String IsAvailable = response.getString("IsAvailable");
                        if (IsAvailable.equals("1")) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "This Table Is Not Available Please Choose Other Table", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest2 = new JsonObjectRequest(
                Request.Method.GET,
                URL2,
                null,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        );
        requestQueue2.add(objectRequest2);
    }


}
