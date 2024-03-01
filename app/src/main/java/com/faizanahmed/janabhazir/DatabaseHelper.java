package com.faizanahmed.janabhazir;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookingDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "EmergencyBooking";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_SERVICE_ID = "service_id";
    private static final String COLUMN_VENDOR_ID = "vendor_id";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKING_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_SERVICE_ID + " INTEGER,"
                + COLUMN_VENDOR_ID + " INTEGER,"
                + COLUMN_CITY + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE + " BLOB"
                + ")";
        db.execSQL(CREATE_BOOKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBooking(booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, booking.getUserId());
        values.put(COLUMN_SERVICE_ID, booking.getServiceId());
        values.put(COLUMN_VENDOR_ID, booking.getVendorId());
        values.put(COLUMN_CITY, booking.getCity());
        values.put(COLUMN_ADDRESS, booking.getAddress());
        values.put(COLUMN_DESCRIPTION, booking.getDescription());
        values.put(COLUMN_IMAGE, booking.getImage());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addBookings(List<booking> bookings) {
        for (booking booking : bookings) {
            addBooking(booking);
        }
    }

    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}

