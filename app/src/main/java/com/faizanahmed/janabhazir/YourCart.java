package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YourCart extends AppCompatActivity implements RecyclerViewInterface {
    double totalAmount = 0;

    RecyclerView rvProduct, rvService;
    CartProductAdapter cartProductAdapter;
    CartServiceAdapter cartServiceAdapter;
    List<Product> productList;
    List<CartServiceItem> serviceItemList;
    List<productorders> orders;
    LinearLayout service_layout,product_layout;
    RelativeLayout payment_layout,buttons_layout;
    CardView total_amount;
    TextView emptycart;
    int bookingID;
    Button BtnNext;
    TextView ivtotal;
    public interface UploadCallback {
        void onUploadSuccess();
        void onUploadFailure(String error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
        BtnNext=findViewById(R.id.BtnNext);
        ivtotal=findViewById(R.id.tvTotalAmount);
        service_layout=findViewById(R.id.rlService);
        product_layout=findViewById(R.id.rlProduct);
        payment_layout=findViewById(R.id.rlPayment);
        total_amount=findViewById(R.id.cvTotalAmount);
        buttons_layout=findViewById(R.id.rlBottomButtons);
        emptycart=findViewById(R.id.emptycart);
        serviceItemList = new ArrayList<>();


        // Initialize and setup product list and adapter
        rvProduct = findViewById(R.id.rvProductList);
        productList = new ArrayList<>();
        initializeProductList();
         // This method will populate the productList
        cartProductAdapter = new CartProductAdapter(YourCart.this,orders, productList, this,YourCart.this);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        rvProduct.setAdapter(cartProductAdapter);

        // Initialize and setup service item list and adapter
        rvService = findViewById(R.id.rvServiceList);


        initializeServiceItemList(); // This method will populate the serviceItemList
        cartServiceAdapter = new CartServiceAdapter(YourCart.this, serviceItemList, this,YourCart.this);
        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setAdapter(cartServiceAdapter);
        updateTotalAmount();

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bookingdataholder.isBookingInstanceValid()) {
                    // Proceed to the next activity since a service is selected
                    Intent intent = new Intent(YourCart.this, confirmorder.class);
                    intent.putExtra("totalAmount",totalAmount);
                    startActivity(intent);
                } else {
                    // No service selected, show a toast message
                    Toast.makeText(YourCart.this, "Please select a service first ", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public void updateTotalAmount() {
         totalAmount = 0;

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


    public void initializeProductList() {


        // Clear the existing productList to ensure it's empty before adding new items
        productList.clear();
        if(productordersdataholder.getOrderList().isEmpty() && !bookingdataholder.isBookingInstanceValid()){
            product_layout.setVisibility(View.GONE);
            service_layout.setVisibility(View.GONE);
            payment_layout.setVisibility(View.GONE);
            total_amount.setVisibility(View.GONE);
            buttons_layout.setVisibility(View.GONE);
            emptycart.setVisibility(View.VISIBLE);

        } else if (productordersdataholder.getOrderList().isEmpty()){
            product_layout.setVisibility(View.GONE);
        } else {


            // Get the list of product orders from the ProductOrderManager
            orders = productordersdataholder.getOrderList();

            // Iterate over each order
            for (productorders order : orders) {
                // Use the product ID from the order to find the corresponding product
                Product product = ProductDataHolder.findProductById(order.getProductID());

                if (product != null) {
                    // Create a new product instance with the order's quantity
                    Product orderedProduct = new Product(product.getId(), product.getName(), product.getDescription(), order.getQuantity(), product.getCategory(), product.getPrice(), product.isAvailability());

                    // Add the product with the updated quantity to the productList
                    productList.add(orderedProduct);
                }
            }
        }
        updateTotalAmount();
        // Now productList is initialized with products from the orders
    }


    public void initializeServiceItemList() {
        serviceItemList.clear();
        if(!bookingdataholder.isBookingInstanceValid()){
            service_layout.setVisibility(View.GONE);
        }
        else{


        NormalBooking normalBooking = bookingdataholder.getNormalBookingInstance();
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
        }
    updateTotalAmount();
        // There's no need to add cartServiceItem here as it's done within the valid condition block
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "Item Clicked", Toast.LENGTH_SHORT).show();
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