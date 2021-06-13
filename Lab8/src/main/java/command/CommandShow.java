package command;

import element.Worker;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.TreeSet;

import server.DataManager;
import tools.Speaker;

/**
 * Класс-команда show. Выводит коллекцию.
 *
 * @author mike
 */
public class CommandShow extends Command {
    String result = "---\n";
    
    public CommandShow(String ... args) {
        ready = true;
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            collection.stream().forEach(worker -> result += worker.toString() + "\n---\n");
            result = result.trim();
            return new Speaker(result);
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        }
    }
}
