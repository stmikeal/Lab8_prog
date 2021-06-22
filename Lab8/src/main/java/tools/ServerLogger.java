package tools;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerLogger {
   
    public static Logger logger = Logger.getLogger(ServerLogger.class.getName());
    static {
        try(InputStream ins = ServerLogger.class.getResourceAsStream("log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(ServerLogger.class.getName());
        }catch (Exception ignore){
            System.out.println("Конфигурация логгера не обнаружена."
                    + "\nЛоггирование будет выводиться в стандартный поток вывода.");
        } 
    }
}
