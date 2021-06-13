package command;

import element.Worker;

import java.sql.SQLException;
import java.util.TreeSet;

import server.DataManager;
import tools.Speaker;

/**
 * Класс-команда clear.
 *
 * @author mike
 */
public class CommandClear extends Command {

    /**
     * Очищает коллекцию.
     */
    public CommandClear(String ... args){
        this.ready = true;
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            collection.clear(username);
            speaker = new Speaker("Мы очистили коллекцию!");
            speaker.success();
            return speaker;
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        } catch (Exception e) {
            speaker = new Speaker("Мы не смогли очистить коллекцию!");
            speaker.error();
            return speaker;
        }
    }
}
