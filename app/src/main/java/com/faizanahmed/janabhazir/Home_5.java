package com.faizanahmed.janabhazir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home_5 extends AppCompatActivity {

    DrawerLayout SideBarDrawer;
    ImageView SideBarMenu;
    NavigationView NavigationDrawer1, NavigationDrawer2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home5);

        //START OF SIDEBAR
        SideBarDrawer=findViewById(R.id.SideBarDrawer);
        SideBarMenu=findViewById(R.id.ivSideBar);

        SideBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SideBarDrawer.isDrawerOpen(Gravity.LEFT))
                    SideBarDrawer.closeDrawer(Gravity.LEFT);
                else
                    SideBarDrawer.openDrawer(Gravity.LEFT);
            }
        });

        NavigationDrawer1 = findViewById(R.id.NavigationDrawerMain);
        NavigationDrawer1.setItemIconTintList(null);
        NavigationDrawer2 = findViewById(R.id.NavigationDrawerSettings);
        NavigationDrawer2.setItemIconTintList(null);

        MenuItem headerItem = NavigationDrawer1.getMenu().findItem(R.id.main_item); // Find the main menu item by its ID
        // Apply a custom text size to the main menu item
        SpannableString spannableString = new SpannableString(headerItem.getTitle());
        spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        headerItem.setTitle(spannableString);

        MenuItem headerSettingsItem = NavigationDrawer2.getMenu().findItem(R.id.main_settings_item); // Find the main menu item by its ID
        // Apply a custom text size to the main menu item
        SpannableString spannableString1 = new SpannableString(headerSettingsItem.getTitle());
        spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        headerSettingsItem.setTitle(spannableString1);

        //handling if iProfile item is clicked then user_profile_6 should be opened
        if (NavigationDrawer1 != null) {
            NavigationDrawer1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Check if the clicked item is 'iLogout'
                    if (item.getItemId() == R.id.iProfile) {
                        // Code to start a new activity
                        Intent intent = new Intent(Home_5.this, userProfile_6.class); // Replace YourLogoutActivity with the actual activity class
                        startActivity(intent);
                        return true;
                    }else if (item.getItemId() == R.id.iRewards){
                        Intent intent = new Intent(Home_5.this, Booking_Emergency.class);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        }

        if (NavigationDrawer2 != null) {
            NavigationDrawer2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Check if the clicked item is 'iLogout'
                    if (item.getItemId() == R.id.iLogout) {
                        // Code to start a new activity
                        Intent intent = new Intent(Home_5.this, MainActivity_1.class); // Replace YourLogoutActivity with the actual activity class
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        }

        ImageView btnBackArrow = findViewById(R.id.back_arrow);
        btnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SideBarDrawer.closeDrawer(Gravity.LEFT);
            }
        });
        //END OF SIDEBAR

        //START OF TOP BAR
        ImageView btnSupport = findViewById(R.id.ivSupport);
        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_5.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home_5.this, Support_11.class);
                startActivity(intent);
            }
        });
        //END OF TOP BAR

        Button btnViewAllTrendingServices = findViewById(R.id.btnViewAllTrendingServices);
        btnViewAllTrendingServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_5.this, AllTrendingServices.class);
                startActivity(intent);
            }
        });

        Button btnViewAllServices = findViewById(R.id.btnViewAllServices);
        btnViewAllServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_5.this, SearchAllServices.class);
                startActivity(intent);
            }
        });

        Button btnViewAllServiceCategories = findViewById(R.id.btnViewAllServiceCategories);
        btnViewAllServiceCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_5.this, ServiceCategory_3.class);
                startActivity(intent);
            }
        });



    }
}