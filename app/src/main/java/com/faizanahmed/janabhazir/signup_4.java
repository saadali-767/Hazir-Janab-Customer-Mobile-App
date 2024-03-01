package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class signup_4 extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword, etAddress;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup4);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);
        btnComplete = findViewById(R.id.BtnComplete);

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from EditTexts
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String address = etAddress.getText().toString();

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(signup_4.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform signup operation
                new SignupTask().execute(firstName, lastName, email, password, address);
            }
        });
    }

    private class SignupTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String serverUrl = getResources().getString(R.string.server_url);
            String signupApiUrl = serverUrl + "hazirjanab/signup.php";
            String signupUrl = signupApiUrl;
            String result = "";
            String firstName = params[0];
            String lastName = params[1];
            String email = params[2];
            String password = params[3];
            String address = params[4];

            try {
                URL url = new URL(signupUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();

                // Write parameters to the request
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                        URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8"));
                writer.flush();
                writer.close();
                os.close();

                // Read the response from the server
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                result = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Parse the JSON response
            try {
                JSONObject jsonResponse = new JSONObject(result);
                int status = jsonResponse.getInt("Status");
                String message = jsonResponse.getString("Message");

                Toast.makeText(signup_4.this, message, Toast.LENGTH_LONG).show();

                if(status == 1) {
                    // Signup successful
                    Intent intent = new Intent(signup_4.this, MainActivity_1.class);
                    startActivity(intent);
                    finish(); // Close this activity
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(signup_4.this, "Error parsing JSON response", Toast.LENGTH_LONG).show();
            }
        }
    }
}
