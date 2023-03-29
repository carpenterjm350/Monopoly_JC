package com.example.monopoly_1;

public class Property {

    String name, colorGroup, hexColor;
    int price, pricePerHouse, rent, houseRent, houseRent2, houseRent3, houseRent4, hotelRent, mortgage;


    public Property(String name, int price, int pricePerHouse, int rent,
                    int houseRent, int houseRent2, int houseRent3,
                    int houseRent4, int hotelRent, int mortgage, String colorGroup,
                    String hexColor) {


        this.name = name;
        this.colorGroup = colorGroup;
        this.hexColor = hexColor;
        this.price = price;
        this.pricePerHouse = pricePerHouse;
        this.rent = rent;
        this.houseRent = houseRent;
        this.houseRent2 = houseRent2;
        this.houseRent3 = houseRent3;
        this.houseRent4 = houseRent4;
        this.hotelRent = hotelRent;
        this.mortgage = mortgage;

    }



    //region Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorGroup() {
        return colorGroup;
    }

    public void setColorGroup(String colorGroup) {
        this.colorGroup = colorGroup;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPricePerHouse() {
        return pricePerHouse;
    }

    public void setPricePerHouse(int pricePerHouse) {
        this.pricePerHouse = pricePerHouse;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(int houseRent) {
        this.houseRent = houseRent;
    }

    public int getHouseRent2() {
        return houseRent2;
    }

    public void setHouseRent2(int houseRent2) {
        this.houseRent2 = houseRent2;
    }

    public int getHouseRent3() {
        return houseRent3;
    }

    public void setHouseRent3(int houseRent3) {
        this.houseRent3 = houseRent3;
    }

    public int getHouseRent4() {
        return houseRent4;
    }

    public void setHouseRent4(int houseRent4) {
        this.houseRent4 = houseRent4;
    }

    public int getHotelRent() {
        return hotelRent;
    }

    public void setHotelRent(int hotelRent) {
        this.hotelRent = hotelRent;
    }

    public int getMortgage() {
        return mortgage;
    }

    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }


    //endregion

    @Override
    public String toString() {
        return "Property{" + "\n" +
                "price=" + price + "\n" +
                "pricePerHouse=" + pricePerHouse + "\n" +
                "rent=" + rent + "\n" +
                "houseRent=" + houseRent + "\n" +
                "houseRent2=" + houseRent2 + "\n" +
                "houseRent3=" + houseRent3 + "\n" +
                "houseRent4=" + houseRent4 + "\n" +
                "hotelRent=" + hotelRent + "\n" +
                "mortgage=" + mortgage +
                '}';
    }
}
