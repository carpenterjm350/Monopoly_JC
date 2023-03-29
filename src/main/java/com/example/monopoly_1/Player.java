package com.example.monopoly_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private int id;
    private String name;
    private int posOnBoard;
    private int balance;

    private ArrayList<Property> ownedProperties = new ArrayList<>();

    private ObservableList<String> ownedPropertyNames = FXCollections.observableArrayList();


    //region Constructors

    public Player(int id, String name, int posOnBoard, int balance) {
        this.id = id;
        this.name = name;
        this.posOnBoard = posOnBoard;
        this.balance = balance;
    }
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.posOnBoard = -1;
        balance = 1500;
    }

    public Player() {
        this.id = 0;
        this.name = null;
        this.posOnBoard = -1;
        balance = 0;
    }


    //endregion


    //region Getters and Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosOnBoard() {
        return posOnBoard;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosOnBoard(int posOnBoard) {

        this.posOnBoard = (this.posOnBoard + posOnBoard) % 22;

    }

    public ArrayList<Property> getOwnedProperties() {
        return ownedProperties;
    }

    public void setOwnedProperties(ArrayList<Property> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public ObservableList<String> getOwnedPropertyNames() {
        return ownedPropertyNames;
    }

    public void addOwnedPropertyName(String name) {
        this.ownedPropertyNames.add(name);
    }

    public void setOwnedPropertyNames()
    {
        for(int i = 0; i < ownedProperties.size(); i++)
        {
            ownedPropertyNames.add(ownedProperties.get(i).getName());
        }
    }

    public void addMoney(int balance) { this.balance += balance; }

    public void subMoney(int balance) { this.balance -= balance; }

    public void addProperty()
    {
        ownedProperties.add(DbUtils.properties.get(posOnBoard));
    }

    public void addProperty(Property property)
    {
        ownedProperties.add(property);
    }



    //endregion

    public int diceRoll()
    {
        int range = 10;


        //Create Random Number
        Random random = new Random();
        int ranNum = random.nextInt(range);


        //Add 2 to Make dice roll between 2 and 12
        int diceRoll = ranNum + 2;


        setPosOnBoard(diceRoll);


        return (diceRoll);
    }
}
