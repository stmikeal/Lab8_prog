package tools;

import java.time.LocalDate;

public class LocalDateAdapter {
    public static LocalDate encode(String v) {
        return LocalDate.of(Integer.parseInt(v.split(" ")[0].split("-")[0]),
                Integer.parseInt(v.split(" ")[0].split("-")[1]),
                Integer.parseInt(v.split(" ")[0].split("-")[2]));
    }
}
