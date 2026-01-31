package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class orderdetails extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView rvProduct, rvService;
    userordersproductadapter cartProductAdapter;
    userordersserviceadapter cartServiceAdapter;
    List<Product> productList;
    List<CartServiceItem> serviceItemList;
    List<productorders> orders;
    int bookingID,defaultValue,check;
    Button BtnNext;
    List<productorders> pList;
    LinearLayout product_layout;
    TextView emptyproductlist;
    TextView ivtotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
        BtnNext=findViewById(R.id.BtnNext);
        product_layout=findViewById(R.id.rlProduct);
        emptyproductlist=findViewById(R.id.emptyproductlist);
        ivtotal=findViewById(R.id.tvTotalAmount);

        productList = new ArrayList<>();
        pList = new ArrayList<>();

        defaultValue=0;
        bookingID = getIntent().getIntExtra("bookingId", defaultValue);
        check=getIntent().getIntExtra("check",defaultValue);
        if(check ==1 ){

            BtnNext.setVisibility(View.VISIBLE);

        }else{
            BtnNext.setVisibility(View.GONE);
        }



        // Initialize and setup product list and adapter
        rvProduct = findViewById(R.id.rvProductList);
        productList = new ArrayList<>();
        //initializeProductList(); // This method will populate the productList
        fetchNormalBookingsFromDatabase(bookingID);
        cartProductAdapter = new userordersproductadapter(orderdetails.this,pList, productList, this);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setAdapter(cartProductAdapter);

        // Initialize and setup service item list and adapter
        rvService = findViewById(R.id.rvServiceList);
        serviceItemList = new ArrayList<>();
        initializeServiceItemList(); // This method will populate the serviceItemList
        cartServiceAdapter = new userordersserviceadapter(orderdetails.this, serviceItemList, this);
        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setAdapter(cartServiceAdapter);
        String service_status=userbookingsdataholder.findOrdersByProductId(bookingID).getStatus();
        Log.d("service status", service_status);
        if(!service_status.equals("In-progress")){
            BtnNext.setVisibility(View.GONE);
        }else{
            BtnNext.setVisibility(View.VISIBLE);
        }

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(orderdetails.this, order_tracking.class);
               intent.putExtra("bookingId",bookingID);
               startActivity(intent);


            }
        });
    }
    public void updateTotalAmount() {
        double totalAmount = 0;

        // Calculate the total amount from the product list
        for (Product product : productList) {
            totalAmount += product.getPrice() * product.getQuantity();
        }

        // Calculate the total amount from the service list
        for (CartServiceItem serviceItem : serviceItemList) {
            totalAmount += Double.parseDouble(serviceItem.getServiceItem().getItemHourlyRate());
        }

        // Update the TextView with the new total amount
        ivtotal.setText("PKR " + String.format("%.2f", totalAmount) + "/-");
    }

    private void initializeProductList() {
        // Clear the existing productList to ensure it's empty before adding new items
        productList.clear();



        // Get the list of product orders from the ProductOrderManager
       // orders = productordersdataholder.getOrderList();

        // Iterate over each order
        for (productorders order : pList) {
            // Use the product ID from the order to find the corresponding product
            Product product = ProductDataHolder.findProductById(order.getProductID());

            if (product != null) {
                // Create a new product instance with the order's quantity
                Product orderedProduct = new Product(product.getId(), product.getName(), product.getDescription(), order.getQuantity(), product.getCategory(), product.getPrice(), product.isAvailability());

                // Add the product with the updated quantity to the productList
                productList.add(orderedProduct);
            }
        }
        Log.d("product list", "initializeProductList: "+productList);
        if (productList.isEmpty() || productList == null){
            product_layout.setVisibility(View.GONE);
            emptyproductlist.setVisibility(View.VISIBLE);

        }
        updateTotalAmount();

        // Now productList is initialized with products from the orders
    }


    private void initializeServiceItemList() {
        NormalBooking normalBooking = userbookingsdataholder.findOrdersByProductId(bookingID);
        if (normalBooking != null) {
            // Use the new method to get the Item by serviceId
            Item serviceItem = servicedataholder.findItemByServiceId(normalBooking.getServiceId());

            if (serviceItem != null) {
                // Assuming CartServiceItem constructor takes a NormalBooking and an Item
                CartServiceItem cartServiceItem = new CartServiceItem(normalBooking, serviceItem);
                // Now it's safe to add cartServiceItem to the list, as it's guaranteed to be initialized
                serviceItemList.add(cartServiceItem);
            } else {
                // Handle case where no Item matches the serviceId
                System.out.println("No service found with ID: " + normalBooking.getServiceId());
            }
        } else {
            // Handle case where there is no NormalBooking instance
            System.out.println("No NormalBooking instance available.");
        }
        updateTotalAmount();
        // There's no need to add cartServiceItem here as it's done within the valid condition block
    }

    private void fetchNormalBookingsFromDatabase(int bookingId) {
        String serverUrl = getResources().getString(R.string.server_url);
        String allNormalBookingsApiUrl = serverUrl + "hazirjanab/fetch_bookingproducts.php"; // Replace with your server URL for fetching normal booking data

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
                                pList.clear();

                                // Process each normal booking in the array
                                for (int i = 0; i < normalBookingsArray.length(); i++) {
                                    JSONObject bookingObject = normalBookingsArray.getJSONObject(i);
                                    int productID = bookingObject.getInt("product_id");
                                    int quantity = bookingObject.getInt("quantity");


                                    // Construct a NormalBooking object (assuming you have a constructor)
                                    productorders productorder = new productorders(productID,quantity);
                                    pList.add(productorder);

                                    // Log the fetched booking
                                    Log.d("NormalBookingLog", "Fetched Normal Booking: " + productorder.toString());
                                }
                                //userbookingsdataholder.setOrderList(bookingList);
                                // Notify the adapter that the data set has changed to refresh the RecyclerView
                                if (cartProductAdapter != null) {
                                    cartProductAdapter.notifyDataSetChanged();
                                }
                                initializeProductList();
                            } else {
                                initializeProductList();
                                // Handle the failure case here
                                String message = responseObject.getString("Message");
                                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(getApplicationContext(), "Error parsing JSON response: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("booking_id", String.valueOf(bookingId));
                return params;
            }
        };

        queue.add(stringRequest);
    }


    @Override
    public void onItemClicked(int position) {
//        Toast.makeText(this, "Item Clicked", Toast.LENGTH_SHORT).show();
    }
//    private void uploadData(NormalBooking normalBooking, UploadCallback callback) {
//        // Extract parameters from normalBooking instance
//        final String userId = String.valueOf(normalBooking.getUserId());
//        final String serviceId = String.valueOf(normalBooking.getServiceId());
//        final String vendorId = String.valueOf(normalBooking.getVendorId());
//        final String city = normalBooking.getCity();
//        final String address = normalBooking.getAddress();
//        final String date = normalBooking.getDate();
//        final String time = normalBooking.getTime();
//        final String description = normalBooking.getDescription();
//        final String type = normalBooking.getType();
//        final String status = normalBooking.getStatus();
//
//        // Encode the image byte array to Base64
//        final String encodedImage = Base64.encodeToString(normalBooking.getPicture(), Base64.DEFAULT);
//
//        // Define the server endpoint to your API
//        String serverUrl = getResources().getString(R.string.server_url);
//        String fullApiUrl = serverUrl + "hazirjanab/post_order.php"; // Replace with your actual API endpoint
//
//        // Create the request
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, fullApiUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("UploadData", "Volley onResponse: " + response); // Log the response
//                        Toast.makeText(YourCart.this, "Booking Completed successfully", Toast.LENGTH_SHORT).show();
//                        // Handle the server response here
//                        JSONObject jsonResponse = null;
//                        try {
//                            jsonResponse = new JSONObject(response);
//                            bookingID = jsonResponse.getInt("booking_id");
//                            Log.d("UploadData", "Booking ID: " + bookingID);
//                            callback.onUploadSuccess();
//
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("UploadData", "Volley error: " + error.toString()); // Log the error
//
//                Toast.makeText(YourCart.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                callback.onUploadFailure(error.toString());
//                // Handle errors here
//            }
//        }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_id", userId);
//                params.put("service_id", serviceId);
//                params.put("vendor_id", vendorId);
//                params.put("city", city);
//                params.put("address", address);
//                params.put("date", date);
//                params.put("time", time);
//                params.put("description", description);
//                params.put("type", type);
//                params.put("status", status);
//                params.put("image", encodedImage); // Ensure this is the actual Base64 encoded image string
//                Log.d("UploadData", "Parameters: " + params.toString());
//                return params;
//            }
//        };
//
//        // Add the request to the RequestQueue
//        Volley.newRequestQueue(this).add(stringRequest);
//    }
//    private void uploadProductOrders(final List<productorders> productOrders, final int bookingId) {
//        String serverUrl = getResources().getString(R.string.server_url);
//        String fullApiUrl = serverUrl + "hazirjanab/post_bookings.php";
//
//        final JSONArray bookingsArray = new JSONArray();
//        for (productorders order : productOrders) {
//            JSONObject bookingObject = new JSONObject();
//            try {
//                bookingObject.put("booking_id", bookingId);
//                bookingObject.put("product_id", order.getProductID());
//                bookingObject.put("quantity", order.getQuantity());
//                bookingsArray.put(bookingObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        final JSONObject bookingsObject = new JSONObject();
//        try {
//            bookingsObject.put("bookings", bookingsArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        final String bookingsString = bookingsObject.toString();
//
//        //final String bookingsString = bookingsArray.toString();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, fullApiUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("UploadProductOrders", "Volley onResponse: " + response);
//                        Toast.makeText(YourCart.this, "Product orders uploaded successfully", Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("UploadProductOrders", "Volley error: " + error.toString());
//                Toast.makeText(YourCart.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//        ) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return bookingsString.getBytes(StandardCharsets.UTF_8);
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//        };
//
//        Volley.newRequestQueue(this).add(stringRequest);
//    }

}