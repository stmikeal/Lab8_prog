package element;

import java.io.Serializable;

/**
 * Класс личных данных. Хранит внешние данные работника.
 *
 * @author mike
 */
public class Person implements Serializable {

    private final Integer height; //Поле не может быть null, Значение поля должно быть больше 0
    private final Color eyeColor; //Поле не может быть null
    private final Color hairColor; //Поле не может быть null
    private final Country nationality; //Поле не может быть null

    public Person(Integer height, Color eyeColor, Color hairColor, Country nationality) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Integer getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String result = Integer.toString(height);
        if (eyeColor == null) {
            result += ", null";
        } else {
            result += ", " + eyeColor.toString();
        }
        if (hairColor == null) {
            result += ", null";
        } else {
            result += ", " + hairColor.toString();
        }
        if (nationality == null) {
            result += ", null";
        } else {
            result += ", " + nationality.toString();
        }
        return result;
    }

    public String toStringSave() {
        String result = Integer.toString(height) + "\n";
        if (eyeColor == null) {
            result += "null\n";
        } else {
            result += ", " + eyeColor.toString();
        }
        if (hairColor == null) {
            result += ", null\n";
        } else {
            result += ", " + hairColor.toString();
        }
        if (nationality == null) {
            result += ", null\n";
        } else {
            result += ", " + nationality.toString();
        }
        return result;
    }
}
