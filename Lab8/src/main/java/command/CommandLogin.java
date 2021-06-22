package command;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.DataManager;
import tools.ClientLogger;
import tools.Encoder;
import tools.Speaker;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

public class CommandLogin extends Command{
    public CommandLogin(TextField username, PasswordField password) {
        this.username = username.getText();
        this.password = Encoder.encrypt(password.getText());
        ready = true;
    }
    public CommandLogin(String ... args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.println("Если захотите выйти из цикла ввода напишите \"exit\"");
                System.out.println("Введите логин:");
                username = scanner.nextLine();
                System.out.println("Введите пароль:");
                password = scanner.nextLine();
                if ((!username.equals("exit"))&&(!password.equals("exit"))){
                    password = Encoder.encrypt(password);
                    ready = true;
                } else {
                    System.out.println("Отменяем аунтификацию.");
                }
                break;
            } catch(NoSuchElementException e) {
                System.out.println("Поздравляю, вы сломали ввод. Можете так больше не делать?");
                ClientLogger.logger.log(Level.WARNING, "Во время ввода команды Login ошибка ввода.");
            }
        }
    }

    @Override
    public Speaker event(DataManager collection) {
        boolean result = false;
        try {
            result = collection.login(username, password);
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        }
        if (result) {
            Speaker speaker = new Speaker("Успешный вход.");
            speaker.setPrivateMessage1(username);
            speaker.setPrivateMessage2(password);
            return speaker;
        } else {
            return new Speaker("Неверный логин или пароль.");
        }
    }
}
