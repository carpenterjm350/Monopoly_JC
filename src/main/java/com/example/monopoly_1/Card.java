package com.example.monopoly_1;

public class Card {

    private int propertyID;
    private String name;
    private double buyPrice;
    private double rentPrice;
    private String colorCategory;


    //region Constructors


    public Card() {
        propertyID = 0;
        name = null;
        buyPrice = 0;
        rentPrice = 0;
        colorCategory = null;
    }

    public Card(int propertyID, String name, double buyPrice, double rentPrice, String colorCategory) {
        this.propertyID = propertyID;
        this.name = name;
        this.buyPrice = buyPrice;
        this.rentPrice = rentPrice;
        this.colorCategory = colorCategory;
    }


    //endregion


    //region Getters and Setters


    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getColorCategory() {
        return colorCategory;
    }

    public void setColorCategory(String colorCategory) {
        this.colorCategory = colorCategory;
    }


    //endregion
}
