package com.faizanahmed.janabhazir;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Booking_Normal_4 extends AppCompatActivity {

    TextView tvSelectServiceDate;
    Calendar calendar = Calendar.getInstance();
    ImageView btnUploadImage;
    private final int GALLERY_REQ_CODE = 1;
    private Uri imageUri;
    private Bitmap bitmap;
   Integer serviceId;
    TextView etAddress;
    String serviceType;
    private Spinner sCityItems, sServiceTimeItems;
    private EditText etServiceDescription;
    int userId;

    private byte[] imgBlob1;

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
        setContentView(R.layout.booking_normal_4);
        sCityItems = findViewById(R.id.sCityItems);
        sServiceTimeItems = findViewById(R.id.sServiceTimeItems);
        etAddress = findViewById(R.id.etAddress);
        etServiceDescription = findViewById(R.id.etServiceDescription);
        tvSelectServiceDate = findViewById(R.id.tvSelectServiceDate);

        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
        userId = 0; // Placeholder for actual user ID
        UserDatabaseHelper ddbHelper = new UserDatabaseHelper(this);
        //Integer storedUserData = null;
        try {
            userId = ddbHelper.getLoggedInUserData().getInt("id");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.d("userId", String.valueOf(userId));
        if(userId != 1) {
            Log.d("userId", String.valueOf(userId));
        } else {
            Log.d("MainActivity_1", "Stored user data is missing or incomplete.");
        }



        ImageView ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Booking_Normal_4.this, "Cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Booking_Normal_4.this, YourCart.class);
                startActivity(intent);
            }
        });

         etAddress = findViewById(R.id.etAddress);
        String status="Pending";
         serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
         serviceType = getIntent().getStringExtra("serviceType");
         Integer vendor_id=getIntent().getIntExtra("vendor_id",0);

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


     //   tvSelectServiceDate = findViewById(R.id.tvSelectServiceDate);
        tvSelectServiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //Service Items
        Spinner sService = findViewById(R.id.sServiceItems);
        ArrayList<String> serviceItems = new ArrayList<>();
        serviceItems.add(serviceName);
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceItems);
        sService.setAdapter(serviceAdapter);

        //City Items
        Spinner sServiceCity = findViewById(R.id.sCityItems);
        ArrayList<String> cityItems = new ArrayList<>();
        cityItems.add(serviceCity);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityItems);
        sServiceCity.setAdapter(cityAdapter);

        //Service Time
        Spinner sServiceTime = findViewById(R.id.sServiceTimeItems);
        String[] serviceTimeItems = {"Morning", "Afternoon", "Evening", "Night"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceTimeItems);
        sServiceTime.setAdapter(adapter);

        //imgGallery = findViewById(R.id.uploadPicture);
        btnUploadImage = findViewById(R.id.btnUploadImage);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK);
                intentGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, GALLERY_REQ_CODE);
            }
        });

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
//            JSONObject userData=UserDataSingleton.getInstance().getUserData();
//            int userId=-1;
//
//            {
//                try {
//                    userId = userData.getInt("id");
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }




            int vendorId = 1; // Placeholder for actual vendor ID
            byte[] imgBlob = new byte[10];

            @Override
            public void onClick(View view) {
                Log.d("vendorId", String.valueOf(vendor_id));
                bookingdataholder.clearNormalBookingInstance();
               // productordersdataholder.clearOrderList();
                //Toast.makeText(Booking_Normal_4.this, userId, Toast.LENGTH_SHORT).show();
                NormalBooking booking = new NormalBooking(
                        0, userId, serviceId, vendor_id, serviceCity ,etAddress.getText().toString(), tvSelectServiceDate.getText().toString(), sServiceTime.getSelectedItem().toString(), serviceDescription, imgBlob1,serviceType, status
                );
                bookingdataholder.setNormalBookingInstance(booking);
                if (bookingdataholder.isBookingInstanceValid()) {
                    Log.d("BookingCheck", "The booking instance has been successfully added and is valid.");
                    Toast.makeText(Booking_Normal_4.this, "added to cart",Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("BookingCheck", "The booking instance is not valid or has not been added.");
                    // Handle the case where the booking instance is not valid or added
                }
                Log.d("serviceDate",tvSelectServiceDate.getText().toString());
                if (imageUri != null && !etAddress.getText().toString().isEmpty() && !etServiceDescription.getText().toString().isEmpty() && !tvSelectServiceDate.getText().toString().isEmpty()) {
                    //uploadData();
                    Intent intent = new Intent(Booking_Normal_4.this, MainActivity_1.class);
                    intent.putExtra("serviceId", serviceId);
                    intent.putExtra("serviceName", serviceName);
                    intent.putExtra("serviceHourlyRate", serviceHourlyRate);
                    intent.putExtra("serviceDescription", serviceDescription);
                    intent.putExtra("serviceCity", serviceCity);
                    intent.putExtra("serviceType", serviceType);
                    intent.putExtra("serviceCategory", serviceCategory);
                    intent.putExtra("serviceImageUrl", serviceImageUrl);
                    intent.putExtra("serviceVideoUrl", serviceVideoUrl);
                    intent.putExtra("address", etAddress.getText().toString());
                    intent.putExtra("serviceTime", sServiceTime.getSelectedItem().toString());

                    startActivity(intent);
                }
                else{
                    Toast.makeText(Booking_Normal_4.this, "Please complete the form first!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Format the selected date and set it on the TextView
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                tvSelectServiceDate.setText(selectedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Get the DatePicker object from DatePickerDialog
        DatePicker datePicker = datePickerDialog.getDatePicker();

        // Set the minimum date to today's date
        long oneDayInMilliseconds = 24 * 60 * 60 * 1000;


        datePicker.setMinDate(System.currentTimeMillis() + oneDayInMilliseconds);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }


    private void uploadData() {
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            // Get other details from user input
            final String address = etAddress.getText().toString();
            final String city = sCityItems.getSelectedItem().toString();
            final String serviceDate = tvSelectServiceDate.getText().toString();
            final String serviceTime = sServiceTimeItems.getSelectedItem().toString();
            final String serviceDescription = etServiceDescription.getText().toString();

            String serverUrl = getResources().getString(R.string.server_url);
            String fullApiUrl = serverUrl + "hazirjanab/form.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, fullApiUrl, // Replace with your API endpoint
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("UploadData", "Volley onResponse: " + response); // Log the response
                            Toast.makeText(Booking_Normal_4.this, "Booking Completed successfully", Toast.LENGTH_SHORT).show();
                            // Handle the server response here
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("UploadData", "Volley error: " + error.toString()); // Log the error
                            Toast.makeText(Booking_Normal_4.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            // Handle errors here
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", "1");
                    params.put("service_id", String.valueOf(serviceId));
                    params.put("vendor_id", "1");
                    params.put("city", city);
                    params.put("address", address);
                    params.put("date", serviceDate); // changed from service_date to date
                    params.put("time", serviceTime); // changed from service_time to time
                    params.put("description", serviceDescription);
                    params.put("image", encodedImage); // Ensure this is the actual Base64 encoded image string
                    params.put("type", serviceType);
                    Log.d("UploadData", "Parameters: " + params.toString());
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE && data != null) {
            imageUri = data.getData();
            btnUploadImage.setImageURI(imageUri);
            Log.d("UploadData", "Image selected, URI: " + imageUri); // Confirm image selection

            try {
                // Convert the selected image into a byte array
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                imgBlob1 = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                Log.e("UploadData", "Failed to convert image to byte array", e);
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("UploadData", "Image not selected or an error occurred.");
        }
    }

}