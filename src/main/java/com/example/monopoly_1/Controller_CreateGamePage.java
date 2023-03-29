package com.example.monopoly_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Controller_CreateGamePage {

    public TextField gameKey_TextField;

    @FXML
    private TextField player1_TextField;

    @FXML
    private TextField player2_TextField;

    @FXML
    private TextField player3_TextField;

    @FXML
    private TextField player4_TextField;

    public static int gameKey;


    //region Game


    @FXML
    private void newGame(ActionEvent event) throws IOException, SQLException {

        //New GameKey Setup
        int gameID = gameKey(1000, 100000000);
        gameKey = gameID;


        DbUtils.getProperties();


        Player[] players = new Player[4];
        setUpNewPlayers(players);


        //Fill Empty Player Names
        fillPlayersList(players);


        //Delete Old Saved Players and Old Owned Properties
        DbUtils.deletePlayers();
        DbUtils.deletePlayerProperties();


        //Transfer Players list to GamePage
        Controller_GamePage.createCurrentPlayers(players);



        DbUtils.changeScene(event, DbUtils.gamePageFile, "Monopoly", null, null, false);
    }

    @FXML
    private void oldGame(ActionEvent event) throws SQLException {

        //Set GameKey
        gameKey = Integer.parseInt(gameKey_TextField.getText());



        DbUtils.getGame(event, Controller_GamePage.currentPlayers, Controller_CreateGamePage.gameKey);
    }


    //endregion


    //region Players


    private void setUpNewPlayers(Player[] players)
    {

        players[0] = new Player(1, player1_TextField.getText());
        players[1] = new Player(2, player2_TextField.getText());
        players[2] = new Player(3, player3_TextField.getText());
        players[3] = new Player(4, player4_TextField.getText());

    }

    private void fillPlayersList(Player [] players)
    {

        for(int i = 0; i < players.length; i++)
        {
            if(players[i].getName() == null || players[i].getName().equals(""))
            {
                players[i].setName("Player " + (i + 1));
            }
        }
    }


    //endregion


    private int gameKey(int range, int min)
    {
        return (int)(Math.random() * range) + min;
    }

    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        DbUtils.changeScene(event, DbUtils.startPageFile, "Home",null, null, true);
    }

}
