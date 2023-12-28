package com.example.final_dictionary;

import Database.Favorite;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SaveController implements Initializable {

    private final Favorite d = new Favorite();
    @FXML
    private TextField searchSaveWordBar;
    @FXML
    private VBox savedWordsContainer;
    @FXML
    private ScrollPane SavedList;
    @FXML
    private Label wordCount;
    @FXML
    private Button launchQuiz1Button;
    @FXML
    private Button launchQuiz2Button;
    @FXML
    private Label nothingLabel;
    @FXML
    private ImageView image;

    public SaveController() throws SQLException {
    }


    public void loadListSavedWords() {
        try {
            ArrayList<String> list = d.getFavorite();
            wordCount.setText(0 + " word");
            if (!list.isEmpty()) {
                if (list.size() == 1)
                    wordCount.setText("1 word");
                else wordCount.setText(list.size() + " words");
                SavedList.setVisible(true);
                image.setVisible(false);
                nothingLabel.setVisible(false);
                savedWordsContainer.getChildren().clear();
                savedWordsContainer.setSpacing(10);
                Node[] nodes = new Node[list.size()];

                for (int i = 0; i < nodes.length; i++) {
                    try {
                        SavedItemController.index = i;
                        nodes[i] = FXMLLoader.load(Objects.requireNonNull(SaveController.class.getResource("fxml/Item.fxml")));
                        savedWordsContainer.getChildren().add(nodes[i]);
                    } catch (IOException ev) {
                        ev.printStackTrace(new PrintStream(System.err));
                    }
                }
            } else {
                SavedList.setVisible(false);
                image.setVisible(true);
                nothingLabel.setVisible(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadListSuggestSavedWords(ArrayList<String> suggestList) {
        SavedItemController.option = 1;
        wordCount.setText(0 + " word");
        if (!suggestList.isEmpty()) {
            if (suggestList.size() == 1)
                wordCount.setText("1 word");
            else wordCount.setText(suggestList.size() + " words");
            SavedList.setVisible(true);
            image.setVisible(false);
            nothingLabel.setVisible(false);
            savedWordsContainer.getChildren().clear();
            savedWordsContainer.setSpacing(10);
            Node[] nodes = new Node[suggestList.size()];

            for (int i = 0; i < nodes.length; i++) {
                try {
                    SavedItemController.index = i;
                    nodes[i] = FXMLLoader.load(Objects.requireNonNull(SaveController.class.getResource("fxml/Item.fxml")));
                    savedWordsContainer.getChildren().add(nodes[i]);
                } catch (IOException ev) {
                    ev.printStackTrace(new PrintStream(System.err));
                }
            }
        } else {
            SavedList.setVisible(false);
            image.setVisible(true);
            nothingLabel.setVisible(true);
        }
    }


    public void handleReloadButton(ActionEvent e) {
        loadListSavedWords();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadListSavedWords();
        handleListSaveWord();
        EventBus.subscribe(this::handleReloadButton);

        launchQuiz1Button.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        launchQuiz1Button.setTooltip(new Tooltip("Launch Flashcard"));

        launchQuiz2Button.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        launchQuiz2Button.setTooltip(new Tooltip("Launch Studying"));

        launchQuiz1Button.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
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
        }));

        launchQuiz2Button.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Studying.fxml")));
                Stage stage = new Stage();
                Scene scene = new Scene(parent);
                PerspectiveCamera camera = new PerspectiveCamera();
                scene.setCamera(camera);
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
        }));
    }

    private void handleListSaveWord() {
        searchSaveWordBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            savedWordsContainer.getChildren().clear();
            String in1 = searchSaveWordBar.getText();
            SavedItemController.input = in1;
            ArrayList<String> list1;
            try {
                list1 = d.suggestWordsFa(in1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            loadListSuggestSavedWords(list1);
        });
    }
}