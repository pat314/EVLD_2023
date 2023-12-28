package com.example.final_dictionary;

import Database.Account;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    private final Account d = new Account();

    public Main() throws SQLException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/splashScreen.fxml")));
        Scene scene = new Scene(pane);
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
        stage.getIcons().add(icon);

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        stage.setX((screen.getWidth() - 637) / 2);
        stage.setY((screen.getHeight() - 360) / 2);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                d.resetActiveAccount();
            }
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}