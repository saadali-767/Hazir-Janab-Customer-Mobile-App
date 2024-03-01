package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceType_2 extends AppCompatActivity {

    DrawerLayout SideBarDrawer;
    ImageView SideBarMenu;
    NavigationView NavigationDrawer1, NavigationDrawer2;
    List<Item> itemList;
    List<Product> productList;
    //List<Item> filteredList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_type_2);
        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
        productList = new ArrayList<>();
        itemList = new ArrayList<>();
       // filteredList = new ArrayList<>();
        fetchItemsFromDatabase();
        fetchProductsFromDatabase();
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

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServiceType_2.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServiceType_2.this, Support_11.class);
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
    private void fetchItemsFromDatabase() {
        String serverUrl = getResources().getString(R.string.server_url);
        String allServicesApiUrl = serverUrl + "hazirjanab/item.php";
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
                Toast.makeText(ServiceType_2.this, "Failed to fetch items: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }
}