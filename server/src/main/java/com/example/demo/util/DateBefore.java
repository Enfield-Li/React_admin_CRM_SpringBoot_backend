package com.example.demo.util;

// import static com.example.demo.util.Before.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class DateBefore {

  public static Date daysBefore(Integer days) {
    return Date.from(Instant.now().minus(Duration.ofDays(days)));
  }

  public static String daysStringBefore(Integer days) {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    .format(DateBefore.daysBefore(days));
  }
}
