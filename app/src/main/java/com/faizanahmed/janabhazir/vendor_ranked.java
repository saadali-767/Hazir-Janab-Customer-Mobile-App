package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.faizanahmed.janabhazir.Vendor;
import com.faizanahmed.janabhazir.VendorAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class vendor_ranked extends AppCompatActivity {
    private List<Vendor> vendorList; // Declare vendorList as an instance variable

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_ranked);

        Intent intent = getIntent();

        // Retrieve the data
        Integer serviceId = getIntent().getIntExtra("serviceId", -100);
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceHourlyRate = getIntent().getStringExtra("serviceHourlyRate");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        String serviceCity = getIntent().getStringExtra("serviceCity");
        String serviceCategory = getIntent().getStringExtra("serviceCategory");
        String serviceImageUrl = getIntent().getStringExtra("serviceImageUrl");
        String serviceVideoUrl = getIntent().getStringExtra("serviceVideoUrl");
        String serviceType = getIntent().getStringExtra("serviceType");

        String vendor_name = getIntent().getStringExtra("vendor_name");
//        Log.d("IntentDatareceivedfrom search", "Service ID: " + serviceId);
//        Log.d("IntentDatareceivedfrom search", "Service Name: " + serviceName);
//        Log.d("IntentDatareceivedfrom search", "Service Hourly Rate: " + serviceHourlyRate);
//        Log.d("IntentDatareceivedfrom search", "Service Description: " + serviceDescription);
//        Log.d("IntentDatareceivedfrom search", "Service City: " + serviceCity);
//        Log.d("IntentDatareceivedfrom search", "Service Category: " + serviceCategory);
//        Log.d("IntentDatareceivedfrom search", "Service Image URL: " + serviceImageUrl);
//        // Log.d("IntentData", "Service Video URL: " + serviceVideoUrl); // Uncomment if needed
//        Log.d("IntentDatareceivedfrom search", "Service Type: " + serviceType);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_vendors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vendorList = initializeVendors(); // Corrected line

        VendorAdapter adapter = new VendorAdapter(this,vendorList,  serviceId, serviceName, serviceCity, serviceDescription, serviceHourlyRate,serviceType,serviceImageUrl,serviceCategory);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        for (Vendor vendor : vendorList) {
            fetchAndProcessVendorData(vendor, adapter);
        }
    }

    private List<Vendor> initializeVendors() {
        List<Vendor> vendorList = new ArrayList<>();

        // Dummy data for vendors and their reviews
        List<String> reviewsVendor1 = Arrays.asList("....", "The vendor was punctual and completed the task within the agreed time.", "Review 3 for Vendor 1");
        vendorList.add(new Vendor("mujeeb", "vendor_image_1", reviewsVendor1));

        List<String> reviewsVendor2 = Arrays.asList("....", "Service provider's quality of work was mediocre, but they were punctual.", "Review 3 for Vendor 2");
        vendorList.add(new Vendor("abdullah", "vendor_image_2", reviewsVendor2));

        // Add more vendors and their reviews as needed
        return vendorList;
    }

    private void fetchAndProcessVendorData(final Vendor vendor, final VendorAdapter adapter) {
        AtomicInteger completedRequests = new AtomicInteger();
        for (String review : vendor.getReviews()) {
            JSONObject postData = new JSONObject();
            try {
                postData.put("text", review);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }


            String url = "http://172.17.68.86:5000/predict";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, postData, response -> {
                try {
                    // Update vendor's scores
                    vendor.addReviewScores(
                            response.getInt("Timeliness"),
                            response.getInt("Quality_of_Service"),
                            response.getInt("Expertise_and_Knowledge")
                    );

                    Log.d("VendorScoreUpdate", "Updated Vendor: " + vendor.getName() + ", Timeliness: " + vendor.getTimelinessScore() + ", Quality: " + vendor.getQualityOfServiceScore() + ", Expertise: " + vendor.getExpertiseAndKnowledgeScore());
                    int currentCompletedRequests = completedRequests.incrementAndGet();
                    Log.d("VendorRequest", "Completed Requests: " + currentCompletedRequests);
                    if (completedRequests.incrementAndGet() == totalReviewCount(vendorList)) {
                        Log.d("VendorSorting", "Before sorting:");
                        for (Vendor v : vendorList) {
                            Log.d("VendorSorting", "Vendor: " + v.getName() + ", Total Score: " + v.getTotalScore());
                        }

                        Collections.sort(vendorList);

                        Log.d("VendorSorting", "After sorting:");
                        for (Vendor v : vendorList) {
                            Log.d("VendorSorting", "Vendor: " + v.getName() + ", Total Score: " + v.getTotalScore());
                        }
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
                error.printStackTrace();
                completedRequests.incrementAndGet();
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            requestQueue.add(jsonObjectRequest);
        }
    }

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
        intent.putExtra("vendor_name", clickedVendor.getName());

        // Add other details as needed
        startActivity(intent);
    }
}