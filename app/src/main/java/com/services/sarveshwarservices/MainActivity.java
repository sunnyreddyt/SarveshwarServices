package com.services.sarveshwarservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.services.sarveshwarservices.mvp.MVPActivity;

import org.w3c.dom.Text;

/**
 * Created by Sarveshwar Reddy on 12/03/2018.
 */
public class MainActivity extends AppCompatActivity {

    TextView asyncTaskTextView, retrofitTextView, volleyTextView, googleMapsTextView, mvpTextView, serviceTextView, intentServiceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncTaskTextView = (TextView) findViewById(R.id.asyncTaskTextView);
        retrofitTextView = (TextView) findViewById(R.id.retrofitTextView);
        volleyTextView = (TextView) findViewById(R.id.volleyTextView);
        googleMapsTextView = (TextView) findViewById(R.id.googleMapsTextView);
        mvpTextView = (TextView) findViewById(R.id.mvpTextView);
        serviceTextView = (TextView) findViewById(R.id.serviceTextView);
        intentServiceTextView = (TextView) findViewById(R.id.intentServiceTextView);


        intentServiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, IntentServiceActivity.class);
                startActivity(intent);

            }
        });

        serviceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                startActivity(intent);

            }
        });

        mvpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MVPActivity.class);
                startActivity(intent);

            }
        });

        asyncTaskTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AsyncActivity.class);
                startActivity(intent);

            }
        });

        retrofitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RetrofitActivity.class);
                startActivity(intent);

            }
        });

        volleyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, VolleyActivity.class);
                startActivity(intent);

            }
        });

        googleMapsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GoogleMapsActivity.class);
                startActivity(intent);

            }
        });

    }
}
