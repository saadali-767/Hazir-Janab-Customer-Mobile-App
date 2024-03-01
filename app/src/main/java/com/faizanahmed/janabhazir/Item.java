package com.faizanahmed.janabhazir;

public class Item {
    private Integer itemId;
    private String itemName;
    private String itemHourlyRate;
    private String itemDescription;
    private String itemCity;
    private String itemCategory;
    private String itemType;
    private String itemImageUrl;
    //private String itemVideoUrl;
   // private String imageBase64;

    public Item(Integer itemId, String itemName, String itemHourlyRate, String itemDescription, String itemCity, String itemCategory, String itemType,  String itemImageUrl) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemHourlyRate = itemHourlyRate;
        this.itemDescription = itemDescription;
        this.itemCity = itemCity;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        //this.itemImageUrl = itemImageUrl;
       // this.itemVideoUrl = itemVideoUrl;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemHourlyRate() {
        return itemHourlyRate;
    }

    public void setItemHourlyRate(String itemHourlyRate) {
        this.itemHourlyRate = itemHourlyRate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemCity() {
        return itemCity;
    }

    public void setItemCity(String itemCity) {
        this.itemCity = itemCity;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

//    public String getItemVideoUrl() {
//        return itemVideoUrl;
//    }
//
//    public void setItemVideoUrl(String itemVideoUrl) {
//        this.itemVideoUrl = itemVideoUrl;
//    }
}



