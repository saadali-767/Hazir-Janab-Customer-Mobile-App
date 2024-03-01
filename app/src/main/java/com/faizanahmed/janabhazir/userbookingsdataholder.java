package com.faizanahmed.janabhazir;

import java.util.ArrayList;
import java.util.List;

public class userbookingsdataholder {
    private static List<NormalBooking> bookinglist = new ArrayList<>();

    // Method to get the list of product orders
    public static List<NormalBooking> getOrderList() {
        return bookinglist;
    }

    // Method to add a product order to the list
    public static void addProductOrder(NormalBooking order) {
        bookinglist.add(order);
    }

    // Method to remove a product order from the list by product ID
    public static boolean removeProductOrder(int productID) {
        return bookinglist.removeIf(order -> order.getId() == productID);
    }

    // Method to set a new list of product orders
    public static void setOrderList(List<NormalBooking> newList) {
        bookinglist = newList;
    }

    // Method to clear the list of product orders
    public static void clearOrderList() {
        bookinglist.clear();
    }

    // Method to find product orders by product ID
    public static NormalBooking findOrdersByProductId(int productId) {
        NormalBooking matchedOrder = null; // Initialize matchedOrder to null
        for (NormalBooking order : bookinglist) {
            if (order.getId() == productId) {
                matchedOrder = order;
                break; // Exit the loop once a match is found
            }
        }
        return matchedOrder; // Return matched order, or null if no match found
    }

}
