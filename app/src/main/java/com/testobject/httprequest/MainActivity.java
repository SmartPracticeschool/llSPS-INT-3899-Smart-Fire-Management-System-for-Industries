package com.testobject.httprequest;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.ProxySelector;
import java.net.URI;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    static String responseCode = "";
    private TextView proxyField;
    private TextView contentField;
    private TextView responseStatusField;
    private EditText endpointField;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        proxyField = (TextView) findViewById(R.id.proxy_tv);
        contentField = (TextView) findViewById(R.id.content);
        responseStatusField = (TextView) findViewById(R.id.response_status);
        endpointField = (EditText) findViewById(R.id.endpoint);
        Button get = (Button) findViewById(R.id.button);
        Button clear = (Button) findViewById(R.id.buttonClear);

        if (savedInstanceState == null) {
            try {
                proxyField.setText((CharSequence) ProxySelector.getDefault().select(new URI("http://google.com")).get(0).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    responseStatusField.setText(null);
                    contentField.setText(getResponse(endpointField.getText().toString()));
                    responseStatusField.setText(responseCode);
                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    responseStatusField.setText(null);
                    contentField.setText(null);
                    endpointField.setText(null);
                }
            });
        }

    }

    private String getResponse(String endpoint) {
        HttpHelper helper = new HttpHelper();
        String result = "";
        try {
            result = helper.execute(endpoint).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        responseCode = HttpHelper.getResponseCode();
        return result;
    }
}
