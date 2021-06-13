package tools;

import command.*;
import java.util.HashMap;

/**
 * Класс отвечающий за распознавание команды.
 * @author mike
 */
public class Invoker {
    
    private final HashMap<String, CommandRunnable> commandMap = new HashMap<>();
    
    String[] args;
    
    public Invoker() {
        commandMap.put("help", (o1) -> new CommandHelp(o1));
        commandMap.put("info", (o1) -> new CommandInfo(o1));
        commandMap.put("show", (o1) -> new CommandShow(o1));
        commandMap.put("add", (o1) -> new CommandAdd(o1));
        commandMap.put("update", (o1) -> new CommandUpdate(o1));
        commandMap.put("remove_by_id", (o1) -> new CommandRemove(o1));
        commandMap.put("clear", (o1) -> new CommandClear(o1));
        commandMap.put("execute_script", (o1) -> new CommandExecute(o1));
        commandMap.put("exit", (o1) -> new CommandExit(o1));
        commandMap.put("add_if_min", (o1) -> new CommandAddIfMin(o1));
        commandMap.put("remove_lower", (o1) -> new CommandRemoveLower(o1));
        commandMap.put("filter_less_than_status", (o1) -> new CommandFilterStatus(o1));
        commandMap.put("filter_contains_name", (o1) -> new CommandFilterContains(o1));
        commandMap.put("print_ascending", (o1) -> new CommandPrint(o1));
        commandMap.put("login", (o1) -> new CommandLogin(o1));
        commandMap.put("register", (o1) -> new CommandRegister(o1));
    }
    
    public Command invoke(String ... args) {
        return commandMap.get(args[0]).run(args);
    }
    
    public boolean contains(String command) {
        return commandMap.containsKey(command);
    }
    
    @FunctionalInterface
    interface CommandRunnable{
        public Command run(String[] args);
    }
    
}
