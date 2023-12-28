package com.example.final_dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InfoController {
    @FXML
    private Hyperlink tuphan;
    @FXML
    private Hyperlink anhtuan;
    @FXML
    private Hyperlink tuantran;

    @FXML
    void tuphan(ActionEvent e) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/tuphan22028238"));
    }

    @FXML
    void anhtuan(ActionEvent e) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/anhtuan23004"));
    }

    @FXML
    void tuantran(ActionEvent e) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/tranminhtuan1709"));
    }
}