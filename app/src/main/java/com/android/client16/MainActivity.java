package com.android.client16;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText editTextAddress, editTextPort;
    String dstAddress;
    Integer dstPort;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clear = (Button)findViewById(R.id.buttonclear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAddress.setText("");
                editTextPort.setText("");
            }
        });

        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
    }
    public void doPost (View view) {

        dstAddress = editTextAddress.getText().toString();
        dstPort = Integer.parseInt(editTextPort.getText().toString());
        Intent it = new Intent(MainActivity.this, Connected.class);
        it.putExtra("dstAddress", dstAddress);
        it.putExtra("dstPort", dstPort);
        startActivity(it);
    }
}
