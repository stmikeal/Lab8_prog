package command;

import element.Worker;
import java.util.TreeSet;

import server.DataManager;
import tools.Speaker;

/**
 * Класс-команда help. Выводит help.
 *
 * @author mike
 */
public class CommandHelp extends Command{
    
    public CommandHelp(String ... args) {
        ready = true;
    }

    @Override
    public Speaker event(DataManager collection) {
        return new Speaker("help - справка по командам.",
                "info - вывести информацию о коллекции.",
                "show - вывести все элементы коллекции.",
                "add - добавляет элемент в коллекцию.",
                "update <id> - обновить элемент коллекции по id.",
                "remove_by_id <id> - удаляет элемент коллекции по id.",
                "clear - очищает коллекцию.",
                "execute_script <path> - выполняет скрипт в файле.",
                "exit - завершает программу(без сохранения)",
                "add_if_min - добавляет элемент, если его значение наименьшее.",
                "remove_lower - удаляет из коллекции элементы меньше заданного.",
                "filter_contains_name <name>- вывести элементы, имена которых"
                + "содержат подстроку.",
                "filter_less_than_status <status>- вывести элементы, статус которых "
                + "меньше аргумента.",
                "print_ascending - вывести элементы в порядке возрастания.",
                "login - вход в свой аккаунт.",
                "register - регистрация в системе.");
    }
}
