package com.example.final_dictionary;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Splash implements Initializable {

    private AnchorPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        splash();
    }

    private void splash() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(999);
                } catch (Exception e) {
                    //System.out.println(e);
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            borderPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
                            Stage stage = new Stage();
                            Scene scene = new Scene(borderPane);
                            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();


                            stage.getIcons().add(icon);
                            stage.setTitle("Login");
                            stage.setScene(scene);
                            stage.setX((screen.getWidth() - 1000) / 2);
                            stage.setY((screen.getHeight() - 800) / 2);
                            stage.setResizable(false);

                            stage.show();
                            anchorPane.getScene().getWindow().hide();
                        } catch (IOException exception) {
                            exception.printStackTrace(new PrintStream(System.out));
                        }
                    }
                });
            }
        }.start();
    }
}