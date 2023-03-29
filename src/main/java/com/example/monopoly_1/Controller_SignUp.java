package com.example.monopoly_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_SignUp {


    @FXML
    private Text Error_TextField;

    @FXML
    private TextField password_TextField;

    @FXML
    private TextField username_TextField;


    @FXML
    private void signUpButtonClicked(ActionEvent event) throws SQLException {

        //Set Local Variables
        String username = username_TextField.getText();
        String password = password_TextField.getText();


        if (isValid(username, password))
        {
            //Turn ErrorText Off
            errorText(false, null);


            DbUtils.signUpUser(event, username, password);
        }
        //If Password Is Wrong
        else if (username != null && !username.equals("") && username.length() >= 3)
        {
            errorText(true, "Password is Wrong");
        }
        //Username is Wrong
        else
        {
            errorText(true, "Username is Wrong");
        }
    }


    //region ErrorChecking


    private boolean isValid(String username, String password)
    {
        return (username != null && !username.equals("") && username.length() >= 3) &&
                (password != null && !password.equals("") && password.length() >= 4);
    }

    private void errorText(boolean enable, String text)
    {
        if(enable)
        {
            Error_TextField.setVisible(true);
            Error_TextField.setDisable(false);
            Error_TextField.setText(text);
        }else{
            Error_TextField.setVisible(false);
            Error_TextField.setDisable(true);
        }
    }


    //endregion


    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        DbUtils.changeScene(event, DbUtils.startPageFile, "Home", null, null, false);
    }
}
