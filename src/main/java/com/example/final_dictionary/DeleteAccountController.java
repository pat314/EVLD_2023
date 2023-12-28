package com.example.final_dictionary;

import Database.Account;
import Database.Favorite;
import Database.History;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeleteAccountController implements Initializable {
    private final Account dataLite = new Account();
    private final Favorite dataLite1 = new Favorite();
    private final History dataLite2 = new History();
    @FXML
    private TextField email, username;
    @FXML
    private PasswordField password;
    @FXML
    private Button next, confirm, back, okay;
    @FXML
    private Label notificationText;
    @FXML
    private AnchorPane notificationAP, warningAP;

    public DeleteAccountController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        next.setOnAction(actionEvent -> {
            showWarningAP();
        });
        back.setOnAction(actionEvent -> {
            quitWarningAP();
        });
        confirm.setOnAction(actionEvent -> {
            quitWarningAP();
            try {
                checkAccountInformation();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            showNotification();
        });
        okay.setOnAction(actionEvent -> {
            quitNotificationAP();
        });
    }

    @FXML
    public void checkAccountInformation() throws SQLException {
        notificationText.setStyle("-fx-text-fill: red");
        String user = username.getText();
        String pass = password.getText();
        String mail = email.getText();

        if (email.getText().isEmpty() && username.getText().isEmpty() && password.getText().isEmpty()) {
            notificationText.setText("All fields are empty!");
        } else if (email.getText().isEmpty()) {
            notificationText.setText("Email field is empty!");
        } else if (username.getText().isEmpty()) {
            notificationText.setText("Username field is empty!");
        } else if (password.getText().isEmpty()) {
            notificationText.setText("Password field is empty!");
        } else {
            if (!dataLite.checkAccount(user, pass, mail)) {
                notificationText.setText("Your account information is incorrect!");
                return;
            }
            dataLite.deleteAccount(user, pass, mail);
            notificationText.setText("Your account is deleted successfully!");
            notificationText.setStyle("-fx-text-fill: green");
            dataLite1.deleteFavorite();
            dataLite2.deleteHistory();
            quit();
        }
    }

    @FXML
    public void showWarningAP() {
        Parent p = warningAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("warningAP")) {
                i.setDisable(true);
            }
        }
        warningAP.setDisable(false);
        warningAP.setVisible(true);
        warningAP.toFront();
    }

    @FXML
    public void quitWarningAP() {
        Parent p = warningAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("warningAP")) {
                i.setDisable(false);
            }
        }
        warningAP.setDisable(true);
        warningAP.setVisible(false);
        warningAP.toBack();
    }

    @FXML
    public void showNotification() {
        Parent p = notificationAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("notificationAP")) {
                i.setDisable(true);
            }
        }
        notificationAP.setDisable(false);
        notificationAP.setVisible(true);
        notificationAP.toFront();
    }

    @FXML
    public void quitNotificationAP() {
        Parent p = notificationAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("notificationAP")) {
                i.setDisable(false);
            }
        }
        notificationAP.setDisable(true);
        notificationAP.setVisible(false);
        notificationAP.toBack();
    }

    @FXML
    public void quit() {
        try {
            okay.getScene().getWindow().hide();
            Parent borderPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
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
        } catch (IOException ev) {
            throw new RuntimeException(ev);
        }
    }
}