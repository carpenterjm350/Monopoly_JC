package com.example.monopoly_1;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Controller_StartPage {

    @FXML
    private Button play_Button;



    //region Buttons
    @FXML
    private void playButtonClicked(ActionEvent event) throws IOException {
        DbUtils.changeScene(event, DbUtils.createGamePageFile, "Pick A Game", null, null, false);
    }

    @FXML
    private void LoginButtonClicked(ActionEvent event) throws IOException {
        DbUtils.changeScene(event, DbUtils.loginPageFile, "Login", null, null, false);
    }

    @FXML
    private void SignUpButtonClicked(ActionEvent event) throws IOException {
        DbUtils.changeScene(event, DbUtils.signUpPageFile, "Create Account", null, null, false);
    }

    @FXML
    private void ExitButtonClicked(ActionEvent event) throws IOException {
        System.exit(0);
    }

    //endregion


    public void loggedInDisplay()
    {
        play_Button.setDisable(false);
    }




}
