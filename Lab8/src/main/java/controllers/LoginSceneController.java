package controllers;

import client.Client;
import command.Command;
import command.CommandLogin;
import command.CommandRegister;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.ClientLogger;
import tools.Encoder;
import tools.Speaker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class LoginSceneController implements Initializable {
    @FXML
    private ChoiceBox<String> LIST;
    @FXML
    private TextField LOGIN;
    @FXML
    private PasswordField PASSWORD;
    private final Client client;
    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public LoginSceneController() {
        client = new Client();
    }

    @FXML
    private void commandLogin() {
        execute(new CommandLogin(LOGIN, PASSWORD));
    }

    @FXML
    private void commandRegister() {
        execute(new CommandRegister(LOGIN, PASSWORD));
    }

    public void execute(Command command) {
        Speaker speaker;
        try {
            speaker = client.execute(command);
            if (speaker.getMessage().equals("Успешный вход.\n")
                    ||speaker.getMessage().equals("Успешная регистрация.\n")) {
                try {
                    Client.setUsername(LOGIN.getText());
                    Client.setPassword(Encoder.encrypt(PASSWORD.getText()));
                    client.newScene(stage);
                } catch(IOException e) {
                    ClientLogger.logger.log(Level.WARNING, "Не удалось открыть вторую сцену.");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText(speaker.getMessage());
                alert.setContentText("Try to use other profile data or text to me:\ntimetocook420@gmail.com");
                alert.showAndWait();
            }
        } catch(ClassNotFoundException e) {
            ClientLogger.logger.log(Level.WARNING, "Ошибка при отправке команды.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LIST.setItems(FXCollections.observableArrayList("Русский", "Slovak", "Shqiptare", "English(Canada)"));
        LIST.setValue("English(Canada)");
    }
}
