package controllers;

import client.Client;
import command.*;
import element.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tools.ClientLogger;
import tools.Speaker;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;

public class WorkSceneController implements Initializable {
    @FXML
    private ChoiceBox<String> LIST;
    @FXML
    private TableView<Worker> TABLE;
    @FXML
    private TableColumn<Worker, Integer> ID;
    @FXML
    private TableColumn<Worker, String> NAME;
    @FXML
    private TableColumn<Worker, Integer> X;
    @FXML
    private TableColumn<Worker, Integer> Y;
    @FXML
    private TableColumn<Worker, Integer> SALARY;
    @FXML
    private TableColumn<Worker, String> STARTAT;
    @FXML
    private TableColumn<Worker, String> CREATIONDATE;
    @FXML
    private TableColumn<Worker, Position> POSITION;
    @FXML
    private TableColumn<Worker, Status> STATUS;
    @FXML
    private TableColumn<Worker, Integer> HEIGHT;
    @FXML
    private TableColumn<Worker, Color> EYE;
    @FXML
    private TableColumn<Worker, Color> HAIR;
    @FXML
    private TableColumn<Worker, Country> COUNTRY;
    @FXML
    private TextField REMOVE;
    @FXML
    private TextField UPDATE;
    @FXML
    private TextField ADDIFLESS;
    @FXML
    private TextField EXECUTE;
    private Client client;

    public WorkSceneController() {
        client = new Client();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        X.setCellValueFactory(new PropertyValueFactory<>("coordX"));
        Y.setCellValueFactory(new PropertyValueFactory<>("coordY"));
        SALARY.setCellValueFactory(new PropertyValueFactory<>("salary"));
        STARTAT.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        CREATIONDATE.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        POSITION.setCellValueFactory(new PropertyValueFactory<>("position"));
        STATUS.setCellValueFactory(new PropertyValueFactory<>("status"));
        HEIGHT.setCellValueFactory(new PropertyValueFactory<>("height"));
        EYE.setCellValueFactory(new PropertyValueFactory<>("eye"));
        HAIR.setCellValueFactory(new PropertyValueFactory<>("hair"));
        COUNTRY.setCellValueFactory(new PropertyValueFactory<>("country"));
        LIST.setItems(FXCollections.observableArrayList("Русский", "Slovak", "Shqiptare", "English(Canada)"));
        LIST.setValue("English(Canada)");
        Thread thread = new Thread(new Shower());
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void commandRemoveLower() {
        execute(new CommandRemoveLower());
    }
    @FXML
    private void commandClear() {
        execute(new CommandClear());
    }
    @FXML
    private void commandAdd() {
        add("add", 0);
    }
    @FXML
    private void commandShow() {
        Speaker speaker;
        try {
            TreeSet<Worker> collection = getCollection();
            TABLE.setItems(FXCollections.observableArrayList(collection));
        } catch(ClassNotFoundException|SQLException e) {
            callAlert("Runtime Error", "There occurred problem while executing command!", "Try again later or write me.");
            ClientLogger.logger.log(Level.WARNING, "Ошибка при отправке команды.");
        } catch(NullPointerException e) {
            callAlert("DB Error", "There are no elements!", "Try to add any one or write me.");
        }
    }

    private void setTable(TreeSet<Worker> collection) {
        TABLE.setItems(FXCollections.observableArrayList(collection));
    }

    public TreeSet<Worker> getCollection() throws SQLException, ClassNotFoundException, NullPointerException {
        Command command = new CommandShow();
        command.setUsername(Client.getUsername());
        Speaker speaker = client.execute(command);
        return speaker.getCollection();
    }

    @FXML
    private void commandInfo() {
        execute(new CommandInfo());
    }
    @FXML
    private void commandHelp() {
        execute(new CommandHelp());
    }
    @FXML
    private void commandRemove() {
        try {
            execute(new CommandRemove(Integer.parseInt(REMOVE.getText())));
        } catch(NumberFormatException e) {
            ClientLogger.logger.log(Level.WARNING,"Введено не число в поле для id");
            callAlert("Format error", "Error in ID-Field", "Check ID-Field, you should write number there.");
        }
    }
    @FXML
    private void commandUpdate() {
        try {
            Integer id = Integer.parseInt(UPDATE.getText());
            add("update", id);
        } catch(NumberFormatException e) {
            ClientLogger.logger.log(Level.WARNING,"Введено не число в поле для id");
            callAlert("Format error", "Error in ID-Field", "Check ID-Field, you should write number there.");
        }
    }
    @FXML
    private void commandAddIfMin() {
        try {
            Integer id = Integer.parseInt(ADDIFLESS.getText());
            add("addifmin", id);
        } catch(NumberFormatException e) {
            ClientLogger.logger.log(Level.WARNING,"Введено не число в поле для id");
            callAlert("Format error", "Error in ID-Field", "Check ID-Field, you should write number there.");
        }
    }
    @FXML
    private void commandExecute() {
        execute(new CommandExecute(0, EXECUTE.getText()));
    }
    @FXML
    private void commandExit() {
        try {
            client.execute(new CommandExit());
        } catch(ClassNotFoundException e) {
            ClientLogger.logger.log(Level.WARNING, "Не удалось отправить команду выхода.");
        }
        System.exit(1);

    }
    private void execute(Command command) {
        Speaker speaker;
        try {
            command.setUsername(Client.getUsername());
            speaker = client.execute(command);
            callAlert("Answer from server!", speaker.getMessage(), "If you have problems check data or text " +
                    "to me:\ntimetocook420@gmail.com");
        } catch(ClassNotFoundException e) {
            ClientLogger.logger.log(Level.WARNING, "Ошибка при отправке команды.");
        }
    }
    private void add(String type, Integer id) {
        try {
            client.addWindow(type, id);
        } catch (IOException e) {
            e.printStackTrace();
            ClientLogger.logger.log(Level.WARNING, "Не удалось открыть окно!");
            callAlert("Add worker", "Error during opening window", "check data or write me");
        }
    }
    public void callAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public List<Worker> getNewElements(TreeSet<Worker> serverList) {
        List<Worker> result = new ArrayList<>();
        for (Worker worker : serverList) {
            if (!TABLE.getItems().contains(worker))
                result.add(worker);
        }
        return result;
    }

    public List<Worker> getDeletedElements(TreeSet<Worker> serverList) {
        List<Worker> result = new ArrayList<>();
        for (Worker worker : TABLE.getItems()) {
            if (!serverList.contains(worker))
                result.add(worker);
        }
        return result;
    }

    class Shower implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    TreeSet<Worker> collected = getCollection();
                    List<Worker> deletedFlats = getDeletedElements(collected);
                    List<Worker> newFlats = getNewElements(collected);
                    setTable(collected);
                    /*for (Worker worker : deletedFlats)
                        vanishFlat(flat);
                    for (Flat flat : newFlats)
                        visualizeFlat(flat);
                    show(null)*/;
                    Thread.sleep(2000);
                }
            } catch (InterruptedException|SQLException|ClassNotFoundException|NullPointerException ignored) {
            }
        }

    }
}
