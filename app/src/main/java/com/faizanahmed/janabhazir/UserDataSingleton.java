package com.faizanahmed.janabhazir;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDataSingleton {
    private static UserDataSingleton instance;
    private JSONObject userData;

    private UserDataSingleton() {}

    public static void resetInstance() {
        instance = null;
        Log.d("UserDataSingleton", "Instance reset");
    }

    public void updateUserData(String key, String value) {
        if (userData != null) {
            try {
                userData.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized UserDataSingleton getInstance() {
        if (instance == null) {
            instance = new UserDataSingleton();
        }
        return instance;
    }

    public void setUserData(JSONObject data) {
        this.userData = data;
    }

    public JSONObject getUserData() {
        return this.userData;
    }
}
