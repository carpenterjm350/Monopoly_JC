package com.example.monopoly_1;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DbUtils {


    public static String user_id;
    public static String staticUsername;

    public static ArrayList<String> ids = new ArrayList<>();

    public static ArrayList<Property> properties = new ArrayList<>();

    static FXMLLoader loader;

    public static String gamePageFile = "gamePage.fxml", loginPageFile = "loginPage.fxml", signUpPageFile = "signUpPage.fxml",
                    startPageFile = "startPage.fxml", createGamePageFile = "createGamePage.fxml";


    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String password, boolean ablePlay) throws IOException {
        Parent root = null;

        //Successful Login
        if(username != null & password != null)
        {
            loader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
            root = loader.load();
            if(ablePlay) {
                Controller_StartPage controller_startPage = loader.getController();
                controller_startPage.loggedInDisplay();
            }

        }
        else{
            loader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
            root = loader.load();
            if(ablePlay) {
                Controller_StartPage controller_startPage = loader.getController();
                controller_startPage.loggedInDisplay();
            }
        }


        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        if(title.equals("Monopoly"))
        {

            stage.setScene(new Scene(root, 728, 400));

        }else {

            stage.setScene(new Scene(root, 600, 400));

        }
        stage.show();
    }


    //region Account
    @FXML
    public static void signUpUser(ActionEvent event, String username, String password) throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");

            //(SELECT <all records> FROM <table> WHERE <column matches parameter>)
            psCheckUserExists = con.prepareStatement("SELECT * FROM account_info WHERE username = ?;");
            psCheckUserExists.setString(1, username);

            //Store query results
            resultSet = psCheckUserExists.executeQuery();


            if(resultSet.isBeforeFirst()){
                error("This user already exists!");
            }
            //User does not yet exist
            else
            {

                psInsertUser = con.prepareStatement("INSERT INTO account_info (username, pwd) VALUES (?, ?)");
                psInsertUser.setString(1, username);
                psInsertUser.setString(2, password);
                psInsertUser.executeUpdate();

                changeScene(event, "StartPage.fxml", "Home", username, password, false);
            }
        }
        catch (SQLException | IOException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }

    }

    @FXML
    public static void loginUser(ActionEvent event, String username, String password) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");
            preparedStatement = con.prepareStatement("SELECT * FROM account_info WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();


            if(!resultSet.isBeforeFirst()){
                error("Username is Incorrect");
            }
            else
            {
                while(resultSet.next())
                {
                    String retrievedPassword = resultSet.getString("pwd");

                    if (retrievedPassword.equals(password))
                    {
                        user_id = resultSet.getString("user_id");
                        staticUsername = resultSet.getString("username");
                        // Successful login
                        changeScene(event, startPageFile, "Home", username, null, true);


                    }else{
                        //Unsuccessful login
                        error("Password Is Incorrect");
                    }
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{

            if(con != null) {
                con.close();
            }
        }
    }

    //endregion


    //region Game
    @FXML
    public static void getGame(ActionEvent event, Player[] players, int gameKey) throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertUser = null;
        ResultSet resultSet = null;
        ResultSet rsPlayerIDs;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");

            //(SELECT <all records> FROM <table> WHERE <column matches parameter>)
            psCheckUserExists = con.prepareStatement("SELECT * FROM game WHERE game_id = ?;");
            psCheckUserExists.setString(1, Integer.toString(gameKey));

            //Store query results
            resultSet = psCheckUserExists.executeQuery();


            if(resultSet.isBeforeFirst()){

                psInsertUser = con.prepareStatement("SELECT * FROM game WHERE game_id = ?");
                psInsertUser.setString(1, Integer.toString(gameKey));
                rsPlayerIDs = psInsertUser.executeQuery();

                while(rsPlayerIDs.next())
                {
                    ids.add(rsPlayerIDs.getString("player_id"));
                    if(rsPlayerIDs.getBoolean("turn"))
                    {
                        System.out.println("We're in");
                        Controller_GamePage.currentPlayer = (rsPlayerIDs.getInt("player_id") - 1);
                    }
                    else{
                        System.out.println("Nope");
                    }
                }

                getProperties();
                getPlayers(players);

                DbUtils.changeScene(event, DbUtils.gamePageFile, "Monopoly", null, null, false);

            }
            //User does not yet exist
            else
            {
                error("No Game With This Key");
            }
        }
        catch (SQLException | IOException e)
        {

            throw new RuntimeException(e);

        } finally {

            if(con != null) {
                con.close();
            }
        }

    }


    @FXML
    public static void saveGame(int gameKey, int turn) throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");

            //(SELECT <all records> FROM <table> WHERE <column matches parameter>)
            psCheckUserExists = con.prepareStatement("SELECT * FROM game WHERE game_id = ?;");
            psCheckUserExists.setString(1, Integer.toString(gameKey));

            //Store query results
            resultSet = psCheckUserExists.executeQuery();

            System.out.println("Does it call save game");

            if(!resultSet.isBeforeFirst()){

                System.out.println("It went into the if");

                deleteGame();

                psInsertUser = con.prepareStatement("INSERT INTO game (game_id, player_id, turn) VALUES (?, ?, ?)");
                psInsertUser.setString(1, Integer.toString(gameKey));
                for(int i = 1; i < 5; i++) {
                    if((i - 1) == turn)
                    {
                        psInsertUser.setString(2, String.valueOf(i));
                        System.out.println("The current turn should be: " + i + "but current turn says " + turn);
                        psInsertUser.setBoolean(3, true);
                        psInsertUser.executeUpdate();
                    }
                    else
                    {
                        System.out.println("How Many Times");
                        psInsertUser.setString(2, String.valueOf(i));
                        psInsertUser.setBoolean(3, false);
                        psInsertUser.executeUpdate();
                    }


                }
            }
        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }

    }

    @FXML
    public static void deleteGame() throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psDeleteUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");


            psDeleteUser = con.prepareStatement("DELETE FROM game WHERE player_id = ?");

            for(int i = 1; i < 5; i++) {
                psDeleteUser.setString(1, String.valueOf(i));
                System.out.println("delete");
                psDeleteUser.executeUpdate();
            }

        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }

    }

    //endregion


    //region Player
    @FXML
    public static void addPlayer(Player player) throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");


                psInsertUser = con.prepareStatement("INSERT INTO player (user_id, player_balance, player_position, user_name) VALUES (?, ?, ?, ?)");
                psInsertUser.setString(1, String.valueOf(player.getId()));
                psInsertUser.setString(2, String.valueOf(player.getBalance()));
                psInsertUser.setString(3, String.valueOf(player.getPosOnBoard()));
                psInsertUser.setString(4, player.getName());
                psInsertUser.executeUpdate();

                savePlayerProperties(player);

        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }

    }

    @FXML
    public static void deletePlayers() throws SQLException {

        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psDeleteUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");


            psDeleteUser = con.prepareStatement("DELETE FROM player WHERE user_id = ?");

            for(int i = 1; i < 5; i++) {
                psDeleteUser.setString(1, String.valueOf(i));
                System.out.println("delete");
                psDeleteUser.executeUpdate();
            }

        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }

    }


    @FXML
    public static void getPlayers(Player [] players) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");
            preparedStatement = con.prepareStatement("SELECT * FROM player");
            resultSet = preparedStatement.executeQuery();
            int count = 0;


            if(!resultSet.isBeforeFirst()){
                error("No Players");
            }
            else
            {
                while(resultSet.next())
                {
                    players[count] = new Player(Integer.parseInt(resultSet.getString(1)), resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(3)), Integer.parseInt(resultSet.getString(2)));

                    count++;
                    }
                }
            System.out.println("The code gets through the while loop and does not crash");

            getPlayerProperties(players);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } finally{
            if(con != null) {
                con.close();
            }
        }

    }

    //endregion


    //region Properties
    @FXML
    public static void getProperties() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");
            preparedStatement = con.prepareStatement("SELECT * FROM properties");
            resultSet = preparedStatement.executeQuery();

                while(resultSet.next())
                {

                    properties.add(new Property(resultSet.getString(2), Integer.parseInt(resultSet.getString(3)),
                            Integer.parseInt(resultSet.getString(4)), Integer.parseInt(resultSet.getString(5)),
                            Integer.parseInt(resultSet.getString(6)), Integer.parseInt(resultSet.getString(7)),
                            Integer.parseInt(resultSet.getString(8)), Integer.parseInt(resultSet.getString(9)),
                            Integer.parseInt(resultSet.getString(10)), Integer.parseInt(resultSet.getString(11)),
                            resultSet.getString(11), resultSet.getString(12)));
                }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } finally{

            if(con != null) {
                con.close();
            }

        }

    }


    @FXML
    public static void getPlayerProperties(Player [] players) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{

            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");
            preparedStatement = con.prepareStatement("SELECT * FROM player_properties");
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {

                players [Integer.parseInt(resultSet.getString("user_id"))].addProperty(properties.get(Integer.parseInt(resultSet.getString("property_id"))));

            }

            for(int i = 0; i < players.length; i++)
            {
                players[i].setOwnedPropertyNames();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } finally{

            if(con != null) {
                con.close();
            }
        }

    }

    @FXML
    public static void savePlayerProperties(Player player) throws SQLException {
        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");


            psInsertUser = con.prepareStatement("INSERT INTO player_properties (user_id, property_id) VALUES (?, ?)");

            for(int i = 0; i < player.getOwnedProperties().size(); i++)
            {
                psInsertUser.setString(1, String.valueOf(player.getId()));
                psInsertUser.setString(2, String.valueOf(properties.indexOf(player.getOwnedProperties().get(i))));
                psInsertUser.executeUpdate();
            }


        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }
    }

    @FXML
    public static void deletePlayerProperties() throws SQLException {
        //Define stuff
        Connection con = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psDeleteUser = null;
        ResultSet resultSet = null;

        try {

            //Get Connetion to Database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/monopoly_jc", "root", "password");


            psDeleteUser = con.prepareStatement("DELETE FROM player_properties WHERE user_id = ?");

            for(int i = 1; i < 5; i++) {
                psDeleteUser.setString(1, String.valueOf(i));
                System.out.println("delete");
                psDeleteUser.executeUpdate();
            }

        }
        catch (SQLException e)
        {

            throw new RuntimeException(e);

        }

        finally {

            if(con != null) {
                con.close();
            }
        }
    }


    //endregion


    public static void error(String alertMessage)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(alertMessage);
        alert.show();
    }

}
