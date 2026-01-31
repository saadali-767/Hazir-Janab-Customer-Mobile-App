
package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.StringValue;

public class ProductDetails extends AppCompatActivity {

    TextView txtQuantity;
    Button btnIncrease, btnDecrease,btnBookNow;
    int quantity = 0; // Initial quantity

    ImageView ivServiceImage;
    TextView tvServiceName, tvServiceDescription, tvServicePrice, tvServiceAvailability;

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
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SearchAllServices.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProductDetails.this, Support_11.class);
                startActivity(intent);
            }
        });

        // Retrieve the data
        Integer productId = getIntent().getIntExtra("productId", -100);
        String productName = getIntent().getStringExtra("productName");
        String productDescription = getIntent().getStringExtra("productDescription");
        Integer productQuantity = getIntent().getIntExtra("productQuantity", -100);
        String productCategory = getIntent().getStringExtra("productCategory");
        Integer productPrice = getIntent().getIntExtra("productPrice", -100);
        Boolean productAvailability = getIntent().getBooleanExtra("productAvailability", false);

        txtQuantity = findViewById(R.id.txtQuantity);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnBookNow=findViewById(R.id.btnBookNow);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the product is available and quantity is more than 0
                if (quantity > 0) {
                    // Create a new ProductOrders object with the current product ID and quantity
                    Log.d("Product Quantity: ", String.valueOf(quantity));
                    productorders newOrder = new productorders(productId, quantity);

                    // Add this order to the singleton list
                    productordersdataholder.addProductOrder(newOrder);

                    // Show a confirmation message or proceed to the next activity/screen
                    Toast.makeText(ProductDetails.this, "added to cart!", Toast.LENGTH_SHORT).show();

                    // Optionally, you can reset the quantity or navigate the user to another activity
                    // For example, reset the quantity to 0 and update the UI accordingly
                    quantity = 0;
                    txtQuantity.setText(String.valueOf(quantity));

                    // If you wish to navigate the user to another activity after placing the order
                    Intent intent = new Intent(ProductDetails.this, ServiceType_2.class);
                    startActivity(intent);
                } else {
                    // Show an error message if the product is unavailable or quantity is 0
                    Toast.makeText(ProductDetails.this, "Invalid order quantity ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                txtQuantity.setText(String.valueOf(quantity));
            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        ivServiceImage = findViewById(R.id.ivServiceImage);
        tvServiceName = findViewById(R.id.tvProductName);
        tvServiceDescription = findViewById(R.id.tvProductDescription);
        tvServicePrice = findViewById(R.id.tvProductPrice);
//        tvServiceAvailability = findViewById(R.id.tvServiceAvailability);

        tvServiceName.setText(productName);
        tvServiceDescription.setText(productDescription);
        tvServicePrice.setText("Rs. " + productPrice);
        String img = "ic_service"+String.valueOf(productId);
        int imageResId = getResources().getIdentifier(img, "drawable", getPackageName());
        if (imageResId != 0) {
            Log.d("ProductImg", "productimg: " + img);
            Log.d("ProductImageResId", "ProductImageResId: " + imageResId);
            Log.d("ivProductImage", "ivProductImage: " + ivServiceImage);
            ivServiceImage.setImageResource(imageResId);
        } else {
            Log.d("Image", "Image not found");
        }


    }
}