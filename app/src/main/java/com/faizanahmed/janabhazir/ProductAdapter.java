package com.faizanahmed.janabhazir;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;
    RecyclerViewInterface recyclerViewInterface;


    public ProductAdapter(Context context, List<Product> productList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.productList = productList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setFilteredList(List<Product> filteredList){
        this.productList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.template_all_products, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        // Bind data to views within the car
        if(holder.itemNameTextView == null){
            Log.d("ItemNameTextView", "NULL");
        }else{
            holder.itemNameTextView.setText(product.getName());
        }

        if (holder.priceTextView == null) {
            Log.d("PriceTextView", "NULL");
        } else {
            holder.priceTextView.setText("PKR " + product.getPrice());
        }
        if (holder.descriptionTextView == null) {
            Log.d("DescriptionTextView", "NULL");
        } else {
            holder.descriptionTextView.setText(product.getDescription());
        }
        String img = "ic_service"+String.valueOf(product.getId());

        int imageResId = context.getResources().getIdentifier(img, "drawable", context.getPackageName());

        if (imageResId != 0) {
            holder.cardViewImageView.setImageResource(imageResId);
        } else {
            Log.d("Image", "Image not found");
        }

    }

    private byte[] readFileToByteArray(String filePath) {
        File file = new File(filePath);
        byte[] fileContent = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView priceTextView;
        TextView descriptionTextView;
        TextView cityTextView;
        ImageView cardViewImageView;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.CardViewItemNameText1);
            priceTextView = itemView.findViewById(R.id.CardViewHourText1);
            descriptionTextView = itemView.findViewById(R.id.CardViewDescriptionText1);
            cardViewImageView = itemView.findViewById(R.id.CardViewImage1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }
}