package command;

import server.DataManager;
import tools.Speaker;
import element.Worker;

import java.sql.SQLException;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Класс-команда filter_contains_name
 *
 * @author mike
 */
public class CommandFilterContains extends Command{

    /**
     * Фильтр имени. Выводит все элементы, имя которых содержит подстроку.
     *
     * @param console
     * @param args
     */
    
    private String name;
    private String result = "---\n";
    
    public CommandFilterContains(String ... args) {
        ready = true;
        name = args[1];
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            collection.stream().filter(worker -> Pattern.matches(".*" + name + ".*", worker.getName())).forEach(worker -> result += worker.toString() + "\n---\n");
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
