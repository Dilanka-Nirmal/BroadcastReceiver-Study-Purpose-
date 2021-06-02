package com.example.broadcastproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String BROADCAST_ACTION = "com.example.broadcastproj.broadcast.BROADCAST_MESSAGE";
    Button button;
    TextView textView;
    Receiver localListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(v -> {
            if (v.getId() == R.id.button){
                BackgroundService.startAction(this.getApplicationContext());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        localListener = new Receiver();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        this.registerReceiver(localListener, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(localListener);
    }

    public class Receiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent){
            String currentText = textView.getText().toString();
            String message = intent.getStringExtra("value");
            currentText += "\nReceived " + message;
            textView.setText(currentText);
        }
    }

}