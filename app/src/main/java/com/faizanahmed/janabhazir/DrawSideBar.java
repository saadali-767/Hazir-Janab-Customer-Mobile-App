package com.faizanahmed.janabhazir;

import android.app.Activity;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawSideBar {

    private DrawerLayout SideBarDrawer;
    private ImageView SideBarMenu;
    private NavigationView NavigationDrawer1;
    private NavigationView NavigationDrawer2;

    public void setup(Activity activity) {
        SideBarDrawer = activity.findViewById(R.id.SideBarDrawer);
        SideBarMenu = activity.findViewById(R.id.ivSideBar);

        if (SideBarMenu != null) {
            SideBarMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("DrawSideBar", "SideBarMenu clicked");
                    if (SideBarDrawer != null) {
                        if (SideBarDrawer.isDrawerOpen(GravityCompat.START))
                            SideBarDrawer.closeDrawer(GravityCompat.START);
                        else
                            SideBarDrawer.openDrawer(GravityCompat.START);
                    }
                }
            });
        }

        NavigationDrawer1 = activity.findViewById(R.id.NavigationDrawerMain);
        if (NavigationDrawer1 != null) {
            NavigationDrawer1.setItemIconTintList(null);

            MenuItem headerItem = NavigationDrawer1.getMenu().findItem(R.id.main_item);
            if (headerItem != null) {
                SpannableString spannableString = new SpannableString(headerItem.getTitle());
                spannableString.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                headerItem.setTitle(spannableString);
            }

            NavigationDrawer1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.iHome) {
                        Intent intent = new Intent(activity, ServiceType_2.class);
                        activity.startActivity(intent);
                        return true;
                    }
                    if (item.getItemId() == R.id.iProfile) {
                        Intent intent = new Intent(activity, userProfile_6.class);
                        activity.startActivity(intent);
                        return true;
                    }
                    else if (item.getItemId() == R.id.iCart) {
                        //Toast rewards clicked
                        Intent intent = new Intent(activity, YourCart.class);
                        activity.startActivity(intent);
//                        Toast.makeText(activity, "Cart Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if (item.getItemId() == R.id.iRewards) {
                        Intent intent = new Intent(activity, userorders.class);
                        activity.startActivity(intent);
                        //Toast rewards clicked
                        //Toast.makeText(activity, "Rewards Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if (item.getItemId() == R.id.ihistory) {
                        Intent intent = new Intent(activity, orderhistory.class);
                        activity.startActivity(intent);
                        //Toast rewards clicked
                        //Toast.makeText(activity, "Rewards Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else if (item.getItemId() == R.id.isupport) {
                        Intent intent = new Intent(activity, Support_11.class);
                        activity.startActivity(intent);
                        //Toast rewards clicked
                        //Toast.makeText(activity, "Rewards Clicked", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });
        }

        NavigationDrawer2 = activity.findViewById(R.id.NavigationDrawerSettings);
        if (NavigationDrawer2 != null) {
            NavigationDrawer2.setItemIconTintList(null);

            MenuItem headerSettingsItem = NavigationDrawer2.getMenu().findItem(R.id.main_settings_item);
            if (headerSettingsItem != null) {
                SpannableString spannableString1 = new SpannableString(headerSettingsItem.getTitle());
                spannableString1.setSpan(new AbsoluteSizeSpan(24, true), 0, spannableString1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                headerSettingsItem.setTitle(spannableString1);
            }

            NavigationDrawer2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.iLogout) {
                        UserDataSingleton.resetInstance();
                        UserDatabaseHelper dbHelper = new UserDatabaseHelper(activity);
                        dbHelper.logoutUser();
                        Intent intent = new Intent(activity, MainActivity_1.class);
                        activity.startActivity(intent);
                        Log.d( "DrawSideBar", "Logout Clicked! going to login page");
                        activity.finish();
                        return true;
                    }
                    return false;
                }
            });
        }

        ImageView btnBackArrow = activity.findViewById(R.id.back_arrow);
        if (btnBackArrow != null) {
            btnBackArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SideBarDrawer != null) {
                        SideBarDrawer.closeDrawer(GravityCompat.START);
                    }
                }
            });
        }
    }
}
