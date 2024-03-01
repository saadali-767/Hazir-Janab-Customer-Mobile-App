package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AllTrendingServices extends AppCompatActivity implements RecyclerViewInterface{

        ItemAdapter adapter;
        RecyclerView rv;
        List<Item> itemList;
        DrawerLayout SideBarDrawer;
        ImageView SideBarMenu;
        NavigationView NavigationDrawer1, NavigationDrawer2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.all_trending_services);

//



            rv = findViewById(R.id.rv);

            itemList = new ArrayList<>();
//            itemList.add(new Item("1. Car Cleaning", "500", "We will clean your car", "Islamabad", "Mechanic", "image1.jpg", "video1.mp4"));
//            itemList.add(new Item("2. Plumbing Services", "1000", "Professional plumbing solutions", "Lahore", "Plumber", "image2.jpg", "video2.mp4"));
//            itemList.add(new Item("3. Electrical Repairs", "2000", "Fixing electrical issues", "Karachi", "Electrician", "image3.jpg", "video3.mp4"));
//            itemList.add(new Item("4. Carpentry Work", "5000", "Skilled carpenters at your service", "Islamabad", "Carpenter", "image4.jpg", "video4.mp4"));
//            itemList.add(new Item("5. Car Mechanic", "10000", "Mechanical repairs for your vehicle", "Karachi", "Mechanic", "image5.jpg", "video5.mp4"));

            adapter = new ItemAdapter(AllTrendingServices.this, itemList, this);

//        RecyclerView.LayoutManager lm = new LinearLayoutManager((MainActivity7.this));

            GridLayoutManager lm = new GridLayoutManager(this, 2);
            rv.setLayoutManager(lm);
            rv.setAdapter(adapter);


    }

    @Override
    public void onItemClicked(int position) {

    }
}