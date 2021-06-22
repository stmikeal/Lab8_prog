package tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ClientLogger {
    public static Logger logger = Logger.getLogger(ServerLogger.class.getName());;
    static {
        try(InputStream ins = ClientLogger.class.getResourceAsStream("log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            logger = java.util.logging.Logger.getLogger(ServerLogger.class.getName());
        }catch (Exception ignore){
            System.out.println("Конфигурация логгера не обнаружена."
                    + "\nЛоггирование будет выводиться в стандартный поток вывода.");
        }
    }
}
