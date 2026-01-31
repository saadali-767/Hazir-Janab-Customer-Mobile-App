package com.faizanahmed.janabhazir;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.CardParams;
import com.stripe.android.model.Token;
import com.stripe.android.Stripe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class payment extends AppCompatActivity {
    private Stripe stripe;
    private ImageView cardIcon;
    EditText etCardNumber;
    EditText etExpDate;
    EditText etCvc;
    double totalAmount=  0;
    int bookingId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cardIcon = findViewById(R.id.iv_card_icon);
        // Initialize your EditTexts right after setContentView
        etCardNumber = findViewById(R.id.et_card_number);
        etExpDate = findViewById(R.id.et_exp_date);
        etCvc = findViewById(R.id.et_cvc);
        //cardIcon = findViewById(R.id.iv_card_icon);
        Intent intent = getIntent(); // This gets the Intent that started your activity.
        totalAmount= intent.getDoubleExtra("totalAmount",0);
        bookingId=intent.getIntExtra("bookingId",0);
        Log.d("totalAmount", "payment booking_id"+bookingId);

        Log.d("totalAmount", "onCreate: "+totalAmount);

        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCardIcon(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Initialize Stripe
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51MC7IZKE7yyXcUzGxI8mDV3TGw4z78JT4dUnCX6K8YMYWh0ndge0CH7j0LrWoqzpOEn7KvtzP67UMegjF9DwXKp300o7SXJz8o" // Replace with your actual publishable key
        );

        stripe = new Stripe(getApplicationContext(), PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey());
    }

    public void onSubmitPaymentClick(View view) {
        // Collecting card details
        etCardNumber = findViewById(R.id.et_card_number);
        etExpDate = findViewById(R.id.et_exp_date);
        etCvc = findViewById(R.id.et_cvc);

        // Validate card details
        String cardNumber = etCardNumber.getText().toString();
        String expDate = etExpDate.getText().toString();
        String cvc = etCvc.getText().toString();

        if (cardNumber.isEmpty() || expDate.isEmpty() || cvc.isEmpty() || !expDate.contains("/")) {
            Toast.makeText(this, "Please enter valid card details", Toast.LENGTH_LONG).show();
            return;
        }

        // Split expiration date into month and year
        String[] expDateParts = expDate.split("/");
        int expMonth, expYear;
        try {
            expMonth = Integer.parseInt(expDateParts[0]);
            expYear = Integer.parseInt(expDateParts[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "Invalid expiration date format", Toast.LENGTH_LONG).show();
            return;
        }

        // Create CardParams
        CardParams cardParams = new CardParams(
                cardNumber,
                expMonth,
                expYear,
                cvc
        );

        // Create a Stripe token
        createToken(cardParams);
    }

    private void createToken(CardParams cardParams) {
        stripe.createCardToken(cardParams, new ApiResultCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
                // Send token to your server
                sendTokenToServer(token.getId());
            }

            @Override
            public void onError(Exception e) {
                // Handle error
                Log.d("TAG", "onError: " + e);
                String errorMessage = e.toString();
                if (errorMessage.contains("Your card number is incorrect")) {
                    errorMessage = "Incorrect card number";
                }
                String finalErrorMessage = errorMessage;
                runOnUiThread(() -> Toast.makeText(payment.this, finalErrorMessage, Toast.LENGTH_LONG).show());
                //runOnUiThread(() -> Toast.makeText(payment.this, "Payment Error: " + e.toString(), Toast.LENGTH_LONG).show());
            }
        });
    }

    private void sendTokenToServer(final String token) {
        String serverUrl1 = getResources().getString(R.string.server_url);
        String serverUrl = serverUrl1 + "hazirjanab/stripe.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                response -> {
                    // Response received from the server
                    Log.d("ServerResponse", response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");
                        if ("success".equals(status)) {
                            // Payment was successful
                            runOnUiThread(() -> Toast.makeText(payment.this, "Payment made successfully", Toast.LENGTH_LONG).show());
                            Intent intent = new Intent(this, MainActivity_1.class);
                            startActivity(intent);


                        } else {
                            // Payment failed
                            String errorMessage = jsonResponse.optString("message", "Unknown error");
                            runOnUiThread(() -> Toast.makeText(payment.this, "Payment failed: " + errorMessage, Toast.LENGTH_LONG).show());
                        }
                    } catch (JSONException e) {
                        Log.e("TAG", "Json parsing error: " + e.getMessage());
                        runOnUiThread(() -> Toast.makeText(payment.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                },
                error -> {
                    // Error handling
                    Log.e("TAG", "Error sending token to server: " + error.toString());
                    runOnUiThread(() -> Toast.makeText(payment.this, "Network Error: " + error.getMessage(), Toast.LENGTH_LONG).show());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Post parameters to be sent to the server
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("amount", String.valueOf((int) (totalAmount * 100))); // Convert totalAmount to cents
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void updateCardIcon(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            cardIcon.setImageResource(R.drawable.visa); // Replace with your actual Visa icon resource
        } else if (cardNumber.startsWith("5")) {
            cardIcon.setImageResource(R.drawable.master); // Replace with your actual MasterCard icon resource
        } else {
            cardIcon.setImageResource(R.drawable.creditcard); // A generic card icon or empty state
        }
    }

}