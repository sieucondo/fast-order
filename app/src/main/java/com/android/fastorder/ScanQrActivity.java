package com.android.fastorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.android.fastorder.R;

public class ScanQrActivity extends AppCompatActivity {
    TextView txtResult;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        init();

    }

    public void init() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã QR code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultcode, Intent intent){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultcode, intent);
        if(result != null){
            String contents = result.getContents();
            txtResult = findViewById(R.id.txtResult);
            txtResult.setText(contents);
            Intent intent1 = new Intent(this, HomeActivity.class);
            startActivity(intent1);
            finish();
        }

    }
}