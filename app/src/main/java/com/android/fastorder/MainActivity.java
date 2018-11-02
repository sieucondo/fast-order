package com.android.fastorder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToScanQrCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES );
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(ScanQrActivity.class);
        integrator.initiateScan();
    }


    @Override
    public void onActivityResult(int requestCode, int resultcode, Intent intent){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultcode, intent);
        if(result != null){
            Intent intent1 = new Intent(this, HomeActivity.class);
            startActivity(intent1);
        }
    }


}
