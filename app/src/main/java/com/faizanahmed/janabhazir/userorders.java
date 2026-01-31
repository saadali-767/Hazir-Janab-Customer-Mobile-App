package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userorders extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView  rvService;
    userordersserviceadapter cartServiceAdapter;
    List<CartServiceItem> serviceItemList;
    List<NormalBooking> bookingList;
    LinearLayout rlService;
    TextView emptycart;
    int userId ;// Placeholder for actual user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userorders);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
        bookingList = new ArrayList<>();
        emptycart=findViewById(R.id.emptycart);
        rlService=findViewById(R.id.rlService);

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

        rvService = findViewById(R.id.rvServiceList);
        serviceItemList = new ArrayList<>();
        //initializeServiceItemList(); // This method will populate the serviceItemList
        cartServiceAdapter = new userordersserviceadapter(userorders.this, serviceItemList,this);
        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setAdapter(cartServiceAdapter);
        fetchNormalBookingsFromDatabase(userId);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(userProfile_6.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(userorders.this, Support_11.class);
                startActivity(intent);
            }
        });
    }
    private void initializeServiceItemList() {
        //fetchNormalBookingsFromDatabase(1);
        // Clear the serviceItemList to ensure it's ready for fresh data
        serviceItemList.clear();


        // Retrieve the booking list from the data holder
        bookingList = userbookingsdataholder.getOrderList();

        // Check if the bookingList is not empty
        if (bookingList != null && !bookingList.isEmpty()) {
            // Iterate over all bookings
            for (NormalBooking normalBooking : bookingList) {

                // Use the method to get the Item by serviceId for each booking
                if(normalBooking.getStatus() !="Completed" || normalBooking.getStatus()!="Rejected") {
                    Item serviceItem = servicedataholder.findItemByServiceId(normalBooking.getServiceId());

                    if (serviceItem != null) {
                        // Create a CartServiceItem for each NormalBooking and corresponding Item
                        CartServiceItem cartServiceItem = new CartServiceItem(normalBooking, serviceItem);
                        // Add the CartServiceItem to the serviceItemList
                        serviceItemList.add(cartServiceItem);
                    } else {
                        // Log or handle the case where no Item matches the serviceId
                        Log.d("ServiceItemLog", "No service found with ID: " + normalBooking.getServiceId());
                    }
                }
            }
        } else {
            // Log or handle the case where bookingList is empty or null
            Log.d("ServiceItemLog", "No bookings available or bookingList is null.");

        }

        // Notify any observers that the data set has changed (if you are using an adapter or similar)
        if (cartServiceAdapter != null) {
            cartServiceAdapter.notifyDataSetChanged();
        }
        if(serviceItemList.isEmpty() || serviceItemList == null){
            emptycart.setVisibility(View.VISIBLE);
            rlService.setVisibility(View.GONE);

        }
    }

    private void fetchNormalBookingsFromDatabase(int bookingId) {
        String serverUrl = getResources().getString(R.string.server_url);
        String allNormalBookingsApiUrl = serverUrl + "hazirjanab/fetch_bookings.php"; // Replace with your server URL for fetching normal booking data

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, allNormalBookingsApiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response as a JSONObject
                            JSONObject responseObject = new JSONObject(response);

                            // Check if the status is success
                            if (responseObject.getInt("Status") == 1) {
                                // Get the normal bookings array from the response
                                JSONArray normalBookingsArray = responseObject.getJSONArray("Bookings");

                                // Clear the existing normal bookings
                                bookingList.clear();

                                // Process each normal booking in the array
                                for (int i = 0; i < normalBookingsArray.length(); i++) {
                                    JSONObject bookingObject = normalBookingsArray.getJSONObject(i);
                                    int id = bookingObject.getInt("id");
                                    int userId = bookingObject.getInt("user_id");
                                    int serviceId = bookingObject.getInt("service_id");
                                    int vendorId = bookingObject.getInt("vendor_id");
                                    String city = bookingObject.getString("city");
                                    String address = bookingObject.getString("address");
                                    String date = bookingObject.getString("date");
                                    String time = bookingObject.getString("time");
                                    String description = bookingObject.getString("description");
                                    // For the image, you need to convert the base64 string back to byte array if it's sent as base64.
                                    // If it's sent as a URL or filepath, use that instead.
                                    String type = bookingObject.getString("type");
                                    String status = bookingObject.getString("status");

                                    // Construct a NormalBooking object (assuming you have a constructor)
                                    NormalBooking normalBooking = new NormalBooking(id, userId, serviceId, vendorId, city, address, date, time, description, null, type, status);
                                    bookingList.add(normalBooking);

                                    // Log the fetched booking
                                    Log.d("NormalBookingLog", "Fetched Normal Booking: " + normalBooking.toString());
                                }
                                userbookingsdataholder.setOrderList(bookingList);
                                // Notify the adapter that the data set has changed to refresh the RecyclerView
                                 if (cartServiceAdapter != null) {
                                     cartServiceAdapter.notifyDataSetChanged();
                                 }
                                 initializeServiceItemList();
                            } else {
                                // Handle the failure case here
                                //emptycart.setVisibility(View.VISIBLE);
                                //rlService.setVisibility(View.GONE);
                                String message = responseObject.getString("Message");
                                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                initializeServiceItemList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(getApplicationContext(), "Failed to fetch normal bookings: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(bookingId));
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void onItemClicked(int position) {
        // Check if the position is valid within the serviceItemList
        if (position >= 0 && position < serviceItemList.size()) {
            // Get the booking ID from the serviceItemList, which contains CartServiceItem objects
            int bookingId = serviceItemList.get(position).getBooking().getId();
            // Show the booking ID in a Toast
            Toast.makeText(this, "Booking ID: " + bookingId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(userorders.this, orderdetails.class);
            intent.putExtra("check",1);
            intent.putExtra("bookingId", bookingId);

            startActivity(intent);
        } else {
            // If the position is not valid, log an error or show a Toast
            Log.e("userorders", "Clicked position is out of bounds of the service item list.");
            Toast.makeText(this, "Error: Invalid item position.", Toast.LENGTH_SHORT).show();
        }
    }

}