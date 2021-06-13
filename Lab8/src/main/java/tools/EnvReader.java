/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import exception.EnvException;

/**
 * Читатель переменной окружения. методом getenc получает переменную окружения с
 * именем arg.
 *
 * @author mike
 */
public class EnvReader {

    public static String getenv(String arg) throws EnvException {
        String path = System.getenv(arg);
        if (path == null) {
            throw new EnvException("Мы не можем найти переменную "
                    + "окружения " + arg + ".");
        }
        return path;
    }
}
