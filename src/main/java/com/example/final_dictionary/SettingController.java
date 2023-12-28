package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private Button automaticSoundButton, changePasswordButton, deleteAccountButton;
    @FXML
    private AnchorPane automaticSoundAP, changePasswordAP, deleteAccountAP;
    @FXML
    private AnchorPane switchOptionAP;

    @FXML
    private Rectangle option1;

    @FXML
    private Rectangle option2;

    @FXML
    private Rectangle option3;

    @FXML
    private Rectangle option4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        option1.setVisible(true);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);

        try {
            loadAP();
            //Load default scene
            switchOptionAP.getChildren().clear();
            switchOptionAP.getChildren().add(automaticSoundAP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        automaticSoundButton.setOnAction(actionEvent -> {
            switchToAutomaticSoundAP();
            option1.setVisible(true);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);
        });
        changePasswordButton.setOnAction(actionEvent -> {
            switchToChangePasswordAP();
            option3.setVisible(true);
            option2.setVisible(false);
            option1.setVisible(false);
            option4.setVisible(false);
        });
        deleteAccountButton.setOnAction(actionEvent -> {
            switchToDeleteAccountAP();
            option4.setVisible(true);
            option2.setVisible(false);
            option3.setVisible(false);
            option1.setVisible(false);
        });
    }

    @FXML
    public void switchToAutomaticSoundAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(automaticSoundAP);
    }

    @FXML
    public void switchToChangePasswordAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(changePasswordAP);
    }

    @FXML
    public void switchToDeleteAccountAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(deleteAccountAP);
    }

    @FXML
    public void loadAP() throws IOException {
        if (automaticSoundAP == null) {
            automaticSoundAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/AutomaticSound.fxml")));
        }
        if (changePasswordAP == null) {
            changePasswordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/ChangePassword.fxml")));
        }
        if (deleteAccountAP == null) {
            deleteAccountAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/DeleteAccount.fxml")));
        }
    }
}