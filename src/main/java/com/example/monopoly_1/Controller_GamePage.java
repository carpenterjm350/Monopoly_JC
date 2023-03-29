package com.example.monopoly_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_GamePage implements Initializable {

    @FXML
    public TextField playerName_TextField;
    @FXML
    public Button exitGame_Button;
    @FXML
    public Label gameKey_Label;
    @FXML
    public Text balance_Label, place_Label;
    @FXML
    public Label posOnBoard_Label;
    public ChoiceBox properties_ChoiceBox;
    public Button endTurn_Button;
    @FXML
    public Label player1_Label;
    @FXML
    public Label player2_Label;
    @FXML
    public Label player3_Label;
    @FXML
    public Label player4_Label;
    @FXML
    private Button buy_Button;

    @FXML
    private TextArea cardDetails_TextArea;

    @FXML
    private TextField rollNumber_TextField;

    @FXML
    private Button roll_Button;

    @FXML
    private Button sell_Button;

    public static Player[] currentPlayers = new Player[4];

    public static int currentPlayer = 0;

    private Player player = currentPlayers[currentPlayer];


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentPlayerSetup();
        allPlayerLabelsChange();

        playerPositionCard();


        gameKey_Label.setText(Integer.toString(Controller_CreateGamePage.gameKey));
    }



    //region DuringTurnButtons
    @FXML
    private void rollDice()
    {

        //dice roll
        rollNumber_TextField.setText(Integer.toString(player.diceRoll()));


        playerPositionCard();


        playerPaysRent();


        //Buttons
        roll_Button.setDisable(true);
        endTurn_Button.setDisable(false);
    }

    @FXML
    private void buy()
    {
        //Setting Local Variables
        Property property = DbUtils.properties.get(player.getPosOnBoard());


        //Buying Property
        if(propertyIsNotOwned(property))
        {
            player.subMoney(property.getPrice());
            player.addProperty();
            player.addOwnedPropertyName(property.getName());


            //Change Labels
            balance_Label.setText("Balance: " + player.getBalance());


            //Buttons
            buy_Button.setDisable(true);
            sell_Button.setDisable(true);
        }
        else
        {
            DbUtils.error("Someone Already Owns This Property");
        }
    }

    @FXML
    private void sell()
    {

        //Local Variables
        String choiceboxSelection = properties_ChoiceBox.getSelectionModel().getSelectedItem().toString();
        int propertyIndex = player.getOwnedPropertyNames().indexOf(choiceboxSelection);

        //Selling Property and Changing Label
        player.addMoney(player.getOwnedProperties().get(propertyIndex).getMortgage());
        balance_Label.setText("Balance: " + player.getBalance());

        //Removing Property from lists
        player.getOwnedProperties().remove(propertyIndex);
        player.getOwnedPropertyNames().remove(propertyIndex);

        //Buttons
        buy_Button.setDisable(true);
        sell_Button.setDisable(true);
    }

    @FXML
    private void endTurn() {

        playerCycle();



        //Declare Local Variable
        String placeText = place_Label.getText();



        //Buttons


        //Checks if the player is on Go. If they are, it keeps the buy and sell buttons disabled.
        if (!placeText.equals("Go!"))
        {
            buy_Button.setDisable(false);
            sell_Button.setDisable(false);
        }


        roll_Button.setDisable(false);
        endTurn_Button.setDisable(true);
    }


    //endregion


    //region Players


    public static void createCurrentPlayers(Player [] players)
    {
        currentPlayers = players;
    }

    private void playerCycle()
    {


        if(currentPlayer == 3)
        {
            currentPlayer = 0;
        }
        else
        {
            currentPlayer++;
        }

        player = currentPlayers[currentPlayer];



        currentPlayerSetup();
        allPlayerLabelsChange();
        playerPositionCard();


    }

    private void playerPaysRent()
    {
        //Declare Local Variables
        Property property = DbUtils.properties.get(player.getPosOnBoard());
        Player propertyOwner = null;

        //Get The Property Owner
        for(int i = 0; i < currentPlayers.length; i++)
        {
            if(currentPlayers[i].getOwnedProperties().contains(property))
            {
                propertyOwner = currentPlayers[i];
            }
        }

        //Transfer Money
        if(!propertyIsNotOwned(property))
        {
            player.subMoney(property.getRent());
            propertyOwner.addMoney(property.getRent());

            DbUtils.error("You just paid " + property.getRent() + " to " + propertyOwner.getName());

            allPlayerLabelsChange();

        }
    }

    private void currentPlayerSetup()
    {

        //Changing CurrentPlayer Labels
        playerName_TextField.setText(player.getName());
        balance_Label.setText("Balance: " + player.getBalance());
        posOnBoard_Label.setText("Position: " + player.getPosOnBoard());


        //Set ChoiceBox to CurrentPlayer Properties
        properties_ChoiceBox.setItems(player.getOwnedPropertyNames());

    }

    private void allPlayerLabelsChange()
    {

        player1_Label.setText(currentPlayers[0].getName() + " Balance: " + currentPlayers[0].getBalance());
        player2_Label.setText(currentPlayers[1].getName() + " Balance: " + currentPlayers[1].getBalance());
        player3_Label.setText(currentPlayers[2].getName() + " Balance: " + currentPlayers[2].getBalance());
        player4_Label.setText(currentPlayers[3].getName() + " Balance: " + currentPlayers[3].getBalance());

    }

    private void playerPositionCard()
    {
        if(player.getPosOnBoard() != -1)
        {
            posOnBoard_Label.setText("Position: " + player.getPosOnBoard());
            place_Label.setText(DbUtils.properties.get(player.getPosOnBoard()).getName());
            cardDetails_TextArea.setText(DbUtils.properties.get(player.getPosOnBoard()).toString());
        }
        else
        {
            posOnBoard_Label.setText("Position: " + player.getPosOnBoard());
            place_Label.setText("Go!");
            cardDetails_TextArea.setText("");
        }
    }



    //endregion



    private boolean propertyIsNotOwned(Property property)
    {
        for(int i = 0; i < currentPlayers.length; i++)
        {
            if(currentPlayers[i].getOwnedProperties().contains(property))
            {
                return false;
            }
        }

        return true;
    }

    @FXML
    private void exitGame(ActionEvent event) throws SQLException, IOException {

        System.out.println(currentPlayer);
        DbUtils.saveGame(Controller_CreateGamePage.gameKey, currentPlayer);


        //Deleting Players and Properties to make room for new Saves
        DbUtils.deletePlayers();
        DbUtils.deletePlayerProperties();


        //Saving Updated Players and Properties
        for(int i = 0; i < currentPlayers.length; i++)
        {
            DbUtils.addPlayer(currentPlayers[i]);
        }



        DbUtils.changeScene(event, DbUtils.startPageFile, "Home", null, null, true);

    }
}
