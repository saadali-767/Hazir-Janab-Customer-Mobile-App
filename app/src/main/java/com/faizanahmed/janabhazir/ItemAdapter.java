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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> itemList;
    private Context context;
    RecyclerViewInterface recyclerViewInterface;


    public ItemAdapter(Context context, List<Item> itemList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.itemList = itemList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setFilteredList(List<Item> filteredList){
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.template_all_services, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        // Bind data to views within the car
        holder.itemNameTextView.setText(item.getItemName());
        holder.hourlyRateTextView.setText("PKR " + item.getItemHourlyRate() + "/hr");
        holder.descriptionTextView.setText(item.getItemDescription());
        holder.cityTextView.setText(item.getItemCity());
        //trying
//        String filePath = "R.drawable.ic_launcher_background";
//        byte[] imageBytes = readFileToByteArray(filePath);
//        Toast.makeText(context, "Image bytes: " + imageBytes, Toast.LENGTH_SHORT).show();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        holder.cardViewImageView.setImageBitmap(bitmap);
        String img = "ic_service"+String.valueOf(item.getItemId());

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
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView hourlyRateTextView;
        TextView descriptionTextView;
        TextView cityTextView;
        ImageView cardViewImageView;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.CardViewItemNameText1);
            hourlyRateTextView = itemView.findViewById(R.id.CardViewHourText1);
            descriptionTextView = itemView.findViewById(R.id.CardViewDescriptionText1);
            cityTextView = itemView.findViewById(R.id.CardViewCityText1);
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