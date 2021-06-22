
package server;

import command.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import tools.*;

/**
 * Класс реализующий связь между исполнителем и сервером, создавая новый поток.
 * @author mike
 */
public class SocketAdapter implements Runnable{
    
    private final Socket socket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private Thread thread;
    private final InetAddress clientAddress;
    private ConcurrentHashMap<InetAddress, String> userBase;
    private ExecutorService execPool;
    private ExecutorService writePool;
    
    static {
        System.setProperty("logback.xml", "../logback.xml");
    }
    
    public SocketAdapter(Socket socket, ConcurrentHashMap<InetAddress, String> userBase, ExecutorService execPool, ExecutorService writePool) throws IOException{
        /*
        Запоминаем сокет, открываем потоки ввода-вывода, запускаем новый поток.
        */
        this.socket = socket;
        clientAddress = socket.getInetAddress();
        this.userBase = userBase;
        this.execPool = execPool;
        this.writePool = writePool;
        outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.flush();
        inStream = new ObjectInputStream(socket.getInputStream());
        thread = new Thread();
    }
    
    @Override
    public void run() {
        /*
        Получаем команду от пользователя.
        Пытаемся ее исполнить и отправить ответ в виде текста.
        Иначе отправляем стандартный ответ об ошибке.
        */
        while(thread!=null) {

            Command command = null;

            try {
                command = (Command) inStream.readObject();
            } catch (IOException e) {
                System.out.println("Не смогли прочитать запрос пользователя.");
            } catch(ClassNotFoundException e) {
                System.out.println("Запрос пользователя пришёл в некорректном формате.");
            }

            Command finalCommand = command;
            Speaker spk = null;
            try {
                spk = execPool.submit(() -> {
                    Speaker message = null;
                    try {
                        if (finalCommand.getUsername() != null) {
                            Executor executor = new Executor(finalCommand);
                            message = executor.execute();
                            if (message.getMessage().equals("Успешный вход.\n")
                                    || message.getMessage().equals("Успешная регистрация.\n")) {
                                message.setPrivateMessage1(finalCommand.getUsername());
                                message.setPrivateMessage2(finalCommand.getPassword());
                                userBase.put(clientAddress, finalCommand.getUsername());
                            }
                        } else {
                            message = new Speaker("Сначала нужно зарегестрироваться или залогиниться.");
                        }
                        if (message.getMessage().equals("exit\n")) {
                            ServerLogger.logger.info("Пользователь ввел команду на отключение");
                            thread = null;
                        }
                    } catch (NullPointerException e) {
                        thread = null;
                    }

                    return message;
                }).get();
            } catch (InterruptedException e) {
                System.out.println("Поток выполнения команды был прерван.");
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println("Ошибка выполнения команды.");
            }
            Speaker finalSpk = spk;
            writePool.submit(() -> {
                try {
                    outStream.writeObject(finalSpk);
                    outStream.flush();
                } catch (IOException e) {
                    ServerLogger.logger.log(Level.WARNING, "Поток был прерван", e);
                    System.out.println("Поток " + clientAddress + ": не удалось отправить ответ пользователю");
                    thread.interrupt();
                    thread = null;
                }
            });
        }

    }
}
