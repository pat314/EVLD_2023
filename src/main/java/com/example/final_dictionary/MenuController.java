package com.example.final_dictionary;

import Database.Account;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private final Account d = new Account();
    @FXML
    public Label welcome;
    @FXML
    private Button homeButton, savedWordButton, translateButton, addWordButton, infoButton, settingButton, gameButton;
    @FXML
    private Button usernameButton;
    @FXML
    private Button quitMenu;
    @FXML
    private Button showMenu;
    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane menuAP;
    @FXML
    private AnchorPane switchAP;
    @FXML
    private AnchorPane usernameAP;
    @FXML
    private Button usernameButton2;
    @FXML
    private Button signOutButton;
    @FXML
    private AnchorPane homeAP, savedWordAP, translateAP, addWordAP, gameAP, infoAP, settingAP;
    @FXML
    private Rectangle section1;
    @FXML
    private Rectangle section2;
    @FXML
    private Rectangle section3;
    @FXML
    private Rectangle section4;
    @FXML
    private Rectangle section5;
    @FXML
    private Rectangle section6;
    @FXML
    private Rectangle section7;

    public MenuController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameButton.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        usernameButton.setTooltip(new Tooltip("User Control Panel"));

        showMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        showMenu.setTooltip(new Tooltip("Menu"));

        quitMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        quitMenu.setTooltip(new Tooltip("Close Menu"));

        try {
            String userName = Account.getUsername();
            usernameButton.setText(userName);
            usernameButton2.setText(userName);
            welcome.setText("Welcome " + userName + "!");
            loadAP();
            switchAP.getChildren().add(homeAP);
            switchAP.toFront();
            switchAP.setVisible(true);
            switchAP.setDisable(false);

            section1.setVisible(true);
            section2.setVisible(false);
            section3.setVisible(false);
            section4.setVisible(false);
            section5.setVisible(false);
            section6.setVisible(false);
            section7.setVisible(false);

            showMenu.setOnAction(actionEvent -> showMenu());
            quitMenu.setOnAction(actionEvent -> {
                quitMenu().play();
            });
            container.setOnMouseClicked(mouseEvent -> {
                if (!menuAP.isHover() && !menuAP.isDisable()) {
                    quitMenu().play();
                }
                if (!usernameAP.isHover() && !usernameAP.isDisable()) {
                    quitUsernameAP();
                }
            });
            usernameButton.setOnAction(actionEvent -> showUsernameAP());
            usernameButton2.setOnAction(actionEvent -> quitUsernameAP());

            savedWordButton.setOnAction(actionEvent -> {
                //put loading saved word here so that the saved word list will be automatically update when adding or eliminating favourite
                try {
                    savedWordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Save.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                switchToSavedWordAP(quitMenu());

                section1.setVisible(false);
                section2.setVisible(true);
                section3.setVisible(false);
                section4.setVisible(false);
                section5.setVisible(false);
                section6.setVisible(false);
                section7.setVisible(false);
            });

            homeButton.setOnAction(actionEvent -> {
                try {
                    homeAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Home.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                switchToHomeAP(quitMenu());

                section1.setVisible(true);
                section2.setVisible(false);
                section3.setVisible(false);
                section4.setVisible(false);
                section5.setVisible(false);
                section6.setVisible(false);
                section7.setVisible(false);
            });

            translateButton.setOnAction(actionEvent -> {
                switchToTranslateAP(quitMenu());

                section1.setVisible(false);
                section2.setVisible(false);
                section3.setVisible(true);
                section4.setVisible(false);
                section5.setVisible(false);
                section6.setVisible(false);
                section7.setVisible(false);
            });

            addWordButton.setOnAction(actionEvent -> {
                switchToAddWordAP(quitMenu());

                section1.setVisible(false);
                section2.setVisible(false);
                section3.setVisible(false);
                section4.setVisible(true);
                section5.setVisible(false);
                section6.setVisible(false);
                section7.setVisible(false);
            });

            settingButton.setOnAction(actionEvent -> {
                switchToSettingAP(quitMenu());

                section1.setVisible(false);
                section2.setVisible(false);
                section3.setVisible(false);
                section4.setVisible(false);
                section5.setVisible(false);
                section6.setVisible(true);
                section7.setVisible(false);
            });

            infoButton.setOnAction(actionEvent -> {
                switchToInfoAP(quitMenu());

                section1.setVisible(false);
                section2.setVisible(false);
                section3.setVisible(false);
                section4.setVisible(false);
                section5.setVisible(false);
                section6.setVisible(false);
                section7.setVisible(true);
            });

            gameButton.setOnAction(actionEvent -> {
                switchToGameAP(quitMenu());

                section1.setVisible(false);
                section2.setVisible(false);
                section3.setVisible(false);
                section4.setVisible(false);
                section5.setVisible(true);
                section6.setVisible(false);
                section7.setVisible(false);
            });
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void showMenu() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(true);
        }
        menuAP.setDisable(false);
        menuAP.setVisible(true);
        menuAP.toFront();
        TranslateTransition transition = new TranslateTransition((Duration.seconds(0.15)), menuAP);
        transition.setFromX(-288);
        transition.setToX(0);
        transition.play();
    }

    @FXML
    public TranslateTransition quitMenu() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(false);
        }
        TranslateTransition transition = new TranslateTransition((Duration.seconds(0.15)), menuAP);
        transition.setFromX(0);
        transition.setToX(-288);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuAP.setDisable(true);
                menuAP.setVisible(false);
                menuAP.toBack();
            }
        });
        return transition;
    }

    @FXML
    public void showUsernameAP() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(true);
        }
        usernameButton.setDisable(true);
        usernameButton.setVisible(false);
        usernameAP.setDisable(false);
        usernameAP.setVisible(true);
        usernameAP.toFront();
    }

    @FXML
    public void quitUsernameAP() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(false);
        }
        usernameAP.setDisable(true);
        usernameAP.setVisible(false);
        usernameButton.setVisible(true);
        usernameButton.setDisable(false);
    }

    @FXML
    public void switchToSavedWordAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(savedWordAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void switchToHomeAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(homeAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void switchToTranslateAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(translateAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void switchToAddWordAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(addWordAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void switchToInfoAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(infoAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void switchToSettingAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(settingAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void switchToGameAP(TranslateTransition transition) {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(gameAP);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.15), switchAP);
        transition1.setFromX(1200);
        transition1.setToX(0);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition);
        parallelTransition.play();
    }

    @FXML
    public void loadAP() throws IOException {
        if (homeAP == null) {
            homeAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Home.fxml")));
        }
        if (savedWordAP == null) {
            savedWordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Save.fxml")));
        }
        if (translateAP == null) {
            translateAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Translate.fxml")));
        }
        if (addWordAP == null) {
            addWordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/AddWords.fxml")));
        }
        if (gameAP == null) {
            gameAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Game.fxml")));
        }
        if (infoAP == null) {
            infoAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Info.fxml")));
        }
        if (settingAP == null) {
            settingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Setting.fxml")));
        }
    }

    @FXML
    public void handleSignOutButton() {
        try {
            d.resetActiveAccount();
            signOutButton.getScene().getWindow().hide();
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