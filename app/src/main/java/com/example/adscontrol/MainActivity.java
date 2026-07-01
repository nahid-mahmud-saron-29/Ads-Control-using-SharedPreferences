package com.example.adscontrol;

import android.content.Intent;
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
    // static বাদ দিয়ে সাধারণ লোকাল ভ্যারিয়েবল করা হলো
    boolean showAds = false;

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

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.107/showAds.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("SHOW")){
                            showAds = true;
                            adView.setVisibility(View.VISIBLE);
                            AdRequest adRequest = new AdRequest.Builder().build();
                            adView.loadAd(adRequest);
                        } else {
                            showAds = false;
                            adView.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // এরর আসলেও সেফটি হিসেবে অ্যাড হাইড থাকবে
                showAds = false;
                adView.setVisibility(View.GONE);
            }
        });

        queue.add(stringRequest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInt = new Intent(MainActivity.this, MainActivity2.class);
                // Intent এর মাধ্যমে পরবর্তী Activity তে ডাটা পাস করা হচ্ছে
                myInt.putExtra("SHOW_ADS_KEY", showAds);
                startActivity(myInt);
            }
        });
    }
}