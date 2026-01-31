package com.faizanahmed.janabhazir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class orderPlacedCOD_10 extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume(); // Always call the superclass method first

        // Your code to update the ivSupport ImageView
        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty()){
            ivSupport.setImageResource(R.drawable.fullcart);
        } else {
            // You can set a default image if the conditions are not met
            ivSupport.setImageResource(R.drawable.ic_cart); // Assuming you have an 'emptycart' drawable
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed_cod10);

        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(orderPlacedCOD_10.this, "cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(orderPlacedCOD_10.this, Support_11.class);
                startActivity(intent);
            }
        });

        Integer serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceType = getIntent().getStringExtra("serviceType");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
        String address = getIntent().getStringExtra("address");


        //Display all the intents that were received in toast messages
//        Toast.makeText(this, "serviceId: " + serviceId, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceName: " + serviceName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceHourlyRate: " + serviceHourlyRate, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceDescription: " + serviceDescription, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCity: " + serviceCity, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCategory: " + serviceCategory, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceImageUrl: " + serviceImageUrl, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceVideoUrl: " + serviceVideoUrl, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceType: " + serviceType, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "address: " + address, Toast.LENGTH_SHORT).show();

        //get imageview and textview by id
        TextView tvOrderId = findViewById(R.id.tvOrderId);
        ImageView ivServiceMan = findViewById(R.id.ivServiceMan);
        TextView tvServiceManName = findViewById(R.id.tvServiceMan);
        TextView tvService = findViewById(R.id.tvService);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);

        drawSideBar.setup(this);

        switch (serviceCategory) {
            case "Electrician":
                ivServiceMan.setImageResource(R.drawable.ic_electrician);
                tvServiceManName.setText("Electrician");
                break;
            case "Plumber":
                ivServiceMan.setImageResource(R.drawable.ic_plumber);
                tvServiceManName.setText("Plumber");
                break;
            case "Carpenter":
                ivServiceMan.setImageResource(R.drawable.ic_carpenter);
                tvServiceManName.setText("Carpenter");
                break;
            case "Mechanic":
                ivServiceMan.setImageResource(R.drawable.ic_mechanic);
                tvServiceManName.setText("Mechanic");
                break;
            default:
                Toast.makeText(this, "Error: Service Category not found", Toast.LENGTH_SHORT).show();
                break;
        }

        //RandomOrder ID between 0 and 500000
        int OrderId = (int) (Math.random() * 500000);

        tvOrderId.setText("Order # " + OrderId);
        tvService.setText(serviceName);
        tvAddress.setText(address);
        tvTotalAmount.setText("PKR " + serviceHourlyRate + "/hr");

        if(serviceType.equals("Emergency")){
            tvTime.setText("Emergency Basis");
        }
        else if(serviceType.equals("Appointment")){
            String serviceTime = getIntent().getStringExtra("serviceTime");
            tvTime.setText(serviceTime);
        }
        else{
            Toast.makeText(this, "Inquiry", Toast.LENGTH_SHORT).show();
        }


        Button btnHome = findViewById(R.id.BtnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orderPlacedCOD_10.this, ServiceType_2.class);
                startActivity(intent);
            }
        });


    }
}