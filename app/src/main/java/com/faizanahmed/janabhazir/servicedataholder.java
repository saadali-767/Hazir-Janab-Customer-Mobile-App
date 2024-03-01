package com.faizanahmed.janabhazir;

import java.util.ArrayList;
import java.util.List;

public class servicedataholder {
    private static List<Item> itemList = new ArrayList<>();

    public static List<Item> getItemList() {
        return itemList;
    }

    public static void setItemList(List<Item> newList) {
        itemList = newList;
    }
    public static void clearItemList() {
        itemList.clear();
    }
    public static Item findItemByServiceId(int serviceId) {
        for (Item item : itemList) {
            if (item.getItemId() == serviceId) {
                return item;
            }
        }
        return null; // Return null if no matching item is found
    }
}
