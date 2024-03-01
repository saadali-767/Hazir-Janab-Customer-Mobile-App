package com.faizanahmed.janabhazir;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Booking_Emergency extends AppCompatActivity {
    Integer serviceId;
    ImageView imgGallery;
    private final int GALLERY_REQ_CODE = 1;
    private Uri imageUri;
    private Bitmap bitmap;
    String serviceType;
    private EditText etAddress, etServiceDescription;
    private Spinner sCityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_emergency);
         serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
         serviceType = getIntent().getStringExtra("serviceType");
        Log.d("serviceIDintent", String.valueOf(serviceId));


        imgGallery = findViewById(R.id.uploadPicture);
        etAddress = findViewById(R.id.etAddress);
        etServiceDescription = findViewById(R.id.etServiceDescription);
        sCityItems = findViewById(R.id.sCityItems);
        Button btnUploadImage = findViewById(R.id.btnUploadImage);
        Button btnNext = findViewById(R.id.btnNext);

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


        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, GALLERY_REQ_CODE);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            int userId = 1; // Placeholder for actual user ID
            int vendorId = 1; // Placeholder for actual vendor ID
            byte[] imgBlob = new byte[0];
            String status="pending";

            Calendar calendar = Calendar.getInstance();

            // Retrieve the year, month, and day of month from the calendar instance
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // Note: January is 0, December is 11
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // You can format the date as a string if needed
            String todayDate = dayOfMonth + "/" + (month + 1) + "/" + year;


            @Override
            public void onClick(View view) {
                bookingdataholder.clearNormalBookingInstance();
               // productordersdataholder.clearOrderList();
                //Toast.makeText(Booking_Normal_4.this, userId, Toast.LENGTH_SHORT).show();
                NormalBooking booking = new NormalBooking(
                        0, userId, serviceId, vendorId, serviceCity ,etAddress.getText().toString(), todayDate, "immediately", serviceDescription, imgBlob,serviceType, status
                );
                bookingdataholder.setNormalBookingInstance(booking);
                if (bookingdataholder.isBookingInstanceValid()) {
                    Log.d("BookingCheck", "The booking instance has been successfully added and is valid.");
                    Toast.makeText(Booking_Emergency.this, "added to cart",Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("BookingCheck", "The booking instance is not valid or has not been added.");
                    // Handle the case where the booking instance is not valid or added
                }


                if (imageUri != null && !etAddress.getText().toString().isEmpty() && !etServiceDescription.getText().toString().isEmpty()) {
                    uploadData();
                    Intent intent = new Intent(Booking_Emergency.this, MainActivity_1.class);
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
                    intent.putExtra("serviceTime", "Emergency");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Booking_Emergency.this, "Please complete the form first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            String address = etAddress.getText().toString();
            String description = etServiceDescription.getText().toString();
            String city = sCityItems.getSelectedItem().toString();

            String serverUrl = getResources().getString(R.string.server_url);
            String fullApiUrl = serverUrl + "hazirjanab/form.php";


            StringRequest stringRequest = new StringRequest(Request.Method.POST, fullApiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            Toast.makeText(Booking_Emergency.this, "Booking completed succesfully ", Toast.LENGTH_LONG).show();

                            //   Toast.makeText(Booking_Emergency.this, "Response: " + response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error", error.toString());
                            Toast.makeText(Booking_Emergency.this, "Upload failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("date", "null"); // changed from service_date to date
                     params.put("time", "null"); // changed from service_time to time
                    params.put("description", description);
                    params.put("image", encodedImage);
                    params.put("type", serviceType);

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
            imgGallery.setImageURI(imageUri);
        }
    }
}
