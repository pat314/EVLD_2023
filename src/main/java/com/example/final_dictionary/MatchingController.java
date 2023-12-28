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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MatchingController implements Initializable {

    private final Game d = new Game();
    private final List<Label> selectedLabels = new ArrayList<>();
    @FXML
    private Label box1, box2, box3, box4, box5, box6, box7, box8, box9, box10;
    @FXML
    private Label box11, box12, box13, box14, box15, box16, box17, box18, box19, box20;
    @FXML
    private Label remainingTime, completionTime, record;
    @FXML
    private Button yes, no, replay, start;
    @FXML
    private AnchorPane notification, root, startAnchorpane;
    private boolean clicked = false;
    private Timeline timeline = new Timeline();
    private int elapsedSeconds;
    private int remainingLabels = 20;

    public MatchingController() throws SQLException {

    }

    private List<String> generateRandomValues() throws SQLException {
        List<String> originalValues = d.getMatchingWord();
        List<String> values = new ArrayList<>(originalValues);

        if (values.isEmpty()) {
            return values;
        }
        for (String value : originalValues) {
            String mean = d.getMatchingMean(value);
            values.add(mean);
        }
        Collections.shuffle(values);
        return values;
    }

    private void randomBox() {
        List<Label> boxes = new ArrayList<>(List.of(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10,
                box11, box12, box13, box14, box15, box16, box17, box18, box19, box20));

        List<String> values;
        try {
            values = generateRandomValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < boxes.size(); i++) {
            Label label = boxes.get(i);
            if (label != null) {
                label.setText(values.get(i));
                label.setOnMouseClicked(event -> {
                    try {
                        handleLabelClick(label);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    private void checkMatch(Label label1, Label label2) throws SQLException {
        String text1 = label1.getText();
        String text2 = label2.getText();

        String meaning1 = d.getMatchingMean(text1);
        String meaning2 = d.getMatchingMean(text2);

        boolean isMatch = (meaning1 != null && meaning1.equals(text2)) || (meaning2 != null && meaning2.equals(text1));

        if (isMatch) {
            labelAnimation(label1, label2);

            remainingLabels -= 2;
            if (remainingLabels == 0) {
                remainingLabels = 20;
                d.addMatchingTime(elapsedSeconds);
                showNotification();
            }
        } else {
            label1.setStyle("-fx-background-color: #FFFFFF;;-fx-background-radius: 20;");
            label2.setStyle("-fx-background-color: #FFFFFF;;-fx-background-radius: 20;");
        }
    }

    private void handleLabelClick(Label label) throws SQLException {
        if (selectedLabels.size() < 2) {
            selectedLabels.add(label);
            if (!clicked) {
                clicked = true;
                label.setStyle("-fx-background-color: #FBE5D6;-fx-background-radius: 20;");
            } else {
                clicked = false;
                label.setStyle("-fx-background-color: #FFFFFF;;-fx-background-radius: 20;");
            }

            if (selectedLabels.size() == 2) {
                checkMatch(selectedLabels.get(0), selectedLabels.get(1));
                selectedLabels.clear();
            }
        }
    }

    // Reset all boxes to visible and enable state and randomize the boxes again
    // This method is called when the reset button is clicked
    // This method is also called when the user clicks the "Play Again" button
    private void reset() {
        List<Label> boxes = new ArrayList<>(List.of(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10,
                box11, box12, box13, box14, box15, box16, box17, box18, box19, box20));
        for (Label label : boxes) {
            label.setVisible(true);
            label.setDisable(false);
            FadeTransition transition = new FadeTransition(Duration.seconds(0.2), label);
            transition.setFromValue(0.0);
            transition.setToValue(1.0);
            label.setStyle("-fx-background-color: #FFFFFF;;-fx-background-radius: 20;");
            transition.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Font.loadFont(getClass().getResourceAsStream("image/DEBUG FREE TRIAL.ttf"), 18);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedSeconds++;
            updateTime();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        startTimer();

        for (Node i : root.getChildren()) {
            if (i.getId() == null || !i.getId().equals("startAnchorpane")) {
                i.setDisable(true);
                i.setVisible(false);
            }
        }
        startAnchorpane.setDisable(false);
        startAnchorpane.setVisible(true);
        startAnchorpane.toFront();
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Node i : root.getChildren()) {
                    if (i.getId() == null || (!i.getId().equals("startAnchorpane") && !i.getId().equals("notification"))) {
                        i.setDisable(false);
                        i.setVisible(true);
                    }
                }
                startAnchorpane.setDisable(true);
                startAnchorpane.setVisible(false);
                startAnchorpane.toBack();
                startTime();
                randomBox();
                yes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        remainingLabels = 20;
                        reset();
                        randomBox();
                        quitNotification();
                        startTime();
                    }
                });

                no.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Stage stage = (Stage) no.getScene().getWindow();
                        stage.close();
                    }
                });

                replay.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        remainingLabels = 20;
                        reset();
                        randomBox();
                        startTime();
                    }
                });
            }
        });
    }

    @FXML
    public void labelAnimation(Label label1, Label label2) {
        label1.setStyle("-fx-background-color: #C9FFE1;-fx-background-radius: 20;");
        label2.setStyle("-fx-background-color: #C9FFE1;-fx-background-radius: 20;");

        FadeTransition transition1 = new FadeTransition(Duration.seconds(0.15), label1);
        FadeTransition transition2 = new FadeTransition(Duration.seconds(0.15), label2);
        transition1.setFromValue(1.0);
        transition1.setToValue(0.0);

        transition2.setFromValue(1.0);
        transition2.setToValue(0.0);


        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
        parallelTransition.play();
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
        stopTimer();
        completionTime.setText(remainingTime.getText());
        int time = d.getMatchingTime();
        if (time > elapsedSeconds) time = elapsedSeconds;
        int seconds = time % 60;
        int minutes = time / 60;
        record.setText("Record: " + String.format("%02d:%02d", minutes, seconds));
    }

    @FXML
    public void quitNotification() {
        for (Node i : root.getChildren()) {
            i.setDisable(false);
        }
        notification.setDisable(true);
        notification.setVisible(false);
    }

    @FXML
    public void startTime() {
        stopTimer();
        elapsedSeconds = 0;
        updateTime();
        startTimer();
    }

    private void updateTime() {
        int seconds = elapsedSeconds % 60;
        int minutes = elapsedSeconds / 60;
        remainingTime.setText("Time: " + String.format("%02d:%02d", minutes, seconds));
    }

    @FXML
    private void startTimer() {
        elapsedSeconds = 0;
        updateTime();
        timeline.play();
    }

    @FXML
    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}