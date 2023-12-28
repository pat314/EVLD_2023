package com.example.final_dictionary;

import Database.AddWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddWordController implements Initializable {
    private final AddWord d = new AddWord();
    @FXML
    private TextArea addwordmeaning;
    @FXML
    private TextField breIPA;
    @FXML
    private TextField posField;
    @FXML
    private TextField wordField;
    @FXML
    private ImageView alert1;
    @FXML
    private ImageView alert2;
    @FXML
    private ImageView alert3;
    @FXML
    private AnchorPane addNotiPane;
    @FXML
    private AnchorPane addwordPane;
    @FXML
    private AnchorPane alertPane;
    @FXML
    private Button discardButton;
    @FXML
    private Button saveButton;

    public AddWordController() throws SQLException {
    }


    public void handleDiscardButton(ActionEvent e) {
        wordField.clear();
        posField.clear();
        breIPA.clear();
        addwordmeaning.clear();
    }

    public void handleSaveButton(ActionEvent e) throws SQLException {
        String word = wordField.getText();
        String pos = posField.getText();
        String bre = breIPA.getText();
        String meaning = addwordmeaning.getText();
        String[] lines = meaning.split("\n");
        if (!word.isEmpty() && !pos.isEmpty() && !meaning.isEmpty()) {
            if (!d.isExist(word)) {
                for (Node i : addwordPane.getChildren()) {
                    i.setDisable(true);
                }
                addNotiPane.setDisable(false);
                addNotiPane.setVisible(true);
                addNotiPane.toFront();
                d.addWord(word, pos, bre, lines);
            } else {
                for (Node i : addwordPane.getChildren()) {
                    i.setDisable(true);
                }
                alertPane.setDisable(false);
                alertPane.setVisible(true);
                alertPane.toFront();
            }

            alert1.setVisible(false);
            alert2.setVisible(false);
            alert3.setVisible(false);
        } else {
            if (word.isEmpty()) alert1.setVisible(true);
            else alert1.setVisible(false);

            if (pos.isEmpty()) alert2.setVisible(true);
            else alert2.setVisible(false);

            if (meaning.isEmpty()) alert3.setVisible(true);
            else alert3.setVisible(false);
        }
    }

    //For addNotiPane
    public void handleOkayButton1(ActionEvent e) {
        for (Node i : addwordPane.getChildren()) {
            i.setDisable(false);
        }

        wordField.clear();
        posField.clear();
        breIPA.clear();
        addwordmeaning.clear();
        addNotiPane.setDisable(true);
        addNotiPane.setVisible(false);
        addNotiPane.toBack();
    }


    public void handleOkayButton2(ActionEvent e) {
        for (Node i : addwordPane.getChildren()) {
            i.setDisable(false);
        }
        wordField.clear();
        posField.clear();
        breIPA.clear();
        addwordmeaning.clear();
        alertPane.setDisable(true);
        alertPane.setVisible(false);
        alertPane.toBack();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordField.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        wordField.setTooltip(new Tooltip("Word title"));

        posField.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        posField.setTooltip(new Tooltip("Part of Speech"));

        breIPA.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        breIPA.setTooltip(new Tooltip("International Phonetic Alphabet (IPA)"));

        addwordmeaning.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        addwordmeaning.setTooltip(new Tooltip("Detailed meaning"));

        discardButton.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        discardButton.setTooltip(new Tooltip("Discard Changes"));

        saveButton.getStylesheets().add(getClass().getResource("fxml/Tooltip.css").toExternalForm());
        saveButton.setTooltip(new Tooltip("Save Changes"));
    }
}