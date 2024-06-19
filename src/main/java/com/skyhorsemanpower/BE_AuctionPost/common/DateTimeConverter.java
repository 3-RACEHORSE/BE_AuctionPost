package com.skyhorsemanpower.BE_AuctionPost.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeConverter {

    public static LocalDateTime instantToLocalDateTime(long longOfInstant) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longOfInstant), ZoneId.systemDefault());
    }

    public static long localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
