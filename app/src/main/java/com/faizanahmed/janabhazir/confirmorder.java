package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class confirmorder extends AppCompatActivity {
    Button BtnNext,BtnBack;
    List<productorders> orders;
    int bookingID;
    public interface UploadCallback {
        void onUploadSuccess();
        void onUploadFailure(String error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        BtnBack=findViewById(R.id.BtnBack);

        BtnNext=findViewById(R.id.BtnNext);
        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NormalBooking normalBooking = bookingdataholder.getNormalBookingInstance();
                uploadData(normalBooking, new YourCart.UploadCallback() {
                    @Override
                    public void onUploadSuccess() {
                        orders = productordersdataholder.getOrderList();

                        uploadProductOrders(orders, bookingID);
                        productordersdataholder.clearOrderList();
                        bookingdataholder.clearNormalBookingInstance();

                    }

                    @Override
                    public void onUploadFailure(String error) {
                        Toast.makeText(confirmorder.this, "Failed to upload booking data: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(confirmorder.this, YourCart.class);
                startActivity(intent);
            }
        });
    }
    private void uploadData(NormalBooking normalBooking, YourCart.UploadCallback callback) {
        // Extract parameters from normalBooking instance
        final String userId = String.valueOf(normalBooking.getUserId());
        final String serviceId = String.valueOf(normalBooking.getServiceId());
        final String vendorId = String.valueOf(normalBooking.getVendorId());
        final String city = normalBooking.getCity();
        final String address = normalBooking.getAddress();
        final String date = normalBooking.getDate();
        final String time = normalBooking.getTime();
        final String description = normalBooking.getDescription();
        final String type = normalBooking.getType();
        final String status = normalBooking.getStatus();

        // Encode the image byte array to Base64
        final String encodedImage = Base64.encodeToString(normalBooking.getPicture(), Base64.DEFAULT);

        // Define the server endpoint to your API
        String serverUrl = getResources().getString(R.string.server_url);
        String fullApiUrl = serverUrl + "hazirjanab/post_order.php"; // Replace with your actual API endpoint

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, fullApiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UploadData", "Volley onResponse: " + response); // Log the response
                        Toast.makeText(confirmorder.this, "Booking Completed successfully", Toast.LENGTH_SHORT).show();
                        // Handle the server response here
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response);
                            bookingID = jsonResponse.getInt("booking_id");
                            Log.d("UploadData", "Booking ID: " + bookingID);
                            callback.onUploadSuccess();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UploadData", "Volley error: " + error.toString()); // Log the error

                Toast.makeText(confirmorder.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                callback.onUploadFailure(error.toString());
                // Handle errors here
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                params.put("service_id", serviceId);
                params.put("vendor_id", vendorId);
                params.put("city", city);
                params.put("address", address);
                params.put("date", date);
                params.put("time", time);
                params.put("description", description);
                params.put("type", type);
                params.put("status", status);
                params.put("image", encodedImage); // Ensure this is the actual Base64 encoded image string
                Log.d("UploadData", "Parameters: " + params.toString());
                return params;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void uploadProductOrders(final List<productorders> productOrders, final int bookingId) {
        String serverUrl = getResources().getString(R.string.server_url);
        String fullApiUrl = serverUrl + "hazirjanab/post_bookings.php";

        final JSONArray bookingsArray = new JSONArray();
        for (productorders order : productOrders) {
            JSONObject bookingObject = new JSONObject();
            try {
                bookingObject.put("booking_id", bookingId);
                bookingObject.put("product_id", order.getProductID());
                bookingObject.put("quantity", order.getQuantity());
                bookingsArray.put(bookingObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final JSONObject bookingsObject = new JSONObject();
        try {
            bookingsObject.put("bookings", bookingsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String bookingsString = bookingsObject.toString();

        //final String bookingsString = bookingsArray.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, fullApiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UploadProductOrders", "Volley onResponse: " + response);
                        Toast.makeText(confirmorder.this, "Product orders uploaded successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(confirmorder.this, MainActivity_1.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UploadProductOrders", "Volley error: " + error.toString());
                Toast.makeText(confirmorder.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return bookingsString.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

}