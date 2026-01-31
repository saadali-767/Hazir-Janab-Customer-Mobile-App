package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class serviceDetails extends AppCompatActivity {
 TextView tvProviderName;

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
        setContentView(R.layout.activity_service_details);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
         tvProviderName = findViewById(R.id.tvProviderName);

        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(serviceDetails.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(serviceDetails.this, Support_11.class);
                startActivity(intent);
            }
        });

        Integer serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
        String serviceType = getIntent().getStringExtra("serviceType");
        String vendor_name = getIntent().getStringExtra("vendor_name");
        Integer vendor_id = Integer.valueOf(getIntent().getStringExtra("vendor_id"));

        Log.d("2", "Service ID: " + serviceId);
        Log.d("IntentData", "Service Name: " + serviceName);
        Log.d("IntentData", "Service Hourly Rate: " + serviceHourlyRate);
        Log.d("IntentData", "Service Description: " + serviceDescription);
        Log.d("IntentData", "Service City: " + serviceCity);
        Log.d("IntentData", "Service Category: " + serviceCategory);
        Log.d("IntentData", "Service Image URL: " + serviceImageUrl);
        // Log.d("IntentData", "Service Video URL: " + serviceVideoUrl); // Uncomment if needed
        Log.d("IntentData", "Service Type: " + serviceType);
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

        ImageView ivServiceImage = findViewById(R.id.ivServiceImage);
        ImageView ivVendorImage = findViewById(R.id.ivVendorImage);
        TextView tvServiceName = findViewById(R.id.tvServiceName);
        TextView tvServiceHourlyRate = findViewById(R.id.tvHourlyRate);
        TextView tvServiceDescription = findViewById(R.id.tvServiceDescription);
        TextView tvServiceCity = findViewById(R.id.tvServiceCity);
        TextView tvProviderName=findViewById(R.id.tvProviderName);


        String img = "ic_service"+String.valueOf(serviceId);
        int imageResId = getResources().getIdentifier(img, "drawable", getPackageName());
        if (imageResId != 0) {
            Log.d("vendorimg", "vendorimg: " + img);
            Log.d("vendorImageResId", "vendorImageResId: " + imageResId);
            Log.d("ivVendorImage", "ivVendorImage: " + ivServiceImage);
            ivServiceImage.setImageResource(imageResId);
        } else {
            Log.d("Image", "Image not found");
        }

        String vendorimg = "ic_vendor"+vendor_name.toLowerCase();
        Log.d("vendorimg", "vendorimg: " + vendorimg);
        int vendorImageResId = getResources().getIdentifier(vendorimg, "drawable", getPackageName());
        if (vendorImageResId != 0) {
            Log.d("vendorimg", "vendorimg: " + vendorimg);
            Log.d("vendorImageResId", "vendorImageResId: " + vendorImageResId);
            //log d to check if ivVendorImage is null
            Log.d("ivVendorImage", "ivVendorImage: " + ivVendorImage);
            ivVendorImage.setImageResource(vendorImageResId);
        } else {
            Log.d("Image", "Image not found");
        }

        //set the values of the views
        tvServiceName.setText(serviceName);
        tvProviderName.setText(vendor_name);

        tvServiceHourlyRate.setText("PKR " + serviceHourlyRate + "/hr");
        tvServiceDescription.setText(serviceDescription);
        tvServiceCity.setText(serviceCity);

        Button btnBookNow = findViewById(R.id.btnBookNow);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;

                switch (serviceType) {
                    case "Emergency":
                        intent = new Intent(serviceDetails.this, Booking_Emergency.class);
                        break;
                    case "Appointment":
                        intent = new Intent(serviceDetails.this, Booking_Normal_4.class);
                        break;
                    case "Inquiry":
                        intent = new Intent(serviceDetails.this, Booking_Normal_4.class);
                        break;
                    case "Emarket":
                        Toast.makeText(serviceDetails.this, "Emarket", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(serviceDetails.this, "Invalid service type", Toast.LENGTH_SHORT).show();
                        break;
                }
                //pass all the details to next activity
                intent.putExtra("vendor_id",vendor_id);
                intent.putExtra("serviceId", serviceId);
                intent.putExtra("serviceName", serviceName);
                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
                intent.putExtra("serviceDescription", serviceDescription);
                intent.putExtra("serviceCity", serviceCity);
                intent.putExtra("serviceCategory", serviceCategory);
                intent.putExtra("serviceImageUrl", serviceImageUrl);
                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
                intent.putExtra("serviceType", serviceType);
                // Put other data...
                startActivity(intent);
            }
        });



    }
}