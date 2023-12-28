package com.example.final_dictionary;

import Database.Favorite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SavedItemController implements Initializable {

    public static int index = 0;
    public static int option = 0;
    public static String input = "";
    private final Favorite d = new Favorite();
    private final ArrayList<String> wordList = d.getFavorite();
    private final ArrayList<String> pronounceList = d.getFavoritePOS();
    private final ArrayList<String> detailList = d.getFavoriteDetail();
    private final ArrayList<String> suggestWordList = d.suggestWordsFa(input);
    private final ArrayList<String> suggestPronounceList = d.suggestPronounceFa(input);
    private final ArrayList<String> suggestDetailList = d.suggestDetailFa(input);
    @FXML
    private Label detailLabel;
    @FXML
    private Button noteButton;
    @FXML
    private Label ipaLabel;
    @FXML
    private Button trashButton;
    @FXML
    private Label wordLabel;


    public SavedItemController() throws SQLException {
    }

    /**
     * Update the label of word, IPA, and detail of each card in the list.
     */
    public void updateItem() {
        if (option == 0) {
            wordLabel.setText(wordList.get(index));
            ipaLabel.setText(pronounceList.get(index));
            detailLabel.setText(detailList.get(index));
        } else if (option == 1) {
            wordLabel.setText(suggestWordList.get(index));
            ipaLabel.setText(suggestPronounceList.get(index));
            detailLabel.setText(suggestDetailList.get(index));
        }

    }

    public void handleNoteButton(ActionEvent e) {
        try {
            SavedWordNoteController.word = wordLabel.getText();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/SavedWordNote.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.setResizable(false);

            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            stage.setX((screen.getWidth() - 600) / 2);
            stage.setY((screen.getHeight() - 400) / 2);

            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void handleDeleteButton(ActionEvent e) {
        try {
            if (d.isExistFavorite(wordLabel.getText())) {
                //delete word from database
                d.deleteFavorite(wordLabel.getText());
                //delete file .txt
                try {
                    String userName = d.getUsername();
                    File file = new File("src/main/Note/" + userName + "_" + wordLabel.getText() + ".txt");
                    if (file.delete()) {
                        //nothing
                    }
                } catch (Exception ee) {
                    ee.printStackTrace(new PrintStream(System.err));
                }
            }
        } catch (SQLException ev) {
            throw new RuntimeException(ev);
        }
        //throw an action event to call reload page in save controller
        EventBus.publish(new ActionEvent());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateItem();
        noteButton.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        noteButton.setTooltip(new Tooltip("Add your note"));

        trashButton.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        trashButton.setTooltip(new Tooltip("Remove from Saved words"));
    }
}