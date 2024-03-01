package com.faizanahmed.janabhazir;

import java.util.ArrayList;
import java.util.List;

public class productordersdataholder {
    private static List<productorders> orderList = new ArrayList<>();

    // Method to get the list of product orders
    public static List<productorders> getOrderList() {
        return orderList;
    }

    // Method to add a product order to the list
    public static void addProductOrder(productorders order) {
        orderList.add(order);
    }

    // Method to remove a product order from the list by product ID
    public static boolean removeProductOrder(int productID) {
        return orderList.removeIf(order -> order.getProductID() == productID);
    }

    // Method to set a new list of product orders
    public static void setOrderList(List<productorders> newList) {
        orderList = newList;
    }

    // Method to clear the list of product orders
    public static void clearOrderList() {
        orderList.clear();
    }

    // Method to find product orders by product ID
    public static List<productorders> findOrdersByProductId(int productId) {
        List<productorders> matchedOrders = new ArrayList<>();
        for (productorders order : orderList) {
            if (order.getProductID() == productId) {
                matchedOrders.add(order);
            }
        }
        return matchedOrders; // Return list of matched orders
    }
}
