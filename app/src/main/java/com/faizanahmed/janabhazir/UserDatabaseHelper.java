package com.faizanahmed.janabhazir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public void logAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME);
                int lastNameIndex = cursor.getColumnIndex(COLUMN_LAST_NAME);
                int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                int addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS);
                int phoneNumberIndex = cursor.getColumnIndex(COLUMN_PHONE_NUMBER);

                String logMessage = "User: ";
                if (idIndex != -1) logMessage += cursor.getInt(idIndex) + ", ";
                if (firstNameIndex != -1) logMessage += cursor.getString(firstNameIndex) + ", ";
                if (lastNameIndex != -1) logMessage += cursor.getString(lastNameIndex) + ", ";
                if (emailIndex != -1) logMessage += cursor.getString(emailIndex) + ", ";
                if (passwordIndex != -1) logMessage += cursor.getString(passwordIndex) + ", ";
                if (addressIndex != -1) logMessage += cursor.getString(addressIndex) + ", ";
                if (phoneNumberIndex != -1) logMessage += cursor.getString(phoneNumberIndex);

                Log.d("DB_LOG", logMessage);
            } while (cursor.moveToNext());
        } else {
            Log.d("DB_LOG", "No users found in the database.");
        }

        cursor.close();
        db.close();
    }
    public boolean isUserLoggedIn() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID}, null, null, null, null, null);

        boolean isLoggedIn = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isLoggedIn;
    }

    public boolean checkUserExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        Log.d("DBCheck", "User exists: " + exists);
        cursor.close();
        db.close();
        return exists;
    }



    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // User table and columns
    private static final String TABLE_USER = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PROFILE_PIC = "profile_pic";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";

    // SQL statement to create a user table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USER + "(" +
                    COLUMN_ID + " INTEGER ," +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PROFILE_PIC + " BLOB, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_PHONE_NUMBER + " TEXT" +
                    ")";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Method to insert a user into the database
    public void addUser(JSONObject userData) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, userData.getInt("id"));
        values.put(COLUMN_FIRST_NAME, userData.getString("First_name")); // Changed to "First_name"
        values.put(COLUMN_LAST_NAME, userData.getString("Last_name")); // Changed to "Last_name"
        values.put(COLUMN_EMAIL, userData.getString("Email")); // Changed to "Email"
        values.put(COLUMN_PASSWORD, userData.getString("Password")); // Changed to "Password"

        // Handle the profile picture
        String base64Image = userData.optString("Profile_pic", ""); // Changed to "Profile_pic"
        if (!base64Image.isEmpty()) {
            byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            values.put(COLUMN_PROFILE_PIC, getBitmapAsByteArray(bitmap));
        }

        values.put(COLUMN_ADDRESS, userData.getString("address"));
        values.put(COLUMN_PHONE_NUMBER, userData.getString("phone_number"));

        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public JSONObject getLoggedInUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USER,
                new String[]{COLUMN_ID,COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_PROFILE_PIC, COLUMN_ADDRESS, COLUMN_PHONE_NUMBER},
                null, // Selection
                null, // Selection Args
                null, // Group By
                null, // Having
                null // Order By
        );

        JSONObject userData = new JSONObject();

        if (cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                if (idIndex != -1) {
                    userData.put("id", cursor.getInt(idIndex)); // Adding the user ID to the JSON object
                }
                // Check if the column exists before retrieving data
                int firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME);
                if (firstNameIndex != -1) {
                    userData.put("First_name", cursor.getString(firstNameIndex));
                }

                int lastNameIndex = cursor.getColumnIndex(COLUMN_LAST_NAME);
                if (lastNameIndex != -1) {
                    userData.put("Last_name", cursor.getString(lastNameIndex));
                }

                int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                if (emailIndex != -1) {
                    userData.put("Email", cursor.getString(emailIndex));
                }

                int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                if (passwordIndex != -1) {
                    userData.put("Password", cursor.getString(passwordIndex));
                }

                // Handle the profile picture
                int profilePicIndex = cursor.getColumnIndex(COLUMN_PROFILE_PIC);
                if (profilePicIndex != -1) {
                    byte[] profilePicBytes = cursor.getBlob(profilePicIndex);
                    if (profilePicBytes != null) {
                        Bitmap profilePicBitmap = BitmapFactory.decodeByteArray(profilePicBytes, 0, profilePicBytes.length);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        profilePicBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        String base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                        userData.put("Profile_pic", base64Image);
                    }
                }

                int addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS);
                if (addressIndex != -1) {
                    userData.put("address", cursor.getString(addressIndex));
                }

                int phoneNumberIndex = cursor.getColumnIndex(COLUMN_PHONE_NUMBER);
                if (phoneNumberIndex != -1) {
                    userData.put("phone_number", cursor.getString(phoneNumberIndex));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        cursor.close();
        db.close();

        return userData;
    }

    public void logoutUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }


}
