package com.faizanahmed.janabhazir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.LocaleListCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceType_2 extends AppCompatActivity {
    private static final int REVIEW_REQUEST_CODE = 1;

    DrawerLayout SideBarDrawer;
    ImageView SideBarMenu;
    NavigationView NavigationDrawer1, NavigationDrawer2;
    List<Item> itemList;
    List<Product> productList;
    //List<Item> filteredList ;
    ImageView ivSupport;
    List<NormalBooking> bookingList;
    @Override
    protected void onResume() {
        super.onResume(); // Always call the superclass method first

        // Your code to update the ivSupport ImageView
        ivSupport = findViewById(R.id.ivSupport);
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
        setContentView(R.layout.service_type_2);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
        bookingList = new ArrayList<>();
        //bookingList = userbookingsdataholder.getOrderList();
        productList = new ArrayList<>();
        itemList = new ArrayList<>();
        ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
       // filteredList = new ArrayList<>();
        fetchItemsFromDatabase();
        fetchProductsFromDatabase();
        fetchBookingStatusAndDisplayToast();


        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);

        // Get the logged-in user's data
        JSONObject loggedInUserData = dbHelper.getLoggedInUserData();

        // Extract the user ID
//        int userId = 0; // Default value or error value
//        try {
//            if (loggedInUserData != null && loggedInUserData.has("id")) {
//                userId = loggedInUserData.getInt("id");
//                Log.d("AnotherActivity", "User ID: " + userId);
//                // You can now use the userId as needed in this activity
//            } else {
//                Log.d("AnotherActivity", "User not found");
//                // Handle case where user data is not found or user is not logged in
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            // Handle JSON parsing exception
//        }
//
//        try {
//            Log.d("user id testing", String.valueOf(UserDataSingleton.getInstance().getUserData().get("id")));
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        UserDatabaseHelper ddbHelper = new UserDatabaseHelper(this);
        JSONObject storedUserData = ddbHelper.getLoggedInUserData();
        if(storedUserData != null) {
            Log.d("dbhelperdata", String.valueOf(storedUserData));
        } else {
            Log.d("MainActivity_1", "Stored user data is missing or incomplete.");
        }
//        String serviceName = getIntent().getStringExtra("serviceName");
//        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
//        String serviceDescription = getIntent().getStringExtra("serviceDescription");
//        String serviceCity = getIntent().getStringExtra("serviceCity");
//        String serviceCategory = getIntent().getStringExtra("serviceCategory");
//        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
//        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
//
//        //Display all the intents that were received in toast messages
//        Toast.makeText(this, "serviceName: " + serviceName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceHourlyRate: " + serviceHourlyRate, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceDescription: " + serviceDescription, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCity: " + serviceCity, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceCategory: " + serviceCategory, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceImageUrl: " + serviceImageUrl, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "serviceVideoUrl: " + serviceVideoUrl, Toast.LENGTH_SHORT).show();


        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceType_2.this, "Your cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, YourCart.class);
                startActivity(intent);
            }
        });

        LinearLayout Emergency = findViewById(R.id.llcvEmergency);
        LinearLayout Appointment = findViewById(R.id.llcvAppointment);
        LinearLayout Inquiry = findViewById(R.id.llcvInquiry);
        LinearLayout Emarket = findViewById(R.id.llcvEmarket);

        String serviceType = null;

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(ServiceType_2.this, "Emergency", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, ServiceCategory_3.class);
                intent.putExtra("serviceType", "Emergency");
//                //pass all the details to next activity
//                intent.putExtra("serviceName", serviceName);
//                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
//                intent.putExtra("serviceDescription", serviceDescription);
//                intent.putExtra("serviceCity", serviceCity);
//                intent.putExtra("serviceCategory", serviceCategory);
//                intent.putExtra("serviceImageUrl", serviceImageUrl);
//                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
//                // Put other data...
                startActivity(intent);
            }
        });



        Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(ServiceType_2.this, "Appointment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, ServiceCategory_3.class);
                intent.putExtra("serviceType", "Appointment");
//                //pass all the details to next activity
//                intent.putExtra("serviceName", serviceName);
//                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
//                intent.putExtra("serviceDescription", serviceDescription);
//                intent.putExtra("serviceCity", serviceCity);
//                intent.putExtra("serviceCategory", serviceCategory);
//                intent.putExtra("serviceImageUrl", serviceImageUrl);
//                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
//                // Put other data...
                startActivity(intent);
            }
        });

        Inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(ServiceType_2.this, "Inquiry", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, ServiceCategory_3.class);
                intent.putExtra("serviceType", "Inquiry");

//                //pass all the details to next activity
//                intent.putExtra("serviceName", serviceName);
//                intent.putExtra("serviceHourlyRate", serviceHourlyRate);
//                intent.putExtra("serviceDescription", serviceDescription);
//                intent.putExtra("serviceCity", serviceCity);
//                intent.putExtra("serviceCategory", serviceCategory);
//                intent.putExtra("serviceImageUrl", serviceImageUrl);
//                intent.putExtra("serviceVideoUrl", serviceVideoUrl);
//                // Put other data...
                startActivity(intent);
            }
        });

        Emarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ServiceType_2.this, "Emarket", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, MarketPlaceCategories.class);
                intent.putExtra("serviceType", "Emarket");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REVIEW_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // The review activity returned successfully, handle as needed
                Toast.makeText(this, "Review completed and returned successfully.", Toast.LENGTH_SHORT).show();
                // Refresh your UI here if necessary
            }
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
                                    //Log.d("TAG", "onResponse: Status");
                                    // Log the fetched booking
                                    Log.d("NormalBookingLog", "Fetched Normal Booking: " + normalBooking.toString());
                                }
                                userbookingsdataholder.setOrderList(bookingList);
                                // Notify the adapter that the data set has changed to refresh the RecyclerView

                            } else {
                                // Handle the failure case here
                                //emptycart.setVisibility(View.VISIBLE);
                                //rlService.setVisibility(View.GONE);
                                String message = responseObject.getString("Message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
    private void fetchBookingStatusAndDisplayToast() {
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
        JSONObject loggedInUserData = dbHelper.getLoggedInUserData();
        // Initialize userId as before
        int userId = 0;
        try {
            if (loggedInUserData != null && loggedInUserData.has("id")) {
                userId = loggedInUserData.getInt("id");
            } else {
                Toast.makeText(this, "User not found or not logged in", Toast.LENGTH_SHORT).show();
                return; // User data is missing, so return early
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return; // Exiting on parsing error
        }

        final int finalUserId = userId;


        String serverUrl = getResources().getString(R.string.server_url);
        String fetchBookingsUrl = serverUrl + "/hazirjanab/fetch_bookings.php"; // Adjust the endpoint as needed

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, fetchBookingsUrl,

                response -> {
                    Log.d("BookingResponse", "Response from server: " + response); // Log the complete response

                    try {
                        JSONObject responseObject = new JSONObject(response);
                        if (responseObject.getInt("Status") == 1) {
                            JSONArray bookings = responseObject.getJSONArray("Bookings");
                            for (int i = 0; i < bookings.length(); i++) {
                                JSONObject booking = bookings.getJSONObject(i);
                                String status = booking.getString("status");
                                if ("Completed".equals(status)) {
                                    int vendorId = booking.getInt("vendor_id"); // Make sure this matches your JSON structure
                                    checkForExistingReview(finalUserId, vendorId);
                                    return; // Exit after finding the first completed booking
                                }

                            }
                            // If we make it here, no completed bookings were found
                            //Toast.makeText(ServiceType_2.this, "No completed bookings found", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = responseObject.optString("Message", "Failed to fetch bookings");
                            Toast.makeText(ServiceType_2.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ServiceType_2.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        String errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("Volley Error", "Error: " + errorMessage);
                        Log.e("Volley Error Status", "Status Code: " + error.networkResponse.statusCode);
                    }
                    Toast.makeText(ServiceType_2.this, "Failed to fetch bookings", Toast.LENGTH_SHORT).show();
                }        ) {
            @Override
            protected Map<String, String> getParams() {

                Log.d("userid", "getParams: +"+finalUserId);
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(finalUserId)); // Use final variable here
                return params;
            }
        };

        queue.add(stringRequest);
    }


    private void checkForExistingReview(int userId, int vendorId) {
        String serverUrl = getResources().getString(R.string.server_url);
        String checkReviewUrl = serverUrl + "/hazirjanab/check_for_review.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, checkReviewUrl,
                response -> {
                    try {
                        JSONObject responseObject = new JSONObject(response);
                        // Check if review exists (assuming '0' means no and '1' means yes in your response)
                        if (responseObject.getInt("Status") == 0) {
                            // No review found, navigate to Review Activity
                            Intent intent = new Intent(ServiceType_2.this, review.class); // Use actual Review activity class name
                            intent.putExtra("userId", userId);
                            Log.d("TAG", "checkForExistingReview: "+vendorId);
                            intent.putExtra("vendorId", vendorId);
                            startActivityForResult(intent, REVIEW_REQUEST_CODE);
                        } else {
                            // Review exists, handle accordingly (maybe toast a message or log)
                            Toast.makeText(ServiceType_2.this, "Review already exists", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ServiceType_2.this, "Error parsing response for review check", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle error
                    if (error instanceof NetworkError) {
                        Toast.makeText(ServiceType_2.this, "Network Error: Cannot connect to Internet", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(ServiceType_2.this, "Server Error: The server could not be found. Please try again after some time!", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(ServiceType_2.this, "Auth Failure Error: Cannot connect to Internet", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(ServiceType_2.this, "Parsing Error: Parsing data error", Toast.LENGTH_LONG).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(ServiceType_2.this, "No Connection Error: Cannot connect to Internet", Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(ServiceType_2.this, "Timeout Error: Connection TimeOut! Please check your internet connection.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ServiceType_2.this, "An error occurred: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    if (error.networkResponse != null) {
                        String status = "Error Status code : " + error.networkResponse.statusCode;
                        Log.e("Volley Error", status);
                        // Convert byte[] to string for logging response body (if exists)
                        String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("Volley Error Body", body);
                    }
                }
        ) {
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



//    private void checkBookingStatusAndReview() {
//        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
//        JSONObject loggedInUserData = dbHelper.getLoggedInUserData();
//        int userId = 0; // Default value
//        try {
//            if (loggedInUserData != null && loggedInUserData.has("id")) {
//                userId = loggedInUserData.getInt("id");
//                fetchBookingStatus(userId);
//            } else {
//                // Handle case where user data is not found or user is not logged in
//                return; // Exiting, as we need user id to proceed
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return; // Exiting on parsing error
//        }
//
//        // Assuming you have a method like this defined to perform the check
//    }


//    private void fetchBookingStatus(int userId) {
//        // Network request to check booking status. Assuming your server URL is defined somewhere.
//       // LocaleListCompat contextRef;
//        String serverUrl = getResources().getString(R.string.server_url);
//        String url = "http://192.168.100.132:8080/hazirjanab/fetch_user_status.php";
//       // String url = "YOUR_SERVER_URL/check_booking_status.php"; // Change this to your actual URL
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    // Handle the response, assuming it returns JSON with status and possibly booking details
//                    try {
//                        JSONObject responseObject = new JSONObject(response);
//                        if (responseObject.getInt("Status") == 1 && "Complete".equals(responseObject.getString("status"))) {
//                            // Booking is completed, now check for review
//                            int vendorId = responseObject.getInt("vendor_id");
//                            int bookingId = responseObject.getInt("booking_id");
//                            Log.d("fetchBookingStatus", "Response: " + response);
//                            Log.d("b", "fetchBookingStatus: "+vendorId+bookingId);
//                            checkForReview(userId, vendorId, bookingId);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        // Handle parsing error
//                    }
//                },
//                error -> {
//                    Log.d("TAG", "Error: " + error.toString());
//                    if (error.networkResponse != null) {
//                        Log.d("TAG", "Status Code: " + error.networkResponse.statusCode);
//                        Log.d("TAG", "Response Data: " + new String(error.networkResponse.data, StandardCharsets.UTF_8));
//                    }
//                    Toast.makeText(this, "Failed to fetch booking status", Toast.LENGTH_LONG).show();
//                }
//
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                Log.d("TAG", "getParams: "+userId);
//               params.put("user_id", String.valueOf(userId));
//               // params.put("user_id", "1"); // Temporarily hard-code for debugging
//
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }

    private void checkForReview(int userId, int vendorId, int bookingId) {
        // Similar network request to check for review
        String serverUrl = getResources().getString(R.string.server_url);
        String url = serverUrl + "check_review_exists.php";
       // String url = "YOUR_SERVER_URL/check_review_exists.php"; // Change this to your actual review check URL

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject responseObject = new JSONObject(response);
                        if (responseObject.getInt("Status") == 0) {
                            // Review does not exist, navigate to Review Activity
                            Intent intent = new Intent(ServiceType_2.this, review.class);
                            intent.putExtra("vendorId", vendorId);
                            intent.putExtra("bookingId", bookingId);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle parsing error
                    }
                },
                error -> {
                    // Handle error
                    Toast.makeText(this, "Failed to check for review: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                params.put("vendor_id", String.valueOf(vendorId));
                params.put("booking_id", String.valueOf(bookingId));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void fetchItemsFromDatabase() {
        String serverUrl = getResources().getString(R.string.server_url);
        String allServicesApiUrl = serverUrl + "/hazirjanab/item.php";
        String url = allServicesApiUrl; // Replace with your server URL for fetching item data

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response as a JSONObject
                            JSONObject responseObject = new JSONObject(response);

                            // Check if the status is success
                            if (responseObject.getInt("Status") == 1) {
                                // Get the items array from the response
                                JSONArray itemsArray = responseObject.getJSONArray("Items");

                                // Process each item in the array
                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject itemObject = itemsArray.getJSONObject(i);
                                    int id = itemObject.getInt("id");
                                    String name = itemObject.getString("name");
                                    String hourlyRate = itemObject.getString("hourlyrate");
                                    String description = itemObject.getString("description");
                                    String city = itemObject.getString("city");
                                    String category = itemObject.getString("category");
                                    String type = itemObject.getString("type");

                                    // Assuming you have a way to convert your image data to a URL or file path
                                    // String imageUrl = /* Your logic to fetch or construct the image URL */;

                                    // Construct an Item object
                                    Item item = new Item(id, name, hourlyRate, description, city, category, type, "image70.jpg");
                                    Log.d("ItemLog", "Item : " + item);

                                    // Add the item to your list
                                    itemList.add(item);
                                }

                                // Notify the adapter that the data set has changed to refresh the RecyclerView
                                //adapter.notifyDataSetChanged();
                                servicedataholder.setItemList(itemList);
                                //filterList("", serviceType, serviceCategory);
                            } else {
                                // Handle the failure case here
                                String message = responseObject.getString("Message");
                               // Toast.makeText(SearchAllServices.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(SearchAllServices.this, "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(ServiceType_2.this, "Failed to fetch items: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }
    private void fetchProductsFromDatabase() {
        String serverUrl = getResources().getString(R.string.server_url);
        String allServicesApiUrl = serverUrl + "hazirjanab/product.php";
        String url = allServicesApiUrl; // Replace with your server URL for fetching item data

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response as a JSONObject
                            JSONObject responseObject = new JSONObject(response);

                            // Check if the status is success
                            if (responseObject.getInt("Status") == 1) {
                                // Get the items array from the response
                                JSONArray itemsArray = responseObject.getJSONArray("Products");

                                // Process each item in the array
                                for (int i = 0; i < itemsArray.length(); i++) {
                                    JSONObject itemObject = itemsArray.getJSONObject(i);
                                    int id = itemObject.getInt("id");
                                    String name = itemObject.getString("name");
                                    String description = itemObject.getString("description");
                                    int quantity = itemObject.getInt("quantity");
                                    String category = itemObject.getString("category");
                                    int price = itemObject.getInt("price");
                                    int availabilityInt = itemObject.getInt("availability");
                                    boolean availability = availabilityInt == 1;

                                    Log.d("ProductCheck", "Product : " + id + " " + name + " " + description + " " + quantity + " " + category + " " + price + " " + availability);

                                    // Assuming you have a way to convert your image data to a URL or file path
                                    // String imageUrl = /* Your logic to fetch or construct the image URL */;

                                    // Construct an Product object
                                    Product item = new Product(id, name, description, quantity, category, price, availability);
                                    Log.d("ProductLog", "Product : " + item);

                                    // Add the item to your list
                                    productList.add(item);
                                }
                                ProductDataHolder.setProductList(productList);

                                // Notify the adapter that the data set has changed to refresh the RecyclerView
                              //  adapter.notifyDataSetChanged();
                              //  filterList("", serviceType, serviceCategory);
                            } else {
                                // Handle the failure case here
                                String message = responseObject.getString("Message");
                              //  Toast.makeText(SearchMarket.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ServiceType_2.this, "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.d("error", "onErrorResponse: "+error);
                Toast.makeText(ServiceType_2.this, "Failed to fetch items: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }
}