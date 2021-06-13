package tools;

import element.*;
import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDate;
/**
 * Класс интерактивного чтения.
 * Реализует интерактивное чтения элементов коллекции методом read(InputStream).
 * @author mike
 */
public class ReadWorker {
    public static Worker read(InputStream stream)throws IOException {
        boolean isConsole = false;
        String error = "Скрипт не выполнен, проверьте корректность данных.";
        
        String name = "";
        double x = 0.0;
        double y = 0.0;
        Double salary = 0.0;
        int year = 0;
        int month = 0;
        int day = 0;
        LocalDate startAt = LocalDate.now();
        Position position = null;
        Status status = null;
        int height = 0;
        Color eyeColor = null;
        Color hairColor = null;
        Country nationality = null;
        Person person = null;
        
        if (stream.equals(System.in)) isConsole=true;
        
        if (isConsole) Speaker.hr();
        
        while(true) {
            if (isConsole) Speaker.println("Введите имя:");
            name = Speaker.scanStream(stream);
            if (!isConsole&"".equals(name)) throw new IOException(error);
            if (name==null||name.equals("")) Speaker.println("Введите не пустую строку!");
            else break;
        }

        while(true) {
            if (isConsole) Speaker.println("Введите координату х большую -623:");
            try {
                x = Double.parseDouble(Speaker.scanStream(stream));
                if (x<=-623) throw new NumberFormatException("");
                break;
            } catch(NumberFormatException e) {
                if (!isConsole) throw new IOException(error);
                Speaker.println("Неправильный формат вещественного числа!");
            }     
        }
        
        while(true) {
            if (isConsole) Speaker.println("Введите координату y:");
            try {
                y = Double.parseDouble(Speaker.scanStream(stream));
                break;
            } catch(NumberFormatException e) {
                if (!isConsole) throw new IOException(error);
                Speaker.println("Неправильный формат вещественного числа!");
            }     
        }
        
        while(true){
            if (isConsole) Speaker.println("Введите зарплату:");
            try{
                salary = Double.parseDouble(Speaker.scanStream(stream));
                if (salary<0) throw new NumberFormatException("");
                break;
            } catch(NumberFormatException e) {
                if (!isConsole) throw new IOException(error);
                Speaker.println("Не бывает такой зарплаты!");
            }     
        }
        
        while(true) {
            while(true) {
                if (isConsole) Speaker.println("Введите год начала работы:");
                try {
                    year = Integer.parseInt(Speaker.scanStream(stream));
                    if (year<0) throw new NumberFormatException("");
                    break;
                } catch(NumberFormatException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Не верное значение года!");
                }     
            }
            while(true) {
                if (isConsole) Speaker.println("Введите месяц начала работы:");
                try {
                    month = Integer.parseInt(Speaker.scanStream(stream));
                    if (month<1|month>12) throw new NumberFormatException("");
                    break;
                } catch(NumberFormatException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Не верное значение месяца!");
                }     
            }
            while(true) {
                if (isConsole) Speaker.println("Введите день начала работы:");
                try {
                    day = Integer.parseInt(Speaker.scanStream(stream));
                    if (day<1|day>31) throw new NumberFormatException("");
                    break;
                } catch(NumberFormatException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Не верное значение дня!");
                }     
            }
            try{
                startAt = LocalDate.of(year, month, day);
                break;
            }catch(Exception e) {
                if (!isConsole) throw new IOException(error);
                Speaker.println("Не верно введенная дата!");
            }
        }
        
        while(true) {
            if (isConsole) Speaker.println("Список должностей(null - не указано):"
                    + "[DIRECTOR, ENGINEER, HEAD_OF_DIVISION, null]",
                    "Введите занимаемую должность:");
            try {
                String pos = Speaker.scanStream(stream);
                switch (pos) {
                    case "DIRECTOR": position = Position.DIRECTOR; break;
                    case "ENGINEER": position = Position.ENGINEER; break;
                    case "HEAD_OF_DIVISION": position = Position.HEAD_OF_DIVISION; break;
                    case "": position = null; break;
                    default: throw new IOException("");
                }
                break;
            } catch(IOException e) {
                if (!isConsole) throw new IOException(error);
                Speaker.println("Не бывает такой должности!");
            }    
        }
        
        while(true) {
            if (isConsole) Speaker.println("Список статусов(null - не указано):"
                    + "[FIRED, RECOMMENDED_FOR_PROMOTION, REGULAR, null]",
                    "Введите статус:");
            try {
                String stat = Speaker.scanStream(stream);
                switch (stat) {
                    case "FIRED": status = Status.FIRED; break;
                    case "RECOMMENDED_FOR_PROMOTION": 
                        status = Status.RECOMMENDED_FOR_PROMOTION; break;
                    case "REGULAR": status = Status.REGULAR; break;
                    case "": position = null; break;
                    default: throw new IOException("");
                }
                break;
            } catch(IOException e) {
                if (!isConsole) throw new IOException(error);
                Speaker.println("Не бывает такого статуса!");
            }    
        }
        
        boolean pers = true;
            while(pers) {
                if (isConsole) Speaker.println("Будем вводить личные данные?(y/n):");
                try {
                    String answer = Speaker.scanStream(stream).trim();
                    switch(answer) {
                        case"y": break;
                        case"n": pers=false;break;
                        default: throw new IOException("");
                    }
                    break;
                } catch(IOException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Введите корректный ответ!");
                }     
            }
            while(pers) {
                if (isConsole) Speaker.println("Введите рост:");
                try {
                    height = Integer.parseInt(Speaker.scanStream(stream));
                    if (height<0) throw new NumberFormatException("");
                    break;
                } catch(NumberFormatException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Введите корректный рост!");
                }     
            }
            while(pers) {
                if (isConsole) Speaker.println("Список цветов глаз (null - неизвестно)"
                        + "[GREEN, BLUE, ORANGE, WHITE, null]",
                        "Введите цвет глаз:");
                try {
                    String answer = Speaker.scanStream(stream);
                    switch(answer) {
                        case"GREEN": eyeColor = Color.GREEN; break;
                        case"BLUE": eyeColor = Color.BLUE; break;
                        case"ORANGE": eyeColor = Color.ORANGE; break;
                        case"WHITE": eyeColor = Color.WHITE; break;
                        case"": eyeColor = null; break;
                        default: throw new IOException("");
                    }
                    break;
                } catch(IOException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Введите цвет!");
                }     
            }
            while(pers) {
                if (isConsole) Speaker.println("Список цветов волос (null - неизвестно)"
                        + "[YELLOW, BROWN, WHITE, null]",
                        "Введите цвет волос:");
                try {
                    String answer = Speaker.scanStream(stream);
                    switch(answer) {
                        case"YELLOW": hairColor = Color.YELLOW; break;
                        case"BROWN": hairColor = Color.BROWN; break;
                        case"": hairColor = null; break;
                        case"WHITE": hairColor = Color.WHITE; break;
                        default: throw new IOException("");
                    }
                    break;
                } catch(IOException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Введите цвет!");
                }     
            }
            while(pers) {
                if (isConsole) Speaker.println("Список национальностей (null - неизвестно)"
                        + "[RUSSIA, UNITED_KINGDOM, GERMANY, ITALY, null]",
                        "Введите национальность:");
                try {
                    String answer = Speaker.scanStream(stream);
                    switch(answer) {
                        case"RUSSIA": nationality = Country.RUSSIA; break;
                        case"UNITED_KINGDOM": nationality = Country.UNITED_KINGDOM; break;
                        case"": nationality = null; break;
                        case"GERMANY": nationality = Country.GERMANY; break;
                        case"ITALY": nationality = Country.ITALY; break;
                        default: throw new IOException("");
                    }
                    break;
                } catch(IOException e) {
                    if (!isConsole) throw new IOException(error);
                    Speaker.println("Введите национальность!");
                }     
            }
            if (pers) person = new Person(height, eyeColor, hairColor, nationality);
        
        if (isConsole) Speaker.hr();
        return new Worker(name, new Coordinates(x,y), salary, startAt, position, status, person);
    }
}
