package com.example.adscontrol;

import android.content.Intent;
import android.content.SharedPreferences; // ইম্পোর্ট করতে হবে
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    AdView adView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adView = findViewById(R.id.adView);
        btn = findViewById(R.id.btn);

        new Thread(() -> {
            MobileAds.initialize(this, initializationStatus -> {});
        }).start();

        // SharedPreferences তৈরি করা হচ্ছে
        SharedPreferences sharedPreferences = getSharedPreferences("AdsSettings", MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.107/showAds.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("SHOW")){
                            // লোকাল মেমরিতে true সেভ করা হচ্ছে
                            sharedPreferences.edit().putBoolean("SHOW_ADS", true).apply();

                            adView.setVisibility(View.VISIBLE);
                            AdRequest adRequest = new AdRequest.Builder().build();
                            adView.loadAd(adRequest);
                        } else {
                            // লোকাল মেমরিতে false সেভ করা হচ্ছে
                            sharedPreferences.edit().putBoolean("SHOW_ADS", false).apply();
                            adView.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // নেটওয়ার্ক এরর হলে সেফটির জন্য false সেভ থাকবে
                sharedPreferences.edit().putBoolean("SHOW_ADS", false).apply();
                adView.setVisibility(View.GONE);
            }
        });

        queue.add(stringRequest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // এখন আর Intent-এ করে ডাটা পাঠাতে হবে না, সরাসরি চলে গেলেই হবে
                Intent myInt = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(myInt);
            }
        });
    }
}