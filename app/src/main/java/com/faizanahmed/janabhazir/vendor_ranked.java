package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.faizanahmed.janabhazir.Vendor;
import com.faizanahmed.janabhazir.VendorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class vendor_ranked extends AppCompatActivity {
    private List<Vendor> vendorList = new ArrayList<>();
    private VendorAdapter adapter;
    private AlertDialog loadingDialog;


    private RequestQueue requestQueue;
    Integer serviceId;
    String cat;
    String vendor_id;
    String serviceName;String serviceHourlyRate;
    String serviceDescription;
    String serviceCity;
    String serviceCategory;
    String serviceImageUrl;
    String serviceVideoUrl;
    String serviceType;
    ImageView ivSupport;

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
        setContentView(R.layout.activity_vendor_ranked);

        Intent intent = getIntent();

        // Retrieve the data
         serviceId = getIntent().getIntExtra("serviceId", -100);
         serviceName = getIntent().getStringExtra("serviceName");
         serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
         serviceDescription = getIntent().getStringExtra("serviceDescription");
         serviceCity = getIntent().getStringExtra("serviceCity");
         serviceCategory = getIntent().getStringExtra("serviceCategory");
         serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
         serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
        serviceType = getIntent().getStringExtra("serviceType");

        String vendor_name = getIntent().getStringExtra("vendor_name");
//        Log.d("IntentDatareceivedfrom search", "Service ID: " + serviceId);
//        Log.d("IntentDatareceivedfrom search", "Service Name: " + serviceName);
//        Log.d("IntentDatareceivedfrom search", "Service Hourly Rate: " + serviceHourlyRate);
//        Log.d("IntentDatareceivedfrom search", "Service Description: " + serviceDescription);
//        Log.d("IntentDatareceivedfrom search", "Service City: " + serviceCity);
//        Log.d("IntentDatareceivedfrom search", "Service Category: " + serviceCategory);
//        Log.d("IntentDatareceivedfrom search", "Service Image URL: " + serviceImageUrl);
//        // Log.d("IntentData", "Service Video URL: " + serviceVideoUrl); // Uncomment if needed
    Log.d("IntentDatareceivedfrom search", "Service Type rece: " + serviceType);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_vendors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        vendorList = initializeVendors(); // Corrected line
//
//        VendorAdapter adapter = new VendorAdapter(this,vendorList,  serviceId, serviceName, serviceCity, serviceDescription, serviceHourlyRate,serviceType,serviceImageUrl,serviceCategory);
//        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        fetchVendorsFromDatabase(); // Fetch vendors from your database

//        for (Vendor vendor : vendorList) {
//            fetchAndProcessVendorData(vendor, adapter);
//        }
        ivSupport = findViewById(R.id.ivSupport);
        if(bookingdataholder.isBookingInstanceValid() || !productordersdataholder.getOrderList().isEmpty() ){
            ivSupport.setImageResource(R.drawable.fullcart);
        }
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(vendor_ranked.this, "Your cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(vendor_ranked.this, YourCart.class);
                startActivity(intent);
            }
        });
    }

    private void fetchVendorsFromDatabase() {
        showLoadingDialog();
        String serverUrl = getResources().getString(R.string.server_url);
        String url = serverUrl + "hazirjanab/fetch_vendors.php";
        //String url = "http://192.168.100.17:8080/hazirjanab/fetch_vendors.php"; // Replace with your actual endpoint

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, url, null,
                response -> {
                    Log.d("TAG", "fetchVendorsFromDatabase:1 "+response);
                    try {
                        JSONArray vendorsArray = response.getJSONArray("Vendors");
                        for (int i = 0; i < vendorsArray.length(); i++) {
                            JSONObject vendorObject = vendorsArray.getJSONObject(i);
                             vendor_id = vendorObject.getString("id");
                            String first_name = vendorObject.getString("first_name");
                            String last_name = vendorObject.getString("last_name");
                            String gender = vendorObject.getString("gender");
                            String CNIC = vendorObject.getString("CNIC");
                            String email = vendorObject.getString("email");
                            String phone_number = vendorObject.getString("phone_number");
                            String Address = vendorObject.getString("Address");
                            String category = vendorObject.optString("category"); // Use optString to avoid JSONException if the key does not exist
                            String base64Image = vendorObject.optString("picture"); // Changed from "base64Image" to "picture" to match your JSON
                            Log.d("v", "vendorid" + vendor_id);
                            Log.d("taf", "cat " + category);

                            if (category.equals(serviceCategory)) {
                                // Add the vendor to the list only if the categories match
                                vendorList.add(new Vendor(vendor_id,first_name, last_name, gender, CNIC, email, phone_number, Address, category, base64Image,null));
                            }

                        }

                        adapter = new VendorAdapter(this, vendorList, serviceId, serviceName, serviceCity, serviceDescription, serviceHourlyRate, serviceType, serviceImageUrl, serviceCategory);
                            RecyclerView recyclerView = findViewById(R.id.recycler_view_vendors);

                            recyclerView.setAdapter(adapter);
                        fetchAndAddDescriptions();




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                },
                error -> error.printStackTrace()
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.loading_dialog); // Assuming you have a 'loading_dialog.xml' layout in your 'layout' folder.
        builder.setCancelable(false); // Make dialog non-cancellable

        loadingDialog = builder.create();
        loadingDialog.show();
    }

    private void fetchAndAddDescriptions() {
        for (Vendor vendor : vendorList) {
            // Convert the integer ID to a string for the URL


            String vendorIdStr = String.valueOf(vendor.getID());
            fetchVendorDescription(vendorIdStr); // Pass the ID as a string to the method
        }
    }



    private void fetchVendorDescription(String vendorIdStr) {
        String serverUrl1 = getResources().getString(R.string.server_url);
        String descriptionUrl = serverUrl1 + "/hazirjanab/get_vendor_description.php?vendor_id=" + vendorIdStr;
       // String descriptionUrl = "http://192.168.100.132:8080/hazirjanab/get_vendor_description.php?vendor_id=" + vendorIdStr;
        JsonObjectRequest descriptionRequest = new JsonObjectRequest(
                Request.Method.GET, descriptionUrl, null,
                response -> {
                    try {
                        JSONArray descriptionsJsonArray = response.getJSONArray("descriptions");
                        List<String> newReviews = new ArrayList<>();
                        for (int i = 0; i < descriptionsJsonArray.length(); i++) {
                            newReviews.add(descriptionsJsonArray.getString(i));
                        }
                        // Update the vendor with the new reviews
                        for (Vendor vendor : vendorList) {
                            if (vendor.getID().equals(vendorIdStr)) { // Use equals for String comparison
                                vendor.updateReviews(newReviews);
                                Log.d("VendorDescriptions", "Descriptions updated for Vendor ID " + vendorIdStr + ": " + newReviews);
                                // If necessary, reprocess scores or perform related updates here.
                                processAndScoreReviews(vendor);

                                break; // Exit the loop once the matching vendor is updated
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );
        requestQueue.add(descriptionRequest);
    }

    private void sortVendorsByRating() {
        Collections.sort(vendorList, new Comparator<Vendor>() {
            @Override
            public int compare(Vendor v1, Vendor v2) {
                // Sorting in descending order of rating.
            //    Log.d("sortVendorsByRating", "compare: reached here");
                return Float.compare(v2.getOverallRating(), v1.getOverallRating());
            }
        });
    }

    private void logVendorScores() {
        for (Vendor v : vendorList) {
            Log.d("VendorPreSortScore", "Vendor: " + v.getFirst_name() + ", Overall Rating: " + v.getOverallRating());
        }

    }

    // Inside your existing fetchVendorDescription or similar method
    private void processAndScoreReviews(Vendor vendor) {
      //  Log.d("VendorScoreProcessing", "Processing scores for Vendor: " + vendor.getFirst_name());
        String serverUrl4 = getResources().getString(R.string.predict);
        String serverUrl = serverUrl4 + "predict";
        Log.d("TAG", "processAndScoreReviews: "+serverUrl);
        // Assuming you have a list of reviews for each vendor
        for (String review : vendor.getReviews()) {
            JSONObject postData = new JSONObject();
            try {
                postData.put("text", review);
                postData.put("ngram_size", 3); // Or any other ngram size you prefer

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        serverUrl, // Your Flask endpoint
                        postData,
                        response -> {
                            try {
                                Log.d("predict response", "processAndScoreReviews: "+response);
                                // Extracting scores from response
                                int timeliness = response.getInt("Timeliness");
                                int quality = response.getInt("Quality_of_Service");
                                int expertise = response.getInt("Expertise_and_Knowledge");
                                Log.d("score2", "timeliness: "+timeliness+vendor.getFirst_name());
                                Log.d("score2", "Quality_of_Service: "+quality+vendor.getFirst_name());
                                Log.d("score2", "Expertise_and_Knowledge: "+expertise+vendor.getFirst_name());

                                // Update vendor scores
                                vendor.addReviewScores(timeliness, quality, expertise);
                                vendor.calculateFinalRatings();

                                // After all reviews are processed, sort vendors and update RecyclerView
                                sortAndRefreshVendors();



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        },
                        error -> error.printStackTrace()
                );
                requestQueue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("VendorScoreUpdate", "Updated scores for Vendor: " + vendor.getFirst_name() + ", Timeliness: " + vendor.getTimelinessScore() + ", Quality: " + vendor.getQualityOfServiceScore() + ", Expertise: " + vendor.getExpertiseAndKnowledgeScore());

        }


        Log.d("VendorScoreUpdate", "Updated scores for Vendor: " + vendor.getFirst_name() + ", Timeliness: " + vendor.getTimelinessScore() + ", Quality: " + vendor.getQualityOfServiceScore() + ", Expertise: " + vendor.getExpertiseAndKnowledgeScore());

    }

    private void updateVendorListAndSort() {
        // This method should be called after all vendor scores have been updated
       // Log.d("updateVendorListAndSort", "updateVendorListAndSort:reached her e ");
        sortVendorsByRating(); // Or sortAndRefreshVendors(); based on your code structure
        if (adapter != null) {
          //  Log.d("adapter", "updateVendorListAndSort:reached her e ");

            adapter.notifyDataSetChanged();
        }

    }

    private void sortAndRefreshVendors() {
        // Ensure this uses the intended sorting logic
        Collections.sort(vendorList, new Comparator<Vendor>() {
            @Override
            public int compare(Vendor v1, Vendor v2) {
                // This ensures descending order sorting by overall rating
                return Float.compare(v2.getOverallRating(), v1.getOverallRating());
            }
        });
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Notify changes to the adapter
        }
        logVendorScores(); // Logging for debugging

    }





//    private void fetchAndProcessVendorData(final Vendor vendor, final VendorAdapter adapter) {
//        AtomicInteger completedRequests = new AtomicInteger();
//        for (String review : vendor.getReviews()) {
//            JSONObject postData = new JSONObject();
//            try {
//                postData.put("text", review);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                continue;
//            }
//
//
//            String url = "http://127.0.0.1:5000/predict";
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                    Request.Method.POST, url, postData, response -> {
//                try {
//                    // Update vendor's scores
//                    vendor.addReviewScores(
//                            response.getInt("Timeliness"),
//                            response.getInt("Quality_of_Service"),
//                            response.getInt("Expertise_and_Knowledge")
//                    );
//
//                    Log.d("VendorScoreUpdate", "Updated Vendor: " + vendor.getFirst_name() + ", Timeliness: " + vendor.getTimelinessScore() + ", Quality: " + vendor.getQualityOfServiceScore() + ", Expertise: " + vendor.getExpertiseAndKnowledgeScore());
//                    int currentCompletedRequests = completedRequests.incrementAndGet();
//                    Log.d("VendorRequest", "Completed Requests: " + currentCompletedRequests);
//                    if (completedRequests.incrementAndGet() == totalReviewCount(vendorList)) {
//                        Log.d("VendorSorting", "Before sorting:");
//                        for (Vendor v : vendorList) {
//                            Log.d("VendorSorting", "Vendor: " + v.getFirst_name() + ", Total Score: " + v.getTotalScore());
//                        }
//
//                        Collections.sort(vendorList);
//
//                        Log.d("VendorSorting", "After sorting:");
//                        for (Vendor v : vendorList) {
//                            Log.d("VendorSorting", "Vendor: " + v.getFirst_name() + ", Total Score: " + v.getTotalScore());
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//
//
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }, error -> {
//                error.printStackTrace();
//                completedRequests.incrementAndGet();
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//            };
//
//            requestQueue.add(jsonObjectRequest);
//        }
//    //    sortVendorsByRating();
//      //  sortVendorsByRating(); // Sort the vendors based on their ratings.
//        //adapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed.
//    }

    private int totalReviewCount(List<Vendor> vendorList) {
        int total = 0;
        if (vendorList != null) {
            for (Vendor vendor : vendorList) {
                total += vendor.getReviews().size();
            }
        }
        return total;
    }

    public void onItemClicked(int position) {
        // Start a new activity with the clicked vendor's details
        Vendor clickedVendor = vendorList.get(position);
        Intent intent = new Intent(this, MainActivity_1.class);
        intent.putExtra("vendor_name", clickedVendor.getFirst_name());

        // Add other details as needed
        startActivity(intent);
    }




}