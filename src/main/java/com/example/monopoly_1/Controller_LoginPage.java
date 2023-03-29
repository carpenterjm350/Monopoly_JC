package com.example.monopoly_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_LoginPage {

    @FXML
    private TextField password_TextField;

    @FXML
    private TextField username_TextField;


    @FXML
    private void loginButtonClicked(ActionEvent event) throws SQLException {

        //Set Local Variables
        String username = username_TextField.getText();
        String password = password_TextField.getText();


        DbUtils.loginUser(event, username, password);
    }

    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        DbUtils.changeScene(event, DbUtils.startPageFile, "Home",null, null, false);
    }

}
