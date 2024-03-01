package com.faizanahmed.janabhazir;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Support_11 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support11);

        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);
    }
}