package command;

import server.DataManager;
import tools.Speaker;
import client.Client;
import element.Worker;

import java.sql.SQLException;
import java.util.TreeSet;

/**
 * Класс-команда remove_by_id
 *
 * @author mike
 */
public class CommandRemove extends Command{

    /**
     * Удаление элемента. Удаляет элемент по его id.
     *
     * @param console
     * @param args
     */
    
    private int id;
    private Worker compared = null; 

    public CommandRemove(Integer id) {
        ready = true;
        try {
            this.id = id;
        } catch(NumberFormatException e) {
            ready = false;
        }
    }

    public CommandRemove(String ... args) {
        ready = true;
        try {
            id = Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            ready = false;
        }
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            collection.remove(id, username);
            speaker = new Speaker("Элемент удачно удален.");
            return speaker;
        } catch (SQLException e) {
            speaker = new Speaker("Элемент не был удален");
            speaker.error();
            return speaker;
        }
    }
}
