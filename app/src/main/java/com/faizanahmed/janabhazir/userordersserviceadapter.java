package com.faizanahmed.janabhazir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userordersserviceadapter extends RecyclerView.Adapter<userordersserviceadapter.ViewHolder> {
    private List<CartServiceItem> cartServiceItems; // List of CartServiceItem
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    public userordersserviceadapter(Context context, List<CartServiceItem> cartServiceItems, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.cartServiceItems = cartServiceItems;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.serviceorder, parent, false);
        return new ViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartServiceItem cartServiceItem = cartServiceItems.get(position);
        NormalBooking booking = cartServiceItem.getBooking();
        Item serviceItem = cartServiceItem.getServiceItem();


        holder.tvServiceName.setText(serviceItem.getItemName());
        holder.tvServiceMan.setText(serviceItem.getItemCategory());
        holder.tvServiceType.setText(serviceItem.getItemType());
        holder.tvServiceTime.setText(booking.getTime()); // Assuming getTime() returns a String
        String priceText = "PKR " + serviceItem.getItemHourlyRate() + "/Hr"; // Format price text as needed
        holder.tvServicePrice.setText(priceText);
        holder.tvServiceStatus.setText(booking.getStatus());
        holder.tvServiceDate.setText(booking.getDate());

    }

    @Override
    public int getItemCount() {
        return cartServiceItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivServiceImage;
        TextView tvServiceMan, tvServiceName, tvServiceType, tvServiceTime, tvServicePrice,tvServiceDate,tvServiceStatus;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ivServiceImage = itemView.findViewById(R.id.ivServiceImage);
            tvServiceMan = itemView.findViewById(R.id.tvServiceMan);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvServiceTime = itemView.findViewById(R.id.tvServiceTime); // Added binding
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice); // Added binding
            tvServiceDate=itemView.findViewById(R.id.tvServiceDate);
            tvServiceStatus=itemView.findViewById(R.id.tvServiceStatus);

            itemView.setOnClickListener(v -> recyclerViewInterface.onItemClicked(getAdapterPosition()));
        }
    }
}
