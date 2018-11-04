package util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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

import java.io.IOException;

import model.Table;


public class AsyncTaskGetTableInfo extends AsyncTask<String, Void, Table> {
    ProgressDialog progressDialog;
    Context context;

    public AsyncTaskGetTableInfo(Context context) {
        this.context = context;
    }

    Table table = new Table();

    @Override
    protected Table doInBackground(String... result) {


        return table;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("HowKteam");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Table table) {
        super.onPostExecute(table);
        TableInfo.setTable(table);
    }
}
