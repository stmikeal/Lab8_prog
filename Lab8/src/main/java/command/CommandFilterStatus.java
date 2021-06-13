package command;

import element.Worker;
import server.DataManager;
import tools.Speaker;

import java.sql.SQLException;
import java.util.TreeSet;

/**
 * Класс-команда filter_status.
 *
 * @author mike
 */
public class CommandFilterStatus extends Command{

    /**
     * Фильтр по статусу. Выводит все элементы, у которых статус меньше
     * заданного.
     *
     * @param console
     * @param args
     */
    
    private int status;
    private String result = "---";
    
    public CommandFilterStatus(String ... args) {
        try {
            status = Integer.parseInt(args[1]);
            ready = true;
        } catch(NumberFormatException e) {
            ready = false;
        }
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            collection.stream().filter(worker -> worker.statusToInt() < status).forEach(worker -> result += worker.toString() + "\n---\n");
            result = result.trim();
            speaker = new Speaker(result);
            return speaker;
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        }
    }
}
