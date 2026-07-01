package com.example.adscontrol;

import android.content.SharedPreferences; // ইম্পোর্ট করতে হবে
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity2 extends AppCompatActivity {
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adView = findViewById(R.id.adView);

        // SharedPreferences থেকে ডাটা পড়া হচ্ছে
        SharedPreferences sharedPreferences = getSharedPreferences("AdsSettings", MODE_PRIVATE);
        // "SHOW_ADS" কি-এর মান আনা হচ্ছে, যদি কিছু না পায় তবে ডিফল্ট হিসেবে false ধরবে
        boolean shouldShowAds = sharedPreferences.getBoolean("SHOW_ADS", false);

        if (shouldShowAds){
            adView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } else {
            adView.setVisibility(View.GONE);
        }
    }
}