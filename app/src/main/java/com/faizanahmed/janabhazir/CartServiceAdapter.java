package com.faizanahmed.janabhazir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartServiceAdapter extends RecyclerView.Adapter<CartServiceAdapter.ViewHolder> {
    private List<CartServiceItem> cartServiceItems; // List of CartServiceItem
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    private final YourCart activity;

    public CartServiceAdapter(Context context, List<CartServiceItem> cartServiceItems, RecyclerViewInterface recyclerViewInterface,YourCart activity) {
        this.context = context;
        this.cartServiceItems = cartServiceItems;
        this.recyclerViewInterface = recyclerViewInterface;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_service_list, parent, false);
        return new ViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartServiceItem cartServiceItem = cartServiceItems.get(position);
        NormalBooking booking = cartServiceItem.getBooking();
        Item serviceItem = cartServiceItem.getServiceItem();

        // Set the service details
        holder.tvServiceName.setText(serviceItem.getItemName());
        holder.tvServiceMan.setText(serviceItem.getItemCategory());
        holder.tvServiceType.setText(serviceItem.getItemType());
        holder.tvServiceTime.setText(booking.getTime()); // Assuming getTime() returns a String
        String priceText = "PKR " + serviceItem.getItemHourlyRate() + "/Hr"; // Format price text as needed
        holder.tvServicePrice.setText(priceText);

        // Example click listener for remove action, adjust as needed
        holder.ivRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Remove Product")
                        .setMessage("Are you sure you want to remove this service? Products in the cart will also be removed")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the item from the list
                                cartServiceItems.remove(position);
                                bookingdataholder.clearNormalBookingInstance();
                                // Notify the adapter of the item removed
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, cartServiceItems.size());
                                productordersdataholder.clearOrderList();
                                activity.initializeServiceItemList();
                                activity.initializeProductList();
                                productordersdataholder.clearOrderList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartServiceItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivServiceImage, ivRemoveProduct;
        TextView tvServiceMan, tvServiceName, tvServiceType, tvServiceTime, tvServicePrice;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ivServiceImage = itemView.findViewById(R.id.ivServiceImage);
            ivRemoveProduct = itemView.findViewById(R.id.ivRemoveProduct);
            tvServiceMan = itemView.findViewById(R.id.tvServiceMan);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvServiceTime = itemView.findViewById(R.id.tvServiceTime); // Added binding
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice); // Added binding

            itemView.setOnClickListener(v -> recyclerViewInterface.onItemClicked(getAdapterPosition()));
        }
    }
}
