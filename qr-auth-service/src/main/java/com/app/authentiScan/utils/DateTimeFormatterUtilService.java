package com.app.authentiScan.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeFormatterUtilService {

    public static String localDateTimeToDateTimeString(LocalDateTime localDateTime) {
       if (Objects.nonNull(localDateTime)) {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
           return localDateTime.format(formatter);
       }
       return null;
    }

    public static String localDateTimeToDateString(LocalDateTime localDateTime) {
        if (Objects.nonNull(localDateTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return localDateTime.format(formatter);
        }
        return null;
    }

}
