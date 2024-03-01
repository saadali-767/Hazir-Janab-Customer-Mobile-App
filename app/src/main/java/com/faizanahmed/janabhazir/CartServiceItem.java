package com.faizanahmed.janabhazir;

public class CartServiceItem {
    private NormalBooking booking;
    private Item serviceItem;

    public CartServiceItem(NormalBooking booking, Item serviceItem) {
        this.booking = booking;
        this.serviceItem = serviceItem;
    }

    // Getter and Setter for booking
    public NormalBooking getBooking() {
        return booking;
    }

    public void setBooking(NormalBooking booking) {
        this.booking = booking;
    }

    // Getter and Setter for serviceItem
    public Item getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(Item serviceItem) {
        this.serviceItem = serviceItem;
    }
}

