package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

/**
 * Класс для вспомагательных работ с файлом.
 *
 * @author mike
 */
public class FileReader {

    private static File purpose;

    /**
     * Получает поток ввода из файла. Пытается подключиться к файлу path и
     * получить из него поток ввода
     *
     * @param path
     * @return InputStream
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static BufferedInputStream getStream(String path) throws FileNotFoundException, IOException {

        purpose = new File(path);
        if (!purpose.exists()) {
            Speaker.println("Файла по указанному пути не сущетсвует.");
            throw new FileNotFoundException("");
        }

        if (purpose.isDirectory()) {
            Speaker.println(Speaker.FontColor.RED, "Указанный файл - директория");
            throw new FileNotFoundException("Не удалось получить доступ к файлу "
                    + path);
        }

        FileInputStream inputContainer = new FileInputStream(purpose);
        return new BufferedInputStream(inputContainer);

    }

    public String nextLine() {

        int i;
        try {

        } catch (Exception e) {
            System.out.println("oops");
            return "";
        }
        return "s";
    }
}
