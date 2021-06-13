package client;

import tools.*;
import command.Command;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Main-class консоли.
 * Реализует управление консолью, хранит все глобальные данные.
 * @author Mike Stepanov P3130
 */
public class Client {
    
    private Socket clientSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private int PORT;
    private Speaker speaker;
    private final int REP = 100;
    private InetAddress address;
    private String username = null;
    private String password = null;
    
    static {
        System.setProperty("logback.xml", "../logback.xml");
    }
    
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(
                        () -> {
                            ClientLogger.logger.log(Level.INFO, "Выключение клиента");
                            System.out.println();
                            Client.printCat();
                        }
                )
        );
        ClientLogger.logger.log(Level.INFO, "Клиент запущен");
        Client client = new Client();
    }
    
    public Client() {

        System.out.println("Введите порт для подключения:");
        try {
            PORT = 4242;
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
            if (PORT<=1024||PORT>=65536){
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            ClientLogger.logger.log(Level.WARNING, "Введен неправильный формат порта", e);
            PORT = 4242;
            System.out.println("Введен неправильный формат порта, устанавливаем стандартное значение " + PORT);
        } catch(NoSuchElementException e) {
            ClientLogger.logger.log(Level.WARNING,"Введен некорректный символ при чтении порта", e);
            PORT = 4242;
            System.out.println("Зачем вы ломаете программу?! Ни мучий, апути.");
            System.out.println("Устанавливаем значение по умолчанию - 4242.");
        }
        ClientLogger.logger.log(Level.INFO, "Клиент работает на порту: " + PORT);
        try {
            this.connect();
        } catch(IOException e) {
        }
        try {
            address = clientSocket.getInetAddress();
        } catch (NullPointerException e) {
            System.out.println("Введен неверный порт");
            System.exit(122);
        }

        System.out.println("Добрый день, мы рады вас приветствовать в этой программе,"
                + "\nДля авторизации или регистрации введите login/register.");
        
        listen();

    }
    
    public final void listen() {
        
        String inputString;
        Command command;
        CommandParser cp = new CommandParser();
        Scanner scanner = new Scanner(System.in);
        
        while(scanner.hasNext()) {
            
            inputString = scanner.nextLine();
            command = cp.choice(inputString);

            if (command != null&&command.getUsername()==null) {
                command.setUsername(this.username);
            }

            if (!inputString.equals("")) {
                ClientLogger.logger.log(Level.INFO, "Введена строка " + inputString);
            }

            if ((command != null)&&(command.isReady())) {
                try {

                    speaker = this.execute(command);

                    if (speaker.getMessage().equals("exit\n")) {
                        ClientLogger.logger.log(Level.INFO, "Введено exit, выходим");
                        System.out.println("Работа завершена. До свидания!");
                        System.exit(0);
                    }
                    
                    if (speaker.getMessage().equals("unconnected\n")) {
                        ClientLogger.logger.log(Level.WARNING, "Ошибка подключения к серверу");
                        System.out.println("Сервер не доступен, извините:( Выходим из программы...");
                        System.exit(0);
                    }

                    if (speaker.getMessage().equals("Успешный вход.\n")
                            ||speaker.getMessage().equals("Успешная регистрация.\n")) {
                        username = speaker.getPrivateMessage1();
                        password = speaker.getPrivateMessage2();
                    }
                    
                    speaker.println();
                } catch(ClassNotFoundException e){
                    ClientLogger.logger.log(Level.INFO, "Ответ от сервера в неккоректном формате", e);
                    System.out.println("Ответ пришел в некорректном формате!");
                }
            }
        }
        
    }
    
    public Speaker execute(Command command) throws ClassNotFoundException{
        Speaker tempSpeaker = null;
        try {
            outStream.writeObject(command);
            outStream.flush();
            tempSpeaker = (Speaker) inStream.readObject();
        } catch(IOException e) {
            ClientLogger.logger.log(Level.INFO, "Неудачная попытка подключения");
        }
        return tempSpeaker;
    }
    
    public void connect() throws IOException{
        clientSocket = new Socket("127.0.0.1", PORT);
        outStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outStream.flush();
        inStream = new ObjectInputStream(clientSocket.getInputStream());
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