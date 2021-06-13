package command;

import element.Worker;

import java.sql.SQLException;
import java.util.TreeSet;

import server.DataManager;
import tools.Speaker;

/**
 * Класс-команда info. Выводит информацию о коллекции.
 *
 * @author mike
 */
public class CommandInfo extends Command{
    
    public CommandInfo(String ... args) {
        ready = true;
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            return new Speaker("TreeSet<Worker> collection, " + server.Server.getDate().toString() + ", " +
                    Integer.toString(collection.size()) + " elements.");
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        }
    }
}
