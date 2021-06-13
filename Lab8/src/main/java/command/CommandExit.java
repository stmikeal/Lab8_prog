package command;

import element.Worker;
import java.util.TreeSet;

import server.DataManager;
import tools.Speaker;

/**
 * Класс-команда exit.
 *
 * @author mike
 */
public class CommandExit extends Command{

    /**
     * Выходит из программы.
     *
     * @param
     * @return 
     */
    public CommandExit(String ... args){
        this.ready = true;
    }
    
    @Override
    public Speaker event(DataManager collection) {
        return new Speaker("exit");
    }
}
