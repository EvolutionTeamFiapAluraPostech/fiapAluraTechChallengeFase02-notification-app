package br.com.digitalparking.shared.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
  public static String localDateTimeToDateWithSlash(LocalDateTime localDateTime) {
    if (localDateTime == null) return "";
    return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(localDateTime);
  }
}
