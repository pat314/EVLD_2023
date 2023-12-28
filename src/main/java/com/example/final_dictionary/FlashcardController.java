package com.example.final_dictionary;

import Database.Game;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FlashcardController implements Initializable {
    private final Game d = new Game();
    private final ArrayList<String> word = d.getFlashcardFront();
    @FXML
    private AnchorPane flashcardFront1, flashcardBack1;
    @FXML
    private AnchorPane flashcardFront2, flashcardBack2;
    @FXML
    private StackPane stackPane1, stackPane2;
    @FXML
    private Button backward, forward;
    @FXML
    private Label ipa1, word1, meaning1;
    @FXML
    private Label ipa2, word2, meaning2;
    @FXML
    private Label quantity;
    private int center = 1;
    private int number = 1;

    public FlashcardController() throws SQLException {

    }

    private void loadFlashcardBack(String word, Label ipa, Label meaning) throws SQLException {
        ArrayList<String> back = d.getFlashcardBack(word);
        if (back.isEmpty()) {
            return;
        }
        ipa.setText(back.get(0));
        meaning.setText(back.get(1));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPane1.setDisable(false);
        stackPane2.setDisable(true);
        try {
            setStackPane1Content();
            quantity.setText(number + " / " + word.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stackPane1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flip(stackPane1, flashcardFront1, flashcardBack1);
            }
        });

        stackPane2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flip(stackPane2, flashcardFront2, flashcardBack2);
            }
        });

        backward.setOnAction(actionEvent -> {
            if (number > 1) {
                number--;
                quantity.setText(number + " / " + word.size());
                TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.25), stackPane1);
                TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.25), stackPane2);
                if (center == 1) {
                    try {
                        setStackPane2Content();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    transition1.setFromX(0);
                    transition1.setToX(1050);
                    transition2.setFromX(-1050);
                    transition2.setToX(0);
                    ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
                    parallelTransition.play();
                    parallelTransition.setOnFinished(actionEvent1 -> {
                        center = 2;
                        unFlip(stackPane1, flashcardFront1, flashcardBack1);
                        unFlip(stackPane2, flashcardFront2, flashcardBack2);
                        stackPane1.setDisable(true);
                        stackPane2.setDisable(false);
                    });
                } else {
                    try {
                        setStackPane1Content();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    transition1.setFromX(-1050);
                    transition1.setToX(0);
                    transition2.setFromX(0);
                    transition2.setToX(1050);
                    ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
                    parallelTransition.play();
                    parallelTransition.setOnFinished(actionEvent1 -> {
                        center = 1;
                        unFlip(stackPane1, flashcardFront1, flashcardBack1);
                        unFlip(stackPane2, flashcardFront2, flashcardBack2);
                        stackPane1.setDisable(false);
                        stackPane2.setDisable(true);
                    });
                }
            }
        });

        forward.setOnAction(actionEvent -> {
            if (number < word.size() - 1) {
                number++;
                quantity.setText(number + " / " + word.size());
                TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.25), stackPane1);
                TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.25), stackPane2);
                if (center == 1) {
                    try {
                        setStackPane2Content();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    transition1.setFromX(0);
                    transition1.setToX(-1050);
                    transition2.setFromX(1050);
                    transition2.setToX(0);
                    ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
                    parallelTransition.play();
                    parallelTransition.setOnFinished(actionEvent1 -> {
                        center = 2;
                        unFlip(stackPane1, flashcardFront1, flashcardBack1);
                        unFlip(stackPane2, flashcardFront2, flashcardBack2);
                        stackPane1.setDisable(true);
                        stackPane2.setDisable(false);
                    });
                } else {
                    try {
                        setStackPane1Content();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    transition1.setFromX(1050);
                    transition1.setToX(0);
                    transition2.setFromX(0);
                    transition2.setToX(-1050);
                    ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
                    parallelTransition.play();
                    parallelTransition.setOnFinished(actionEvent1 -> {
                        center = 1;
                        unFlip(stackPane1, flashcardFront1, flashcardBack1);
                        unFlip(stackPane2, flashcardFront2, flashcardBack2);
                        stackPane1.setDisable(false);
                        stackPane2.setDisable(true);
                    });
                }
            }
        });
    }

    @FXML
    public void setStackPane1Content() throws SQLException {
        word1.setText(word.get(number));
        loadFlashcardBack(word1.getText(), ipa1, meaning1);
    }

    @FXML
    public void setStackPane2Content() throws SQLException {
        word2.setText(word.get(number));
        loadFlashcardBack(word2.getText(), ipa2, meaning2);
    }

    @FXML
    public void flip(StackPane stackPane, AnchorPane flashcardFront, AnchorPane flashcardBack) {
        if (flashcardFront.isVisible()) {
            RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(0.25), stackPane);
            rotateTransition1.setAxis(Rotate.X_AXIS);
            rotateTransition1.setFromAngle(0);
            rotateTransition1.setToAngle(-90);
            rotateTransition1.play();

            flashcardFront.setVisible(false);
            flashcardBack.setVisible(true);
            flashcardBack.setStyle("-fx-scale-y: -1");

            RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(0.25), stackPane);
            rotateTransition2.setFromAngle(-90);
            rotateTransition2.setToAngle(-180);
            rotateTransition2.play();
        } else {
            RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(0.25), stackPane);
            rotateTransition1.setAxis(Rotate.X_AXIS);
            rotateTransition1.setFromAngle(0);
            rotateTransition1.setToAngle(90);
            rotateTransition1.play();

            flashcardFront.setVisible(true);
            flashcardBack.setVisible(false);
            flashcardFront.setStyle("-fx-scale-y: -1");

            RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(0.25), stackPane);
            rotateTransition2.setFromAngle(90);
            rotateTransition2.setToAngle(180);
            rotateTransition2.play();
        }
    }

    @FXML
    public void unFlip(StackPane stackPane, AnchorPane flashcardFront, AnchorPane flashcardBack) {
        if (!flashcardFront.isVisible()) {
            RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(0.01), stackPane);
            rotateTransition1.setAxis(Rotate.X_AXIS);
            rotateTransition1.setFromAngle(0);
            rotateTransition1.setToAngle(90);
            rotateTransition1.play();

            flashcardFront.setVisible(true);
            flashcardBack.setVisible(false);
            flashcardFront.setStyle("-fx-scale-y: -1");

            RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(0.01), stackPane);
            rotateTransition2.setFromAngle(90);
            rotateTransition2.setToAngle(180);
            rotateTransition2.play();
        }
    }
}