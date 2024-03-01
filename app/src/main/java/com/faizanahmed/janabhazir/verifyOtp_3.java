package com.faizanahmed.janabhazir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class verifyOtp_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp3);

        Button btnVerifyOTP = findViewById(R.id.BtnVerifyOTP);
        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(verifyOtp_3.this, signup_4.class);
                startActivity(intent);
            }
        });
    }
}