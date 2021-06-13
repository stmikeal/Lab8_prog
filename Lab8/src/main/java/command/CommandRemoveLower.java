package command;

import element.Worker;

import java.sql.SQLException;
import java.util.TreeSet;

import server.DataManager;
import tools.Speaker;

/**
 * Класс-команда remove_lower
 *
 * @author mike
 */
public class CommandRemoveLower extends Command{

    /**
     * Удаляет элемент.Удаляет наименьший элемент коллекции.
     *
     * @param args
     */
    
    public CommandRemoveLower(String ... args) {
        ready = true;
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            collection.remove(collection.first());
            speaker = new Speaker("Наименьший элемент удачно удалён.");
            speaker.success();
            return speaker;
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        } catch(Exception e) {
            speaker = new Speaker("Коллекция пуста.");
            speaker.success();
            return speaker;
        }
    }
}
