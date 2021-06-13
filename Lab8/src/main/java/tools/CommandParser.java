package tools;

import command.*;
import element.Worker;
import server.DataManager;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class CommandParser {
    
    public void listen(InputStream stream, DataManager collection)
            throws NoSuchElementException, ExecutionException{
        String inputString;
        Command command;
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()) {
            inputString = scanner.nextLine();
            command = choice(inputString);
            if (command != null) {
                command.event(collection);
            }
        }
        
    }
    
    public Command choice(String command){
        String[] commandArr = command.trim().split(" ");
        try {
            command = commandArr[0];
        }
        catch(NullPointerException e) {
            return null;
        }
        Invoker invoker = new Invoker();
        if (invoker.contains(command)) {
            return invoker.invoke(commandArr);
        } else {
            return null;
        }
    }
}
