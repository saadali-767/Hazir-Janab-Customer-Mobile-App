package com.faizanahmed.janabhazir;

public class booking {
    private int id;
    private int userId;
    private int serviceId;
    private int vendorId;
    private String city;
    private String address;
    private String description;
    private byte[] image;

    public booking(int id, int userId, int serviceId, int vendorId, String city, String address, String description, byte[] image) {
        this.id = id;
        this.userId = userId;
        this.serviceId = serviceId;
        this.vendorId = vendorId;
        this.city = city;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
