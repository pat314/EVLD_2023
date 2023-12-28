package com.example.final_dictionary;

import Database.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SavedWordNoteController implements Initializable {

    public static String word;
    @FXML
    private Button discardButton;
    @FXML
    private TextArea noteArea;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String filePath;
        try {
            filePath = "src/main/Note/" + Account.getUsername() + "_" + word + ".txt";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        StringBuilder text = new StringBuilder();
        try {
            FileReader reader = new FileReader(filePath);
            if (reader.ready()) {
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    text.append(line).append("\n");
                }
            }
            reader.close();

        } catch (IOException ignored) {
        }
        noteArea.setText(text.toString());

        discardButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            discardButton.getScene().getWindow().hide();
        }));

        saveButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                String content = noteArea.getText();
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace(new PrintStream(System.err));
            }
            saveButton.getScene().getWindow().hide();
        }));
    }
}