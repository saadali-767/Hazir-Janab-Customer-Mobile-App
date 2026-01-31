package com.faizanahmed.janabhazir;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class MainActivity_1 extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;

    TextView tvSignup;
    private void navigateToServiceType2() {
        Intent intent = new Intent(MainActivity_1.this, ServiceType_2.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_1);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.BtnLogin);
        tvSignup= findViewById(R.id.tvSignup);

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
        if (dbHelper.isUserLoggedIn()) {
            // Retrieve user data from SQLite
            Log.d("MainActivity", "dbHelper.isUserLoggedIn() says User is already logged in");
            JSONObject userData = dbHelper.getLoggedInUserData();// You need to implement this method in UserDatabaseHelper
            Log.d("login testing", String.valueOf(userData));
            if (userData != null) {
                // Set the singleton with retrieved data
                UserDataSingleton.getInstance().setUserData(userData);
            }

            navigateToServiceType2();
            return;
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                new LoginTask().execute(email, password);


            }
        });



        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_1.this, signup_4.class);
                startActivity(intent);
            }
        });
    }

//    public class FetchVendorStatusTask extends AsyncTask<Void, Void, String> {
//        private int userId;
//        private WeakReference<Context> contextRef; // Use WeakReference to prevent memory leaks
//
//        public FetchVendorStatusTask(Context context, int userId) {
//            this.contextRef = new WeakReference<>(context);
//            this.userId = userId;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String result = "";
//            try {
//                String serverUrl = contextRef.get().getResources().getString(R.string.server_url); // Ensure context is valid
//                String requestURL = serverUrl + "fetch_user_status.php";
//                URL url = new URL(requestURL);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoOutput(true);
//
//                OutputStream os = connection.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                String postData = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.userId), "UTF-8");
//                writer.write(postData);
//                writer.flush();
//                writer.close();
//                os.close();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//                StringBuilder stringBuilder = new StringBuilder();
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                result = stringBuilder.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Log.d("FetchVendorStatusTask", "Response received: " + result);
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Context context = contextRef.get();
//            if (context == null) return; // Context is no longer valid
//
//            try {
//                JSONObject jsonResponse = new JSONObject(result);
//                int status = jsonResponse.optInt("Status", 0);
//
//                if (status == 1) {
//                    JSONArray data = jsonResponse.getJSONArray("Data");
//                    for (int i = 0; i < data.length(); i++) {
//                        JSONObject booking = data.getJSONObject(i);
//                        String bookingStatus = booking.getString("status");
//                        int vendorId = booking.getInt("vendor_id");
//                        int bookingId = booking.getInt("id");
//                        Log.d("TAG", "onPostExecute: "+status+vendorId+bookingId);
//
//                        if ("Complete".equalsIgnoreCase(bookingStatus)) {
//                            // Assuming CheckForReviewTask is properly implemented
//                            new CheckForReviewTask(context, userId, vendorId, bookingId).execute();
//                            break; // Assuming we only need to check the first "Complete" status
//                        }
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private class CheckForReviewTask extends AsyncTask<Void, Void, String> {
//        private int userId;
//        private int vendorId;
//        private int bookingId;
//
//        public CheckForReviewTask(Context context, int userId, int vendorId, int bookingId) {
//            this.userId = userId;
//            this.vendorId = vendorId;
//            this.bookingId = bookingId;
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                String serverUrl = getResources().getString(R.string.server_url);
//                URL url = new URL(serverUrl + "check_review_exists.php");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoOutput(true);
//                OutputStream os = connection.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8") +
//                        "&" + URLEncoder.encode("vendor_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(vendorId), "UTF-8") +
//                        "&" + URLEncoder.encode("booking_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(bookingId), "UTF-8");
//                writer.write(data);
//                writer.flush();
//                writer.close();
//                os.close();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//                return stringBuilder.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            try {
//                JSONObject jsonResponse = new JSONObject(result);
//                int status = jsonResponse.getInt("Status");
//                if (status == 0) {
//                    // No review found, navigate to ReviewActivity
//                    Intent intent = new Intent(MainActivity_1.this, review.class);
//                    startActivity(intent);
//                } else {
//                    // Handle review exists case
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                // Handling JSON parsing error, potentially notify the user or log the issue
//                Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                // General error handling, e.g., no network connection or server error
//                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Log.d("LoginTask", "doInBackground: " + params[0] + " " + params[1]);
            String serverUrl = getResources().getString(R.string.server_url);
            String loginUrl = serverUrl + "hazirjanab/login.php";
            String result = "";
            String email = params[0];
            String password = params[1];
            Log.d("email:",email);
            Log.d("password:",password);

            try {
                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();

                // Write parameters to the request
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write("email=" + email + "&password=" + password);
                osw.flush();
                osw.close();

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
            Log.d("LoginTask", "doInBackground: " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("LoginResponse", "Response from server: " + result); // Log the response

            // Parse the JSON response
            try {
                JSONObject jsonResponse = new JSONObject(result);
                int status = jsonResponse.getInt("Status");
                String message = jsonResponse.getString("Message");

                Toast.makeText(MainActivity_1.this, message, Toast.LENGTH_LONG).show();

                if(status == 1) {
                    JSONObject userData = jsonResponse.getJSONObject("User");

                    UserDatabaseHelper dbHelper = new UserDatabaseHelper(MainActivity_1.this);
                    dbHelper.addUser(userData);
                    dbHelper.logAllUsers();

                    // Store user data in singleton class
                    UserDataSingleton.getInstance().setUserData(userData);
                    // Call this to display the data

                    JSONObject loggedInUserData = UserDataSingleton.getInstance().getUserData();
//                    try {
//                        int userId = loggedInUserData.getInt("ID"); // Ensure the key matches what you store
//                        new FetchVendorStatusTask(MainActivity_1.this, userId).execute();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                    Intent intent = new Intent(MainActivity_1.this, ServiceType_2.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity_1.this, "Error parsing JSON response", Toast.LENGTH_LONG).show();
            }
        }

    }
}
