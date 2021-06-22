package client;
import controllers.AddSceneController;
import controllers.LoginSceneController;
import controllers.WorkSceneController;
import element.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tools.*;
import command.Command;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Main-class консоли.
 * Реализует управление консолью, хранит все глобальные данные.
 * @author Mike Stepanov P3130
 */
public class Client extends Application{
    
    private static Socket clientSocket;
    private static ObjectInputStream inStream;
    private static ObjectOutputStream outStream;
    private static int PORT = 4242;
    private Speaker speaker;
    private final int REP = 100;
    private InetAddress address;
    private static String username = null;
    private static String password = null;
    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles.Resources");

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static void setBundle(ResourceBundle bundle) {
        Client.bundle = bundle;
    }

    static {
        System.setProperty("logback.xml", "../logback.xml");
    }

    public void newScene(Stage primaryStage) throws IOException{
        primaryStage.hide();
        WorkSceneController.setStage(primaryStage);
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("workScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("You guessed right");
        loader.setController(new WorkSceneController());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("loginScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Try to guess!");
        try {
            InputStream iconStream = getClass().getResourceAsStream("logo.png");
            if (iconStream == null)
                throw new NullPointerException();
            Image image = new Image(iconStream);
            primaryStage.getIcons().add(image);
        } catch(NullPointerException e) {
            ClientLogger.logger.log(Level.WARNING, "Не найдена иконка.");
        }
        LoginSceneController.setStage(primaryStage);
        loader.setController(new LoginSceneController());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(
                        () -> {
                            ClientLogger.logger.log(Level.INFO, "Выключение клиента");
                            System.out.println();
                        }
                )
        );
        ClientLogger.logger.log(Level.INFO, "Клиент запущен");
        try {
            connect();
        } catch(IOException e) {
            ClientLogger.logger.log(Level.WARNING, "Не можем подключиться к серверу");
            System.exit(1);
        }
        Application.launch();
    }

    public Client(){
    }

    public void addWindow(String type, Integer id) throws IOException{
        Stage stage = new Stage();
        AddSceneController.setStage(stage);
        AddSceneController.setType(type);
        AddSceneController.setId(id);
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("addScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        try {
            InputStream iconStream = getClass().getResourceAsStream("logo.png");
            if (iconStream == null)
                throw new NullPointerException();
            Image image = new Image(iconStream);
            stage.getIcons().add(image);
        } catch(NullPointerException e) {
            ClientLogger.logger.log(Level.WARNING, "Не найдена иконка.");
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Add worker");
        loader.setController(new AddSceneController(type, id));
        stage.setScene(scene);
        stage.show();
    }

    public Speaker execute(Command command) throws ClassNotFoundException{
        if (command.getUsername()==null) command.setUsername(getUsername());
        Speaker tempSpeaker = null;
        try {
            outStream.writeObject(command);
            outStream.flush();
            tempSpeaker = (Speaker) inStream.readObject();
        } catch(IOException e) {
            ClientLogger.logger.log(Level.INFO, "Неудачная попытка подключения");
        } catch(NullPointerException e) {
            ClientLogger.logger.log(Level.WARNING, "Сервер не запущен, выходим.");
            System.out.println("Сервер ещё не запущен, выходим...");
            System.exit(1);
        }
        return tempSpeaker;
    }
    
    public static void connect() throws IOException{
        clientSocket = new Socket("127.0.0.1", PORT);
        outStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outStream.flush();
        inStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }
    public static void setPassword(String newPassword) {
        password = newPassword;
    }

    public static String getUsername() {
        return username;
    }

    public static void printCat() {
        System.out.println("########################%+::*****++*+=++****+*+*::+++*+::--./&!!&&/\\-.-@#%%%%%%%%%%%%%%%%%%@@@@@@@@@\n" +
"#######################%\\|||||&!!&|/-*****++*+**-::::*+:::-.\\||!&///\\\\\\=###%%=%%%%%%%%%%%%%%@@@@@@@@\n" +
"#######################=/|&&|||||//\\-*++***:--::---.\\.---:-.//||||\\\\|!|-@##@===%%%%%%%%%%%%%%%@@%%@@\n" +
"########################=||||//\\\\.\\.:++*:---.\\\\\\..\\\\-\\\\.---.\\\\\\/&&\\/|&&:####%===%%%%%%%%%%%%%%%%%%@@\n" +
"########################@*||//\\--...:*++:-:--.\\||||&&|/\\..--\\\\\\\\//||/&!-@###@=%===%%=%%%%%%%%%%%%%@@\n" +
"#########################@\\||/\\.----::*:---.|!&!;$;;№!||/\\.-./\\/\\.\\///.%#####=====%=====%%%%%%%%%%%%\n" +
"#########################@+||/\\.*::------\\.-|!&&$;;;;^;№&/.-.\\///\\...\\-*%@###%=========%%%%%%%%%%%%%\n" +
"###########################=&&|-*:-..\\-:-&№.|!|/$'\"\"''^№&|//.\\-.-**:.-:=%@###@=========%==%=%%%%%%%%\n" +
"############################%:*+**+:-\\\\..:--!;!№^]]\"''!+%:+@%*/.-:*::-:*=@####@#####%========%%%%%%%\n" +
"#############################%===++*:\\:@@=.**!;;^\"\"'';/\\/*.=.&/-\\/\\.-::*+%@##########@======%=%%%%%%\n" +
"##############################%+*:--:/|*%@=:*|$$$$^^;;№№$$;!/..\\\\\\\\//\\.:%################%==%%%%%%%%\n" +
"##############################@=*.\\\\\\-.&;^'';^^№;$^^$;'\"]]]'^!||!&||//-+@####################%%%%%%%\n" +
"##############################@+:-.\\|&№$'\"\"\"\"';&№;$;№^\"]'\"\"\";$;;№&&&|.:+%######################@%%%%\n" +
"###############################=*-\\|&!!;^;^^$;;!&№№№$;^^^^;;$^^^^$;!&/:%%@#######################%%%\n" +
"###############################%:.\\\\|!!!!№$^^^^$№!&!№;;;;$^^;;^^$;$;!|.*=%@@######################%%\n" +
"################################%**.\\|!№№!&!№;;№!&&!&!№№;;$^^^$$^;;^№&\\-+%@@#######################%\n" +
"#################################@=://\\|!№№!!!№№;;$$^$^^^^$^^;''''';$№!/:*%@@@@#####################\n" +
"###############################@@@+:*:./||&&!№№;;;$$$$$$$^^;''\"\"\"\"\";$;!/-*=%@@@@@@#############%==%%\n" +
"##############################@@%=%=:./&№;^^$$^$$;;$^^;'''\"\"\"\"\"\"\"\"\";$№&/.*+%%@@@@############@+++===\n" +
"#############################@%=%%%=*:./|№$^;'\"\"'''\"\"\"\"]]]]]]\"]\"\"\"';$!&/.:+%@@@@@@@#########========\n" +
"#############################@%=+==+*:-\\|!;^;']\"\"]]]]]]]]]]\"\"\"\"\"\"';^$!&/.:*=%@@@@@@#######%*++++++==\n" +
"#############################@@=+==++:-/|&!№;;''\"\"\"]\"]]\"\"\"\"\"\"\"'';;^$!&|\\-:+%@@@#@@@#####@*******++++\n" +
"############################@%=+*++**:-../&!&!;^;''\"\"\"\"\"''''';;;^$№!|||/-*+%@@@@@@@@#@@==+****+*++++\n" +
"##################@#########@%=+*++*:--.\\/\\/|&!№;$^;;;;'\"'';;;^$№&&||///.:+%@@@@@@##%+++=+++++*****+\n" +
"############################@%%=*++****--.\\/|!!!!!№;;$^;;;;^^^$;!&|/|//\\.:+%@@@@@#@%+++++++++*+***++\n" +
"############################@@%==+*::::-../\\.\\|!!№!!!№;$$$$^$;;$;№!&||/\\.-:*=@####%+++++++++******++\n" +
"############################@%%=+*++***=*::::./&&!;№№!№№№!&!&&&&№;№!!||///\\.=@#####%=++++++++++++++*\n" +
"#############################%=++=+***+==++:./&&!!№№!!№;$!&&&&&&;^^$$№&||//:=@#######@=*+++++=++++++\n" +
"##############################%++==+++*+++*:..\\/|&&!&!;^$№|//\\&№;$$$$;!&&|-+%@#@@@@@@##=**++*+%=++*+\n" +
"###############################@%++**++++*:-.-\\/\\/\\&№$^^$№/\\:+/;;№$$$;;;№!\\+======%%%%@@#=**+**=%+*+\n" +
"#################################@=+*::*+=+-.//|!№$$$;$$$;$$;№№№№;;;;;&№№№&\\*==++++==%%%@##=*=+*+%=+\n" +
"###########################@@@@###@@=.\\.-*%=+\\&!&!!&|!!!№;$$;№&//\\.---::***++++=+=====%%%###%==++++=\n" +
"#################@@@@@@@@@%%%%%%===++**:::::------::-.\\\\\\\\\\.....--:****+++++++++++=+==%%%@###@===+++\n" +
"##########@@@@@@@@@@%%%==+++****:::::::----:::-------.--::-:::-:-:--::::****++++++++++====%%@@======\n" +
"######@@@@@@@%%%=+++************:********:::::*:::----..-.......-----:::::*************++=%%@##@####\n" +
"#@@@@%%%======++++++++++++==++************:::::--...\\.\\.\\\\\\\\\\/\\\\.....----------::::-:::::*+=%@######\n" +
"%%%%%%%%%%%=====%===+++*****:::----::--:--.....\\\\\\//\\/////|/||||///////\\\\\\\\\\\\\\\\\\..\\....---::*=@#####\n" +
"%%%%%%%%==++++++*****:**::------.-.\\\\\\.\\\\\\\\\\\\\\////////|||/|/|||||||||||||||||//////\\\\\\\\...-:**===%=%\n");
    }
}