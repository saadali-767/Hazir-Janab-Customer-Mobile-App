package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ServiceCategory_3 extends AppCompatActivity {

    DrawerLayout SideBarDrawer;
    ImageView SideBarMenu;
    NavigationView NavigationDrawer1, NavigationDrawer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_category_3);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceCategory_3.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceCategory_3.this, Support_11.class);
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
            Toast.makeText(ServiceCategory_3.this, "Electrician", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceCategory_3.this, SearchAllServices.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Electrician");
            startActivity(intent);
        });

        cvPlumber.setOnClickListener(view -> {
            Toast.makeText(ServiceCategory_3.this, "Plumber", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceCategory_3.this, SearchAllServices.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Plumber");
            startActivity(intent);
        });

        cvCarpenter.setOnClickListener(view -> {
            Toast.makeText(ServiceCategory_3.this, "Carpenter", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceCategory_3.this, SearchAllServices.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Carpenter");
            startActivity(intent);
        });

        cvMechanic.setOnClickListener(view -> {
            Toast.makeText(ServiceCategory_3.this, "Mechanic", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ServiceCategory_3.this, SearchAllServices.class);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceCategory", "Mechanic");
            startActivity(intent);
        });

    }
}