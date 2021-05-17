package ir.ac.ut.ie.Bolbolestan06.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String quoteWrapper(String str) {
        return "'" + str + "'";
    }
    public static String convertToString(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }

    public static LocalDateTime convertToLocalDateTime(String time) {
        if (time == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return dateTime;
    }

    public static String searchKeywordWrapper(String str) { return "'%" + str + "%'"; }
}