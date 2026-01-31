package com.faizanahmed.janabhazir;

public class bookingdataholder {
    private static NormalBooking normalBookingInstance;

    // Private constructor so no one can instantiate this class
    private bookingdataholder() {}

    // Method to get the instance of NormalBooking
    public static NormalBooking getNormalBookingInstance() {
        if (normalBookingInstance == null) {
            // If no instance is initialized yet, you could initialize a new one here
            // or this method can be left as is, depending on if you want lazy initialization or not
        }
        return normalBookingInstance;
    }

    // Method to set the instance of NormalBooking
    public static void setNormalBookingInstance(NormalBooking newBooking) {
        normalBookingInstance = newBooking;
    }

    // Optional: Method to clear the NormalBooking instance
    public static void clearNormalBookingInstance() {
        normalBookingInstance = null;
    }
    public static boolean isBookingInstanceValid() {
        // Check if the instance is not null
        if (normalBookingInstance != null) {
            // For further validation, check a specific attribute. Example: serviceId
            return true; // Assuming serviceId > 0 is valid
        }
        return false; // No instance present
    }
}
