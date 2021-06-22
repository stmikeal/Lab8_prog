package tools;

import element.Worker;

import java.util.Scanner;
import java.io.InputStream;
import java.io.Serializable;
import java.util.TreeSet;

/**
 * Класс "разговорник".
 * Помогает красиво выводить разную информацию.
 * @author mike
 */
public class Speaker implements Serializable {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String hr = "----------------------------------------------------";
    private StringBuilder message;
    private String privateMessage1 = "";
    private String privateMessage2 = "";
    private TreeSet<Worker> collection;

    public TreeSet<Worker> getCollection() {
        return collection;
    }

    public void setCollection(TreeSet<Worker> c) {
        this.collection = c;
    }

    public String getMessage() {
        return this.message.toString();
    }
    /**
     * Вывод строк.
     * Выводит каждый аргумент с новой строки.
     */
    public static void println(String ... args){
        for(String s:args){
            System.out.println(s);
        }
    }
    
    public void println(){
        hr();
        System.out.print(this.message);
        hr();
    }
    
    public void error(){

    }
    
    public void success() {

    }
    
    public Speaker(String ... args){
        this.message = new StringBuilder();
        for(String arg:args){
            message.append(arg).append("\n");
        }
    }
   
    /**
     * Вывод строк.
     * Выводит все аргументы с новой строки, с заданным цветом.
     */
    public static void println(FontColor color, String ... args){
        System.out.print(color.toString());
        println(args);
        System.out.print(ANSI_RESET);
    }

    public static String scanStream(InputStream stream){
        Scanner reader = new Scanner(stream);
        return reader.nextLine();
    }
    
    /**
     * Полосочка.
     * Выводит длинную полоску для разделения информации.
     */
    public static void hr() {
        System.out.println(hr);
    }
    
    /**
     * Перечисление доступных цветов для текста.
     */
    public enum FontColor implements Serializable{
        BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE;
        
        @Override
        public String toString(){
            switch(this){
                case BLACK: return ANSI_BLACK;
                case RED: return ANSI_RED;
                case GREEN: return ANSI_GREEN;
                case YELLOW: return ANSI_YELLOW;
                case BLUE: return ANSI_BLUE;
                case PURPLE: return ANSI_PURPLE;
                case CYAN: return ANSI_CYAN;
                case WHITE: return ANSI_WHITE;
                default: return ANSI_RESET;
            }
        }
    }

    public void setPrivateMessage1(String privateMessage1) {
        this.privateMessage1 = privateMessage1;
    }
    public void setPrivateMessage2(String privateMessage2) {
        this.privateMessage2 = privateMessage2;
    }
    public String getPrivateMessage1() {
        return privateMessage1;
    }
    public String getPrivateMessage2() {
        return privateMessage2;
    }
}
