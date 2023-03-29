package com.example.monopoly_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/*

Name: Jonathan Carpenter
Assignment: Monopoly
Description: A Game that looks and feels like Monopoly. You have a player that
moves to different places on the map with dice rolls.

 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(DbUtils.startPageFile));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/monopolyMan.png")));
        stage.getIcons().add(image);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}