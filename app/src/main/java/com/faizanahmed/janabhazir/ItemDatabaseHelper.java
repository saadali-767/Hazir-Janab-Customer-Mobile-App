package com.faizanahmed.janabhazir;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookingDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "itemId";
    private static final String COLUMN_ITEM_NAME = "itemName";
    private static final String COLUMN_ITEM_HOURLY_RATE = "itemHourlyRate";
    private static final String COLUMN_ITEM_DESCRIPTION = "itemDescription";
    private static final String COLUMN_ITEM_CITY = "itemCity";
    private static final String COLUMN_ITEM_CATEGORY = "itemCategory";
    private static final String COLUMN_ITEM_TYPE = "itemType";
    private static final String COLUMN_ITEM_IMAGE = "itemImage"; // Column for image byte array

    public ItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_HOURLY_RATE + " TEXT,"
                + COLUMN_ITEM_DESCRIPTION + " TEXT,"
                + COLUMN_ITEM_CITY + " TEXT,"
                + COLUMN_ITEM_CATEGORY + " TEXT,"
                + COLUMN_ITEM_TYPE + " TEXT,"
                + COLUMN_ITEM_IMAGE + " BLOB" // Use BLOB type for binary data
                + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ITEM_ID, item.getItemId());
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_HOURLY_RATE, item.getItemHourlyRate());
        values.put(COLUMN_ITEM_DESCRIPTION, item.getItemDescription());
        values.put(COLUMN_ITEM_CITY, item.getItemCity());
        values.put(COLUMN_ITEM_CATEGORY, item.getItemCategory());
        values.put(COLUMN_ITEM_TYPE, item.getItemType());
        //values.put(COLUMN_ITEM_IMAGE, item.getItemImage()); // Insert the image byte array

        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    // Additional methods to update, retrieve, and delete items can also be added here.
}

