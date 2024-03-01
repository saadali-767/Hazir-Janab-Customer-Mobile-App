package com.faizanahmed.janabhazir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private List<productorders> orders;
    private Context context;
    RecyclerViewInterface recyclerViewInterface;
    private final YourCart activity;

    public CartProductAdapter(Context context,List<productorders> orders, List<Product> productList, RecyclerViewInterface recyclerViewInterface, YourCart activity) {
        this.context= context;
        this.orders=orders;
        this.productList = productList;
        this.recyclerViewInterface = recyclerViewInterface;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_product_list, parent, false);
        return new ProductViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (productList == null || productList.size() <= position || orders == null || orders.size() <= position) {
            Log.e("CartProductAdapter", "The data set is not valid.");
            return;
        }
        Product product = productList.get(position);
        productorders productorders=orders.get(position);

        byte[] imageBytes = product.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.ivProduct.setImageBitmap(bitmap);
        } else {
            holder.ivProduct.setImageResource(R.drawable.ic_product);
        }

        holder.ivRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show confirmation dialog
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Remove Product")
                        .setMessage("Are you sure you want to remove this product?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the item from the list
                                productList.remove(position);
                                productordersdataholder.removeProductOrder(productorders.getProductID());
                                // Notify the adapter of the item removed
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, productList.size());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        holder.tvTitle.setText(product.getName());
        holder.tvPrice.setText("PKR " + product.getPrice() + "/-");
        holder.tvQuantity.setText("x" + productorders.getQuantity());
        holder.txtQuantity.setText(String.valueOf(productorders.getQuantity()));
        holder.tvTotalPrice.setText("PKR " + product.getPrice() * productorders.getQuantity() + "/-");
        holder.quantity = productorders.getQuantity();
        Log.d("Quantity", String.valueOf(holder.quantity));
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.quantity < 10) {
                    holder.quantity++;
                    Log.d("Quantity increased", String.valueOf(holder.quantity));
                    holder.txtQuantity.setText(String.valueOf(holder.quantity));
                    holder.tvQuantity.setText("x" + holder.quantity);
                    holder.tvTotalPrice.setText("PKR " + product.getPrice() * holder.quantity + "/-");
                    productorders.setQuantity(holder.quantity);
                    //activity.updateTotalAmount();
                    notifyItemChanged(position);
                    activity.initializeProductList();

                    //notifyDataSetChanged();

                    //notifyItemRangeChanged(position, productList.size());
                }
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.quantity> 1) {
                    holder.quantity--;
                    holder.txtQuantity.setText(String.valueOf(holder.quantity));
                    holder.tvQuantity.setText("x" + holder.quantity);
                    holder.tvTotalPrice.setText("PKR " + product.getPrice() * holder.quantity + "/-");
                    productorders.setQuantity(holder.quantity);
                    //activity.updateTotalAmount();
                    //notifyItemRangeChanged(position, productList.size());
                    notifyItemChanged(position);
                    activity.initializeProductList();

                   // notifyDataSetChanged();
                }
            }
        });

        // Set other views in the card as needed
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, ivRemoveProduct;
        TextView tvTitle, tvPrice, tvQuantity, tvTotalPrice, txtQuantity;
        Button btnIncrease, btnDecrease;
        int quantity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProductImage);
            ivRemoveProduct = itemView.findViewById(R.id.ivRemoveProduct);
            tvTitle = itemView.findViewById(R.id.tvProductTitle);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalProductPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
