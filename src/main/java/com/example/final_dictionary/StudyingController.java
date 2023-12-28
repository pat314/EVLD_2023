package com.example.final_dictionary;

import Database.Game;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class StudyingController implements Initializable {
    private static final int MAX_ATTEMPTS = 20;
    private final Game d = new Game();
    private final ArrayList<String> question = d.getQuestion();
    private final Random random = new Random();
    @FXML
    Rectangle rectangle;
    @FXML
    private AnchorPane questionAP1, questionAP2, resultAP, notification;
    @FXML
    private Button choiceA1, choiceB1, choiceC1, choiceA2, choiceB2, choiceC2, next, quit;
    @FXML
    private Label word1, word2, resultText;
    @FXML
    private ImageView imageView1, imageView2;
    private int attemptCount = 0;
    private int center = 1;

    public StudyingController() throws SQLException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setQuestionAP1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        choiceA1.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceA1.getText())) {
                    choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                } else {
                    choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                }
                showCorrectAnswer();
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceB1.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceB1.getText())) {
                    choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                } else {
                    choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                }
                showCorrectAnswer();
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceC1.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceC1.getText())) {
                    choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                } else {
                    choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                }
                showCorrectAnswer();
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceA2.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceA2.getText())) {
                    choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                } else {
                    choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                }
                showCorrectAnswer();
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceB2.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceB2.getText())) {
                    choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                } else {
                    choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                }
                showCorrectAnswer();
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceC2.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceC2.getText())) {
                    choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                } else {
                    choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                }
                showCorrectAnswer();
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        next.setOnAction(actionEvent -> {
            attemptCount++;
            if (attemptCount <= MAX_ATTEMPTS) {
                try {
                    refreshChoiceButtonEffect();
                    nextQuestion();
                    rectangle.toBack();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                next.setText("Finish");
                System.out.println("Congratulation!");
                showNotification();
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                quitNotification();
                Stage stage = (Stage) quit.getScene().getWindow();
                stage.close();
            }
        });
    }

    @FXML
    public void setChoices(Label word, Button choiceA, Button choiceB, Button choiceC) throws SQLException {
        ArrayList<String> choiceList = d.getChoice(String.valueOf(word.getText()));
        if (choiceList.isEmpty()) {
            return;
        }
        Collections.shuffle(choiceList);
        choiceA.setText(choiceList.get(0));
        choiceB.setText(choiceList.get(1));
        choiceC.setText(choiceList.get(2));
    }

    @FXML
    public void nextQuestion() throws SQLException {
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.5), questionAP1);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.5), questionAP2);
        if (center == 1) {
            transition1.setFromX(0);
            transition1.setToX(-1135);
            transition2.setFromX(1135);
            transition2.setToX(0);
            setQuestionAP2();
            center = 2;
        } else {
            transition1.setFromX(1135);
            transition1.setToX(0);
            transition2.setFromX(0);
            transition2.setToX(-1135);
            setQuestionAP1();
            center = 1;
        }
        hideResultChoice(transition1, transition2);
    }

    @FXML
    public void showResultChoice() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), resultAP);
        transition.setFromY(710);
        transition.setToY(0);
        transition.play();
    }

    @FXML
    public void hideResultChoice(TranslateTransition transition1, TranslateTransition transition2) {
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(0.5), resultAP);
        transition3.setFromY(0);
        transition3.setToY(710);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2, transition3);
        parallelTransition.play();
    }

    @FXML
    public boolean checkAnswer(int center, String answer) throws SQLException {
        if (center == 1 && answer.equals(d.getAnswer(word1.getText()))) {
            return true;
        } else if (center == 2 && answer.equals(d.getAnswer(word2.getText()))) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void setQuestionAP1() throws SQLException {
        //Image image = new Image("image/anime.jpg");
        //imageView1.setImage(image);
        int randomIndex = random.nextInt(question.size());
        word1.setText(question.get(randomIndex));
        setChoices(word1, choiceA1, choiceB1, choiceC1);
        String id = String.valueOf(d.getId(word1.getText()));
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/msi/" + id + ".jpg")));
        imageView1.setImage(image);
    }

    @FXML
    public void setQuestionAP2() throws SQLException {
        //Image image = new Image("image/anime.jpg");
        //imageView2.setImage(image);
        int randomIndex = random.nextInt(question.size());
        word2.setText(question.get(randomIndex));
        setChoices(word2, choiceA2, choiceB2, choiceC2);
        String id = String.valueOf(d.getId(word2.getText()));
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/msi/" + id + ".jpg")));
        imageView2.setImage(image);
    }

    @FXML
    public void refreshChoiceButtonEffect() {
        choiceA1.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceB1.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceC1.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");

        choiceA2.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceB2.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceC2.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
    }

    @FXML
    public void showCorrectAnswer() throws SQLException {
        if (center == 1) {
            if (choiceA1.getText().equals(d.getAnswer(word1.getText()))) {
                choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
            } else if (choiceB1.getText().equals(d.getAnswer(word1.getText()))) {
                choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
            } else {
                choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
            }
        } else {
            if (choiceA2.getText().equals(d.getAnswer(word2.getText()))) {
                choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
            } else if (choiceB2.getText().equals(d.getAnswer(word2.getText()))) {
                choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
            } else {
                choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
            }
        }
    }

    @FXML
    public void showNotification() {
        notification.setVisible(true);
        notification.setDisable(false);
        notification.toFront();
    }

    @FXML
    public void quitNotification() {
        notification.setVisible(false);
        notification.setDisable(true);
        notification.toBack();
    }
}