package com.faizanahmed.janabhazir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class otp_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);

        Button btnReceiveOTP = findViewById(R.id.BtnReceiveOTP);
        btnReceiveOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(otp_2.this, verifyOtp_3.class);
                startActivity(intent);
            }
        });
    }
}