package com.services.sarveshwarservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.services.sarveshwarservices.service.BackgroundService;

public class ServiceActivity extends AppCompatActivity {

    TextView startServiceTextView, stopServiceTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        startServiceTextView = (TextView) findViewById(R.id.startServiceTextView);
        stopServiceTextView = (TextView) findViewById(R.id.stopServiceTextView);

        startServiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(new Intent(ServiceActivity.this, BackgroundService.class));
                Toast.makeText(ServiceActivity.this, "Service Started", Toast.LENGTH_SHORT).show();
            }
        });

        stopServiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopService(new Intent(ServiceActivity.this, BackgroundService.class));
                Toast.makeText(ServiceActivity.this, "Service Stopped", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
