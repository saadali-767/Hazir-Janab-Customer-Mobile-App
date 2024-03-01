package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MarketPlaceCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place_categories);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MarketPlaceCategories.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MarketPlaceCategories.this, Support_11.class);
                startActivity(intent);
            }
        });

        //get attributes that were passed through an intent
        String serviceType = getIntent().getStringExtra("serviceType");
        Log.d("Intent passed Detail",serviceType );
        Toast.makeText(this, "ServiceType: "+serviceType, Toast.LENGTH_SHORT).show();

        CardView cvElectrician = findViewById(R.id.cv1);
        CardView cvPlumber = findViewById(R.id.cv2);
        CardView cvCarpenter = findViewById(R.id.cv3);
        CardView cvMechanic = findViewById(R.id.cv4);

        cvElectrician.setOnClickListener(view -> {
            Toast.makeText(MarketPlaceCategories.this, "Electrician", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MarketPlaceCategories.this, SearchMarket.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Electrician");
            startActivity(intent);
        });

        cvPlumber.setOnClickListener(view -> {
            Toast.makeText(MarketPlaceCategories.this, "Plumber", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MarketPlaceCategories.this, SearchMarket.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Plumber");
            startActivity(intent);
        });

        cvCarpenter.setOnClickListener(view -> {
            Toast.makeText(MarketPlaceCategories.this, "Carpenter", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MarketPlaceCategories.this, SearchMarket.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Carpenter");
            startActivity(intent);
        });

        cvMechanic.setOnClickListener(view -> {
            Toast.makeText(MarketPlaceCategories.this, "Mechanic", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MarketPlaceCategories.this, SearchMarket.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Mechanic");
            startActivity(intent);
        });
    }
}