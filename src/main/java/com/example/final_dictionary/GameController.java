package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Button launchFlashcard, launchStudying, launchChallenging, launchMatching;
    @FXML
    private AnchorPane flashcardAP, studyingAP, challengingAP, matchingAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        launchFlashcard.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        launchFlashcard.setTooltip(new Tooltip("Launch Flashcard"));

        launchStudying.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        launchStudying.setTooltip(new Tooltip("Launch Studying"));

        launchChallenging.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        launchChallenging.setTooltip(new Tooltip("Launch Challenging"));

        launchMatching.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        launchMatching.setTooltip(new Tooltip("Launch Matching"));

        try {
            loadAP();
            launchFlashcard.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Flashcard.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    PerspectiveCamera camera = new PerspectiveCamera();
                    scene.setCamera(camera);
                    stage.setScene(scene);
                    stage.setTitle("Flashcard");
                    stage.setResizable(false);

                    Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/flashcardPic.png")).toString());
                    stage.getIcons().add(icon);

                    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                    stage.setX((screen.getWidth() - 1200) / 2);
                    stage.setY((screen.getHeight() - 700) / 2);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            launchStudying.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Studying.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Studying");
                    stage.setResizable(false);

                    Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/multiplechoicePic.png")).toString());
                    stage.getIcons().add(icon);

                    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                    stage.setX((screen.getWidth() - 1200) / 2);
                    stage.setY((screen.getHeight() - 700) / 2);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            launchChallenging.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Challenging.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Challenging");
                    stage.setResizable(false);

                    Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/challengePic.png")).toString());
                    stage.getIcons().add(icon);

                    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                    stage.setX((screen.getWidth() - 1200) / 2);
                    stage.setY((screen.getHeight() - 700) / 2);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            launchMatching.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Matching.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Matching");
                    stage.setResizable(false);

                    Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/matching.png")).toString());
                    stage.getIcons().add(icon);

                    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                    stage.setX((screen.getWidth() - 1200) / 2);
                    stage.setY((screen.getHeight() - 700) / 2);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadAP() throws IOException {
        if (flashcardAP == null) {
            flashcardAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Flashcard.fxml")));
        }
        if (studyingAP == null) {
            studyingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Studying.fxml")));
        }
        if (challengingAP == null) {
            challengingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Challenging.fxml")));
        }
        if (matchingAP == null) {
            matchingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Matching.fxml")));
        }
    }
}