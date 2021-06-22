package command;

import element.Worker;
import java.io.InputStream;

import server.DataManager;
import tools.ReadWorker;
import tools.Speaker;
import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

/**
 * Класс-команда add_if_min.
 *
 * @author mike
 */
public class CommandAddIfMin extends Command{

    /**
     * Интерактивный метод добавления. Добавляет полученного из потока
     * работника, если его id меньше всех в коллекции.
     *
     * @param console
     * @param args
     * @param stream
     */
    
    private Worker worker;

    public CommandAddIfMin(Worker worker, Integer id) {
        this.worker = worker;
        this.worker.setId(id);
        this.ready = true;
    }

    public CommandAddIfMin(String ... args){
        try {
            this.worker = ReadWorker.read(System.in);
            this.ready = true;
        } catch(IOException e) {
            Speaker.println(Speaker.FontColor.RED, "Не удалось добавить работника. "
                    + "IOException thrown from <Command Add If Min>.");
        }
    }
    
    @Override
    public Speaker event(DataManager collection) {
        try {
            Integer first;
            if (collection.isEmpty()) {
                first = -2147483648;
            } else {
                first = collection.first(username).getId();
            }
            if (worker.getId() < first) {
                worker.setOwner(username);
                collection.add(worker, true);
                speaker = new Speaker("Мы добавили элемент в коллекцию!");
                speaker.success();
                return speaker;
            }
            speaker = new Speaker("Мы не добавили элемент в коллекцию.");
            return speaker;
        } catch (SQLException e) {
            speaker = new Speaker("База данных сейчас недоступна.");
            speaker.error();
            return speaker;
        } catch (Exception e) {
            speaker = new Speaker("Мы не смогли добавить элемент в коллекцию.");
            speaker.error();
            return speaker;
        }
    }
}
