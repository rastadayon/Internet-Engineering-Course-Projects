package ir.ac.ut.ie.Bolbolestan07.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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

    public static boolean hasIllegalChars(String str) {
        return !stripTags(str).equals(str);
    }

    public static String hashString(String str) throws Exception{
        return Integer.toString(str.hashCode());
    }

    public static String stripTags(String str) {
        String stripStr = str;
        List<String> illegalWords = Arrays.asList("\\<.*?\\>", "'", "\"", "_", "%", "=");
        for (String illegalWord: illegalWords) {
            stripStr = stripStr.replaceAll(illegalWord, "");
        }
        stripStr = StringEscapeUtils.escapeSql(stripStr);
        return stripStr;
    }
}
