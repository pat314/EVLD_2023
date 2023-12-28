package com.example.final_dictionary;

import Database.Game;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ChallengingController implements Initializable {
    private static final int MAX_ATTEMPTS = 10;
    private final Game d = new Game();
    private final ArrayList<String> question = d.getQuestion();
    private final Random random = new Random();
    @FXML
    Rectangle rectangle;
    @FXML
    private AnchorPane questionAP1, questionAP2, resultAP, root, startAnchorpane, notification;
    @FXML
    private Button choiceA1, choiceB1, choiceC1, choiceA2, choiceB2, choiceC2, next, start, quit;
    @FXML
    private Label word1, word2, resultText, time, score, finalScore, highestscore;
    @FXML
    private ImageView imageView1, imageView2;
    private int attemptCount = 0;
    private int center = 1;
    private int timeSeconds = 5;
    private Timeline timeline;
    private int score_player = 0;

    public ChallengingController() throws SQLException {

    }

    private void startCountdown() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeSeconds--;
                time.setText(String.format("Time: %02d", timeSeconds));

                if (timeSeconds <= 0) {
                    timeline.stop();
                    try {
                        handleTimeout();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void handleTimeout() throws SQLException {
        showCorrectAnswer();
        resultText.setStyle("-fx-text-fill: #C00000");
        resultText.setText("Time's up! Sorry, you ran out of time. - 5pt");
        score_player -= 5;
        score.setText("Score: " + score_player);
        resultAP.setVisible(true);
        showResultChoice();
        rectangle.toFront();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font.loadFont(getClass().getResourceAsStream("image/DEBUG FREE TRIAL.ttf"), 18);
        for (Node i : root.getChildren()) {
            if (i.getId() == null || !i.getId().equals("startAnchorpane")) {
                i.setVisible(false);
                i.setDisable(true);
            }
        }
        startAnchorpane.setDisable(false);
        startAnchorpane.setVisible(true);
        startAnchorpane.toFront();
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Node i : root.getChildren()) {
                    if (i.getId() == null || (!i.getId().equals("resultAP") && !i.getId().equals("notification"))) {
                        i.setVisible(true);
                        i.setDisable(false);
                    }
                }
                resultAP.setDisable(false);
                startAnchorpane.setDisable(true);
                startAnchorpane.setVisible(false);
                startAnchorpane.toBack();
                if (timeline != null) {
                    timeline.stop();
                }
                timeSeconds = 5;
                time.setText(String.format("Time: %02d", timeSeconds));
                startCountdown();
                try {
                    setQuestionAP1();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                choiceA1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            if (checkAnswer(center, choiceA1.getText())) {
                                choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                                resultText.setStyle("-fx-text-fill: #385723");
                                resultText.setText("Well done! + 10pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                                score_player += 10;
                                timeline.stop();
                            } else {
                                choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                                resultText.setStyle("-fx-text-fill: #C00000");
                                resultText.setText("That’s incorrect! - 5pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                                score_player -= 5;
                                timeline.stop();
                            }
                            score.setText("Score: " + (score_player));
                            showCorrectAnswer();
                            resultAP.setVisible(true);
                            showResultChoice();
                            rectangle.toFront();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                choiceB1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            if (checkAnswer(center, choiceB1.getText())) {
                                choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                                resultText.setStyle("-fx-text-fill: #385723");
                                resultText.setText("Well done! + 10pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                                score_player += 10;
                                timeline.stop();
                            } else {
                                choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                                resultText.setStyle("-fx-text-fill: #C00000");
                                resultText.setText("That’s incorrect! - 5pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                                score_player -= 5;
                                timeline.stop();
                            }
                            score.setText("Score: " + (score_player));
                            showCorrectAnswer();
                            resultAP.setVisible(true);
                            showResultChoice();
                            rectangle.toFront();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

                choiceC1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            if (checkAnswer(center, choiceC1.getText())) {
                                choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                                resultText.setStyle("-fx-text-fill: #385723");
                                resultText.setText("Well done! + 10pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                                score_player += 10;
                                timeline.stop();
                            } else {
                                choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                                resultText.setStyle("-fx-text-fill: #C00000");
                                resultText.setText("That’s incorrect! - 5pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                                score_player -= 5;
                                timeline.stop();
                            }
                            score.setText("Score: " + (score_player));
                            showCorrectAnswer();
                            resultAP.setVisible(true);
                            showResultChoice();
                            rectangle.toFront();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                choiceA2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            if (checkAnswer(center, choiceA2.getText())) {
                                choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                                resultText.setStyle("-fx-text-fill: #385723");
                                resultText.setText("Well done! + 10pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                                score_player += 10;
                                timeline.stop();
                            } else {
                                choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                                resultText.setStyle("-fx-text-fill: #C00000");
                                resultText.setText("That’s incorrect! - 5pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                                score_player -= 5;
                                timeline.stop();
                            }
                            score.setText("Score: " + (score_player));
                            showCorrectAnswer();
                            resultAP.setVisible(true);
                            showResultChoice();
                            rectangle.toFront();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                choiceB2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            if (checkAnswer(center, choiceB2.getText())) {
                                choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                                resultText.setStyle("-fx-text-fill: #385723");
                                resultText.setText("Well done! + 10pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                                score_player += 10;
                                timeline.stop();
                            } else {
                                choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                                resultText.setStyle("-fx-text-fill: #C00000");
                                resultText.setText("That’s incorrect! - 5pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                                score_player -= 5;
                                timeline.stop();
                            }
                            score.setText("Score: " + (score_player));
                            showCorrectAnswer();
                            resultAP.setVisible(true);
                            showResultChoice();
                            rectangle.toFront();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                choiceC2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            if (checkAnswer(center, choiceC2.getText())) {
                                choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                                resultText.setStyle("-fx-text-fill: #385723");
                                resultText.setText("Well done! + 10pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #8BC266, 30, 0.5, 0, 0)");
                                score_player += 10;
                                timeline.stop();
                            } else {
                                choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                                resultText.setStyle("-fx-text-fill: #C00000");
                                resultText.setText("That’s incorrect! - 5pt");
                                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFB097, 30, 0.5, 0, 0)");
                                score_player -= 5;
                                timeline.stop();
                            }
                            score.setText("Score: " + (score_player));
                            showCorrectAnswer();
                            resultAP.setVisible(true);
                            showResultChoice();
                            rectangle.toFront();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                next.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        attemptCount++;
                        if (attemptCount < MAX_ATTEMPTS) {
                            try {
                                if (timeline != null) {
                                    timeline.stop();
                                }
                                timeSeconds = 5;
                                time.setText(String.format("Time: %02d", timeSeconds));
                                startCountdown();
                                refreshChoiceButtonEffect();
                                nextQuestion();
                                rectangle.toBack();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            next.setText("End");
                            try {
                                d.addMultipleChoicePoint(score_player);
                                showNotification();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
        int randomIndex = random.nextInt(question.size());
        word1.setText(question.get(randomIndex));
        setChoices(word1, choiceA1, choiceB1, choiceC1);
        String id = String.valueOf(d.getId(word1.getText()));
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("image/msi/" + id + ".jpg")));
        imageView1.setImage(image);
    }

    @FXML
    public void setQuestionAP2() throws SQLException {
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
    public void showNotification() throws SQLException {
        for (Node i : root.getChildren()) {
            if (i.getId() == null || !i.getId().equals("notification")) {
                i.setDisable(true);
            }
        }
        notification.setVisible(true);
        notification.setDisable(false);
        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.5), notification);
        transition.setFromX(0.1);
        transition.setFromY(0.1);
        transition.setToX(1);
        transition.setToY(1);
        transition.play();
        finalScore.setText("Your score: " + score_player);
        int highestScore = d.getMultipleChoicePoint();
        if (highestScore > score_player) highestScore = score_player;
        highestscore.setText("Highest score: " + highestScore);
    }
}