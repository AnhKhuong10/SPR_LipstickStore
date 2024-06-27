package org.example.mystoreh.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateTimeUtil {
    public static Date getCurrentDateTimeFormatted() {
        // Lấy ngày giờ hiện tại dưới dạng LocalDateTime
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Định dạng ngày giờ hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Chuyển đổi chuỗi định dạng thành LocalDateTime
        TemporalAccessor temporalAccessor = formatter.parse(formattedDateTime);
        LocalDateTime parsedDateTime = LocalDateTime.from(temporalAccessor);

        // Chuyển đổi LocalDateTime thành Date
        Date date = Date.from(parsedDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return date;
    }
}
