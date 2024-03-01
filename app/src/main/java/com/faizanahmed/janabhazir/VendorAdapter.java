package com.faizanahmed.janabhazir;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ViewHolder> {

    private List<Vendor> vendorList;
    private View.OnClickListener mOnClickListener;


    private Context mContext;
    private Integer serviceId;
    private String serviceName;
    private String serviceCity;
    private String serviceDescription;
    private String serviceType;
    private String serviceImageUrl;
    private String  serviceCategory;
    private String serviceHourlyRate; // Add a context to use for starting a new Activity

    public VendorAdapter(Context context, List<Vendor> vendorList,Integer serviceId,String serviceName,String serviceCity,String serviceDescription,String serviceHourlyRate,String serviceType,String serviceImageUrl,String serviceCategory) {
        this.vendorList = vendorList;
        this.mContext = context;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceCity = serviceCity;
        this.serviceDescription = serviceDescription;
        this.serviceType = serviceType;
        this.serviceHourlyRate = serviceHourlyRate;
        this.serviceImageUrl = serviceImageUrl;
        this.serviceCategory = serviceCategory;





    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vendor vendor = vendorList.get(position);
        holder.vendorName.setText(vendor.getName());
        holder.ratingBarTimeliness.setRating(vendor.getAverageTimelinessScore());
        holder.ratingBarQuality.setRating(vendor.getAverageQualityOfServiceScore());
        holder.ratingBarExpertise.setRating(vendor.getAverageExpertiseScore());
        holder.text_vendor_occupation.setText(serviceCategory);
        String imageName = "ic_vendor"+vendor.getName().toLowerCase(); // Convert to lower case

        //Toast.makeText(mContext, "Image name: " + imageName, Toast.LENGTH_SHORT).show();
        int imageResId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        if (imageResId != 0) {
            holder.imageVendor.setImageResource(imageResId);
        } else {
            Log.d("VendorAdapter", "Image not found for: " + imageName);
            holder.imageVendor.setImageResource(R.drawable.vendor_image); // Fallback image
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,serviceDetails.class); // Replace DetailActivity with the actual Activity class you want to navigate to
            intent.putExtra("vendor_name", vendorList.get(position).getName()); // Assuming there's an ID or some identifier for each vendor
            intent.putExtra("serviceId", serviceId);
            intent.putExtra("serviceName", serviceName);
            intent.putExtra("serviceCity", serviceCity);
            intent.putExtra("serviceDescription", serviceDescription);
            intent.putExtra("serviceHourlyRate", serviceHourlyRate);
            intent.putExtra("serviceType", serviceType);
            intent.putExtra("serviceImageUrl",serviceImageUrl);
            intent.putExtra("serviceCategory",serviceCategory);

            Log.d("intent being sent from vendor ", "Service ID: " + serviceId);
            Log.d("intent being sent from vendor  search", "Service Name: " + serviceName);
            Log.d("intent being sent from vendor  search", "Service Hourly Rate: " + serviceHourlyRate);
            Log.d("intent being sent from vendor  search", "Service Description: " + serviceDescription);
            Log.d("intent being sent from vendor  search", "Service City: " + serviceCity);
            mContext.startActivity(intent);
        });

        // Load the image into the ImageView. You can use a library like Glide or Picasso.
        // Glide.with(holder.itemView.getContext()).load(vendor.getImageUrl()).into(holder.vendorImage);
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vendorImage;
        TextView vendorName;
        RatingBar ratingBarTimeliness;
        RatingBar ratingBarQuality;
        RatingBar ratingBarExpertise;
        CircleImageView imageVendor;
        TextView text_vendor_occupation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vendorImage = itemView.findViewById(R.id.image_vendor);
            vendorName = itemView.findViewById(R.id.text_vendor_name);
            ratingBarTimeliness = itemView.findViewById(R.id.ratingBarTimeliness);
            ratingBarQuality = itemView.findViewById(R.id.ratingBarQuality);
            ratingBarExpertise = itemView.findViewById(R.id.ratingBarExpertise);
            imageVendor = itemView.findViewById(R.id.image_vendor);
            text_vendor_occupation=itemView.findViewById(R.id.text_vendor_occupation);

        }
    }
}
