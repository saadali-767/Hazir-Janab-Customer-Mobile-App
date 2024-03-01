package com.faizanahmed.janabhazir;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firestore.v1.Cursor;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class userProfile_6 extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhoneNumber, tvUsername, tvPassword, tvAddress, tvDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile6);

        DrawSideBar drawSideBar = new DrawSideBar();
        drawSideBar.setup(this);

        ImageView ivSupport = findViewById(R.id.ivSupport);
        ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(userProfile_6.this, "Support", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(userProfile_6.this, Support_11.class);
                startActivity(intent);
            }
        });

        ImageView ivEditUserInformation = findViewById(R.id.ivEditUserInformation);
        ImageView ivEditAccountCredentials = findViewById(R.id.ivEditAccountCredentials);
        ImageView ivEditAddress = findViewById(R.id.ivEditAddress);

        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvUsername = findViewById(R.id.tvUserName);
        tvPassword = findViewById(R.id.tvPassword);
        tvAddress = findViewById(R.id.tvAddress);

        try {
            JSONObject userData = UserDataSingleton.getInstance().getUserData();
            String firstName = userData.optString("First_name", ""); // Changed to "First_name"
            String lastName = userData.optString("Last_name", ""); // Changed to "Last_name"
            String email = userData.optString("Email", ""); // Changed to "Email"
            String phoneNumber = userData.optString("phone_number", ""); // "phone_number" is correct
            String address = userData.optString("address", ""); // "phone_number" is correct
            String password = userData.optString("Password", ""); // "phone_number" is correct

            tvDisplayName.setText(firstName); // Set first name to display name
            tvName.setText(firstName + " " + lastName); // Set full name
            tvEmail.setText(email); // Set email
            tvPhoneNumber.setText(phoneNumber); // Set phone number
            tvAddress.setText(address); // Set phone number
            tvPassword.setText(password); // Set phone number

            tvUsername.setText(email); // Using email as username as per your requirement
            // tvPassword should not display the actual password for security reasons
            // tvAddress will be set based on user action in popup
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading user data", Toast.LENGTH_SHORT).show();
        }




        ivEditUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditPopup("userInformation");
            }
        });

        ivEditAccountCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditPopup("accountCredentials");
            }
        });

        ivEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditPopup("address");
            }
        });
    }

    private void showEditPopup(String type) {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.template_user_profile_popup, null);

        TextView tvPopupTitle = popupView.findViewById(R.id.tvPopupTitle);
        TextInputEditText etInputField1 = popupView.findViewById(R.id.etInputField1);
        TextInputEditText etInputField2 = popupView.findViewById(R.id.etInputField2);
        TextInputEditText etInputField3 = popupView.findViewById(R.id.etInputField3);
        Button btnSaveBtn = popupView.findViewById(R.id.btnSave);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();

        // Set fields based on the type
        if (type.equals("userInformation")) {
            tvPopupTitle.setText("Edit User Information");
            etInputField1.setHint("Name");
            etInputField2.setHint("Email");
            etInputField3.setHint("Phone");
            btnSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newName = etInputField1.getText().toString();
                    String newEmail = etInputField2.getText().toString();
                    String newPhone = etInputField3.getText().toString();

                    Toast.makeText(userProfile_6.this, "Changes Saved", Toast.LENGTH_SHORT).show();


//                    tvName.setText(etInputField1.getText().toString()+ "!");
//                    tvEmail.setText(etInputField2.getText().toString());
//                    tvPhoneNumber.setText(etInputField3.getText().toString());
//                    tvDisplayName.setText(etInputField1.getText().toString());


                    if (newName != null && !newName.isEmpty()) {
                        tvName.setText(etInputField1.getText().toString()+ "!");

                        UserDataSingleton.getInstance().updateUserData("First_name", newName.split(" ")[0]);
                        UserDataSingleton.getInstance().updateUserData("Last_name", newName.split(" ").length > 1 ? newName.split(" ")[1] : "");
                    }

                    if (newEmail != null && !newEmail.isEmpty()) {
                        UserDataSingleton.getInstance().updateUserData("Email", newEmail);
                        tvEmail.setText(etInputField2.getText().toString());

                    }

                    if (newPhone != null && !newPhone.isEmpty()) {
                        tvPhoneNumber.setText(etInputField3.getText().toString());
                        UserDataSingleton.getInstance().updateUserData("phone_number", newPhone);
                    }

                   // UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
                    //JSONObject userData = userDataSingleton.getUserData();
                    //Log.d("AnotherClass", "User data: " + userData.toString());


                    dialog.dismiss();
                }
            });
            // Load existing user info into fields
        } else if (type.equals("accountCredentials")) {
            tvPopupTitle.setText("Edit Account Credentials");
            etInputField1.setHint("Username");
            etInputField2.setHint("Password");
            etInputField3.setVisibility(View.GONE);

            btnSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newUsername = etInputField1.getText().toString();
                    String newPassword = etInputField2.getText().toString();
                   // tvUsername.setText(etInputField1.getText().toString());
                    //tvPassword.setText(etInputField2.getText().toString());

                    if (newUsername != null && !newUsername.isEmpty()) {
                        tvUsername.setText(etInputField1.getText().toString());

                        UserDataSingleton.getInstance().updateUserData("Email", newUsername); // Assuming 'Email' is used as the usernam

                    }
                    if (newPassword != null && !newPassword.isEmpty()) {
                        tvPassword.setText(etInputField2.getText().toString());

                        UserDataSingleton.getInstance().updateUserData("Password", newPassword);

                    }
                    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
                    JSONObject userData = userDataSingleton.getUserData();
                    Log.d("AnotherClass", "User data: " + userData.toString());


                    //UserDataSingleton.getInstance().updateUserData("Email", newUsername); // Assuming 'Email' is used as the username
                  //  UserDataSingleton.getInstance().updateUserData("Password", newPassword);

                    dialog.dismiss();
                }
            });
            // Load existing account credentials
        } else if (type.equals("address")) {
            tvPopupTitle.setText("Edit Address");
            etInputField2.setVisibility(View.GONE);
            etInputField3.setVisibility(View.GONE);

            btnSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newaddress = etInputField1.getText().toString();

                    etInputField1.setHint("Address");
                    if (newaddress != null && !newaddress.isEmpty()) {
                        tvAddress.setText(etInputField1.getText().toString());
                        UserDataSingleton.getInstance().updateUserData("address", newaddress);
                        UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
                        JSONObject userData = userDataSingleton.getUserData();
                        Log.d("AnotherClass", "User data: " + userData.toString());

                    }
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

}