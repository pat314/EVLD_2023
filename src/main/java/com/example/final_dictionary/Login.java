package com.example.final_dictionary;

import Database.Account;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.PrintStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Login implements Initializable {

    private final Account dataLite = new Account();
    @FXML
    public PasswordField su_pass;
    @FXML
    public Button signUpButton;
    @FXML
    public TextField su_email;
    @FXML
    public TextField su_username;
    @FXML
    private Hyperlink createAcc;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink signIn;
    @FXML
    private TextField username;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane login_form;
    @FXML
    private Rectangle fadeRectangle;
    @FXML
    private Button gettingStartedButton;
    @FXML
    private Label dictionaryLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView image;
    @FXML
    private AnchorPane gettingStartedPane;

    public Login() throws SQLException {
    }

    public void login() {
        //connect = connectDB();
        try {
            String user = username.getText();
            String pass = password.getText();
            if (dataLite.checkLogin(user, pass)) {
                dataLite.setActiveAccount(user, pass);
                //Show the dictionary after successful login
                //javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loginButton.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                Scene scene = new Scene(root);
                Stage stage = new Stage();


                Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                stage.getIcons().add(icon);

                stage.setTitle("English - Vietnamese Learner's Dictionary");

                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                stage.setX((screen.getWidth() - 1200) / 2);
                stage.setY((screen.getHeight() - 700) / 2);

                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password.\n Please try again or sign up!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception event) {
            event.printStackTrace(new PrintStream(System.err));
        }
    }


    public void login_enter() {
        //connect = connectDB();
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        String user = username.getText();
                        String pass = password.getText();
                        if (dataLite.checkLogin(user, pass)) {
                            dataLite.setActiveAccount(user, pass);
                            //Show the dictionary after successful login
                            //javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            loginButton.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                            Scene scene = new Scene(root);
                            Stage stage = new Stage();


                            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                            stage.getIcons().add(icon);

                            stage.setTitle("English - Vietnamese Learner's Dictionary");

                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                            stage.setX((screen.getWidth() - 1200) / 2);
                            stage.setY((screen.getHeight() - 700) / 2);

                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ev) {
                        ev.printStackTrace(new PrintStream(System.err));
                    }
                }
            }
        });
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        String user = username.getText();
                        String pass = password.getText();
                        if (dataLite.checkLogin(user, pass)) {
                            dataLite.setActiveAccount(user, pass);
                            //Show the dictionary after successful login
                            //javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            loginButton.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                            //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();


                            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                            stage.getIcons().add(icon);

                            stage.setTitle("English - Vietnamese Learner's Dictionary");

                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                            stage.setX((screen.getWidth() - 1200) / 2);
                            stage.setY((screen.getHeight() - 700) / 2);

                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ev) {
                        ev.printStackTrace(new PrintStream(System.err));
                    }
                }
            }
        });
    }

    /*
     **********************************************************************************************************************
     * SIGN UP
     */
    public boolean patternMatches(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @FXML
    public void signup() {
        //connect = connectDB();
        try {
            String user = su_username.getText();
            String pass = su_pass.getText();
            String email = su_email.getText();
            if (patternMatches(email)) {
                if (dataLite.isExistAccount(user)) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Account is exist!", "System Alert", JOptionPane.ERROR_MESSAGE);
                } else {
                    dataLite.signUp(user, pass, email);
                    javax.swing.JOptionPane.showMessageDialog(null, "Sign up successfully, please sign in now!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Wrong email format. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            signup_form.setVisible(false);
            login_form.setVisible(true);

        } catch (Exception event) {
            event.printStackTrace(new PrintStream(System.err));
        }
    }

    @FXML
    public void changeForm(ActionEvent event) {
        if (event.getSource() == createAcc) {
            signup_form.setVisible(true);
            login_form.setVisible(false);

        } else if (event.getSource() == signIn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
        }
    }

    private void addShutdownHook() {
        Thread shutdownThread = new Thread(() -> {
            try {
                dataLite.resetActiveAccount();
            } catch (Exception e) {
                e.printStackTrace(new PrintStream(System.err));
            }
        });

        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), fadeRectangle);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        FadeTransition fade2 = new FadeTransition(Duration.millis(1500), dictionaryLabel);
        fade2.setFromValue(0.0);
        fade2.setToValue(1.0);

        FadeTransition fade3 = new FadeTransition(Duration.millis(1500), descriptionLabel);
        fade3.setFromValue(0.0);
        fade3.setToValue(1.0);

        FadeTransition fade4 = new FadeTransition(Duration.millis(3000), gettingStartedButton);
        fade4.setFromValue(0.0);
        fade4.setToValue(1.0);

        FadeTransition fade5 = new FadeTransition(Duration.millis(1000), image);
        fade5.setFromValue(1.0);
        fade5.setToValue(0.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1000));
        pauseTransition.play();

        pauseTransition.setOnFinished(event -> {
            fade5.play();
            fade.play();
            fade2.play();
            fade3.play();
            fade4.play();
            image.setDisable(true);

        });

        FadeTransition fade6 = new FadeTransition(Duration.millis(100), gettingStartedPane);
        fade6.setFromValue(1.0);
        fade6.setToValue(0.0);

        FadeTransition fade7 = new FadeTransition(Duration.millis(100), login_form);
        fade7.setFromValue(0.0);
        fade7.setToValue(1.0);

        gettingStartedButton.setOnAction(event -> {
            fade6.play();
            fade7.play();
            gettingStartedPane.setDisable(true);
        });

        username.getStylesheets().add(Objects.requireNonNull(getClass().getResource("fxml/Tooltip.css")).toExternalForm());
        username.setTooltip(new Tooltip("Type your username here"));
        password.setTooltip(new Tooltip("Type your password here"));
        su_email.setTooltip(new Tooltip("Type your email here"));
        su_username.setTooltip(new Tooltip("Type your username here"));
        su_pass.setTooltip(new Tooltip("Type your password here"));
        // TODO
        addShutdownHook();
    }
}