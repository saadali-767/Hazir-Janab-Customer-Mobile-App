package com.faizanahmed.janabhazir;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class review extends AppCompatActivity {
    String reviewText;
    EditText reviewEditText;
    TextView bookingInfoTextView;
    Button submitReviewButton;
    int userId;
    int vendorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewEditText = findViewById(R.id.reviewEditText);
        submitReviewButton = findViewById(R.id.submitReviewButton);
        bookingInfoTextView = findViewById(R.id.bookingInfoTextView); // This line was missing

        Intent intent = getIntent();
        if (intent != null) {
             userId = intent.getIntExtra("userId", -1); // Default value is -1 if userId is not found
             vendorId = intent.getIntExtra("vendorId", -1);
            fetchAndDisplayBookingInfo(userId);

            // Now you have userId and vendorId, you can use them as needed
            Log.d("TAG", "Received userId: " + userId);
            Log.d("TAG", "Received vendorId: " + vendorId);

            submitReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitReview();
                }
            });
        }

    }
    private void submitReview() {
        reviewText = reviewEditText.getText().toString().trim(); // Trim to remove any leading or trailing spaces

        // Check if the reviewText is empty
        if (reviewText.isEmpty()) {
            Toast.makeText(review.this, "Please input a review or go back to cancel", Toast.LENGTH_LONG).show();
            return; // Exit the method early if there's no text entered
        }
        String serverUrl1 = getResources().getString(R.string.server_url);
        String serverUrl = serverUrl1 + "/hazirjanab/insert_review.php";
         reviewText = reviewEditText.getText().toString();
        // int userId = 1; // Get this from intent or session
        //int vendorId = 5; // Get this from intent or session
        // String serverUrl = "http://your_server_url/insert_review.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                response -> {
                    Log.d("response", "submitReview: "+response);
                    // Handle response
                    Toast.makeText(review.this, "Review submitted successfully", Toast.LENGTH_LONG).show();
                    updateBookingStatus(); // Call this to update the booking status

                },
                error -> {
                    // Handle error
                    Toast.makeText(review.this, "Failed to submit review: " + error.toString(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                params.put("vendor_id", String.valueOf(vendorId));
                params.put("description", reviewText);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void updateBookingStatus() {
        String serverUrl1 = getResources().getString(R.string.server_url);
        String serverUrl = serverUrl1 + "/hazirjanab/update_booking_status.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                response -> {
                    Log.d("UpdateStatusResponse", "Response: " + response);
                    try {
                        JSONObject responseObject = new JSONObject(response);
                        if ("success".equals(responseObject.getString("status"))) {
                           // Toast.makeText(review.this, "Booking status updated to Reviewed", Toast.LENGTH_SHORT).show();
                            updateModel(reviewText);
                        } else {
                            Toast.makeText(review.this, responseObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(review.this, "Error parsing update status response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle error
                    Toast.makeText(review.this, "Failed to update booking status: " + error.toString(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                params.put("vendor_id", String.valueOf(vendorId));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void updateModel(final String reviewText) {
        String serverUrl4 = getResources().getString(R.string.update);
        String serverUrl = serverUrl4 + "update_model";

        //String serverUrl = "http://192.168.100.132:5001/update_model";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a JSON object with the data to send
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("text", reviewText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serverUrl, jsonBody,
                response -> {
                    Log.d("UpdateModelResponse", "Model update response: " + response);
                    // Handle response
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                },
                error -> {
                    Log.e("UpdateModelError", "Failed to update model: " + error.toString());
                    Toast.makeText(review.this, "Failed to update model: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }
    private void fetchAndDisplayBookingInfo(final int userId) {
        String serverUrl = getResources().getString(R.string.server_url);
        String url = serverUrl + "/hazirjanab/fetch_bookings_review.php";
       // String url = "http://yourserver.com/path/to/fetch_booking_info.php"; // Update with your server URL
        RequestQueue queue = Volley.newRequestQueue(this);

        // Prepare the request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("response", "fetchAndDisplayBookingInfo: "+response);
                    try {

                        // Parse the JSON response
                        JSONObject jsonResponse = new JSONObject(response);
                        int status = jsonResponse.getInt("Status");
                        if (status == 1) {
                            JSONArray bookings = jsonResponse.getJSONArray("Bookings");
                            StringBuilder bookingDetails = new StringBuilder();

                            for (int i = 0; i < ((JSONArray) bookings).length(); i++) {
                                JSONObject booking = bookings.getJSONObject(i);
                                String city = booking.getString("city");
                                String address = booking.getString("address");
                                String date = booking.getString("date");
                                String description = booking.getString("description");

                                // Append booking information to the StringBuilder
                                bookingDetails.append("City: ").append(city).append("\n")
                                        .append("Address: ").append(address).append("\n")
                                        .append("Date: ").append(date).append("\n")
                                        .append("Description: ").append(description).append("\n\n");
                            }

                            // Display the booking information in the TextView
                            bookingInfoTextView.setText(bookingDetails.toString());
                        } else {
                            // Handle case where no bookings are found
                            String message = jsonResponse.optString("Message", "No bookings found");
                            Toast.makeText(review.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(review.this, "Error parsing booking info", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(review.this, "Error fetching booking info: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId)); // Send the user ID as POST parameter
                return params;
            }
        };

        // Add the request to the RequestQueue to execute it
        queue.add(stringRequest);
    }


}
