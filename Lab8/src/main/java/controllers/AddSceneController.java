package controllers;

import client.Client;
import command.CommandAdd;
import command.CommandAddIfMin;
import command.CommandUpdate;
import element.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.ClientLogger;
import tools.Speaker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;


public class AddSceneController implements Initializable {
    private static Stage stage;
    private static String type = "";
    private static Integer id;
    private Stage thisStage;
    private String thisType;
    private Integer thisId;


    @FXML
    private TextField NAME;

    @FXML
    private TextField X;

    @FXML
    private TextField Y;

    @FXML
    private TextField SALARY;

    @FXML
    private TextField HEIGHT;

    @FXML
    private ChoiceBox<Position> POSITION;

    @FXML
    private ChoiceBox<Status> STATUS;

    @FXML
    private ChoiceBox<Color> EYE;

    @FXML
    private ChoiceBox<Color> HAIR;

    @FXML
    private ChoiceBox<Country> COUNTRY;

    @FXML
    private DatePicker STARTAT;

    @FXML
    private CheckBox PD;

    public static void setType(String type) {
        AddSceneController.type = type;
    }

    public static void setId(Integer id) {
        AddSceneController.id = id;
    }

    public AddSceneController(String type, Integer id) {
        this();
        this.id = id;
        this.type = type;
    }
    public AddSceneController() {
    }

    public static void setStage(Stage stage) {
        AddSceneController.stage = stage;
    }

    @FXML
    public void create() {
        String name = null;
        try {
            name = NAME.getText();
            if (name==null||name.equals("")) throw new NullPointerException();
        } catch(NullPointerException e) {
            callAlert("name");
        }
        double x = 0;
        try {
            x = Double.parseDouble(X.getText());
            if (x<=-623) throw new NullPointerException();
        } catch(NullPointerException|NumberFormatException e) {
            callAlert("x");
            return;
        }
        double y = 0;
        try {
            y = Double.parseDouble(Y.getText());
        } catch(NullPointerException|NumberFormatException e) {
            callAlert("y");
            return;
        }
        double salary = 0;
        try {
            salary = Double.parseDouble(SALARY.getText());
            if (salary<=0) throw new NullPointerException();
        } catch(NullPointerException|NumberFormatException e) {
            callAlert("salary");
            return;
        }
        LocalDate startDate = null;
        try {
            startDate = STARTAT.getValue();
            if (startDate==null|startDate.isBefore(LocalDate.of(1920,1,1))) throw new NullPointerException();
        } catch(NullPointerException|NumberFormatException e) {
            callAlert("start at");
            return;
        }
        Position position = POSITION.getValue();
        Status status = STATUS.getValue();
        Person person = null;
        if (PD.isSelected()) {
            Color hair = HAIR.getValue();
            Color eye = EYE.getValue();
            Country country = COUNTRY.getValue();
            int height = 0;
            try {
                height = Integer.parseInt(HEIGHT.getText());
                if (height<=0) throw new NullPointerException();
            } catch(NullPointerException|NumberFormatException e) {
                callAlert("height");
                return;
            }
            person = new Person(height, eye, hair, country);
        }
        Worker worker = new Worker(name,new Coordinates(x,y), salary, startDate, position, status, person);
        worker.setOwner(Client.getUsername());
        Client client = new Client();
        Speaker speaker = null;
        try {
            if (type.equals("add")) speaker = client.execute(new CommandAdd(worker));
            else if (type.equals("addifmin")) speaker = client.execute(new CommandAddIfMin(worker, id));
            else if (type.equals("update")) speaker = client.execute(new CommandUpdate(worker, id));
            else throw new NullPointerException();
            call("Answer from server", speaker.getMessage(), "If you have problems write me\ntimetocook420@gmail.com");
        } catch(ClassNotFoundException e) {
            ClientLogger.logger.log(Level.WARNING, "Ошибка отправки команды add");
        } catch(NullPointerException e) {
            ClientLogger.logger.log(Level.WARNING, "Неправильно переданный аргумент");
        }
        stage.hide();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        POSITION.setItems(FXCollections.observableArrayList(Position.DIRECTOR, Position.ENGINEER, Position.HEAD_OF_DIVISION));
        STATUS.setItems(FXCollections.observableArrayList(Status.FIRED, Status.RECOMMENDED_FOR_PROMOTION, Status.REGULAR));
        EYE.setItems(FXCollections.observableArrayList(Color.BLUE, Color.BROWN, Color.GREEN));
        HAIR.setItems(FXCollections.observableArrayList(Color.BROWN, Color.ORANGE, Color.WHITE, Color.YELLOW));
        COUNTRY.setItems(FXCollections.observableArrayList(Country.ITALY, Country.GERMANY, Country.RUSSIA, Country.UNITED_KINGDOM));
        this.thisId = id;
        this.thisType = type;
        this.thisStage = stage;
    }

    private void callAlert(String field) {
        call("Format Exception", "You used wrong format for some fields!", "Check field "+field);
    }

    public void call(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
