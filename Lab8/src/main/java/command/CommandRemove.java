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
            collection.stream().filter(worker -> worker.getId() == id).forEach(worker -> compared = worker);
            if (compared != null) {
                compared.setOwner(username);
                collection.remove(compared);
                speaker = new Speaker("Элемент удачно удален.");
                speaker.success();
            } else {
                speaker = new Speaker("Элемент не найден.");
                speaker.error();
            }
            return speaker;
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        }
    }
}
