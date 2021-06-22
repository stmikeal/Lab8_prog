package command;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import element.Worker;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import server.DataManager;
import tools.CommandParser;
import tools.Speaker;

/**
 * Класс-команда execute_script.
 *
 * @author mike
 */
public class CommandExecute extends Command{

    static Deque<String> stack = new ArrayDeque<>();

    /**
     * Выполняет скрипт из переданного файла.
     *
     * @param console
     * @param args
     */
    
    private String path = "";
    private String next;

    public CommandExecute(Integer i, String path) {
        next = path;
        this.ready = true;
    }
    public CommandExecute(String ... args){
        try {
            next = args[1];
            this.ready = true;
        } catch(Exception e) {
            this.ready = false;
        }
        
    }
    
    @Override
    public Speaker event(DataManager collection) throws ExecutionException {
        if (stack.contains(next)) {
            speaker = new Speaker("Произошло зацикливание!");
            speaker.error();
            return speaker;
        }
        try {
            
            path = (new File(next)).getAbsolutePath();
            if (path.length() > 4) {
                if (path.substring(0, 4).equals("/dev")) { 
                    throw new NullPointerException();
                }
            }
            stack.add(path);
            
            File purpose = new File(path);
            if (!purpose.exists()) {
                throw new FileNotFoundException("");
            }
            if (purpose.isDirectory()) {
                throw new FileNotFoundException();
            }
            FileInputStream inputContainer = new FileInputStream(purpose);
            
            CommandParser cp = new CommandParser();
            cp.listen(new BufferedInputStream(inputContainer), collection);
            stack.removeLast();
            
            speaker = new Speaker("Удалось прочитать скрипт " + path);
            speaker.success();
            return speaker;
            
        } catch (ArrayIndexOutOfBoundsException | FileNotFoundException e) {
            speaker = new Speaker("Не удалось прочитать скрипт " + path);
            speaker.error();
            return speaker;
        }

    }
}
