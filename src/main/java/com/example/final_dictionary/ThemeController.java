package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ThemeController implements Initializable {
    @FXML
    private Button light, dark;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (light.getStyle().equals("-fx-background-color: green")) {
            changeThemeToLight();
        }
        if (dark.getStyle().equals("-fx-background-color: green")) {
            changeThemeToDark();
        }
        light.setOnAction(actionEvent -> {
            changeThemeToLight();
        });
        dark.setOnAction(actionEvent -> {
            changeThemeToDark();
        });
    }

    @FXML
    public void changeThemeToLight() {
        light.setStyle("-fx-background-color: green");
        dark.setStyle("-fx-background-color: transparent");
    }

    @FXML
    public void changeThemeToDark() {
        light.setStyle("-fx-background-color: transparent");
        dark.setStyle("-fx-background-color: green");
    }
}