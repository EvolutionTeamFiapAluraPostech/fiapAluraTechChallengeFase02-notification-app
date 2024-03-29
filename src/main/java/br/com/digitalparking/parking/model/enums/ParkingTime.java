package br.com.digitalparking.parking.model.enums;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum ParkingTime {

  ONE("1-Hour", 1, 5),
  TWO("2-Hours", 2, 10),
  UNLIMITED("Unlimited", 0, 0);

  private final String description;
  private final Integer hour;
  private final Integer valuePerHour;
  private static final Map<String, ParkingTime> BY_DESCRIPTION = new HashMap<>();
  private static final Map<Integer, ParkingTime> BY_HOUR = new HashMap<>();

  ParkingTime(String parkingTime, Integer hour, Integer valuePerHour) {
    this.description = parkingTime;
    this.hour = hour;
    this.valuePerHour = valuePerHour;
  }

  static {
    for (ParkingTime parkingTime : values()) {
      BY_DESCRIPTION.put(parkingTime.description, parkingTime);
      BY_HOUR.put(parkingTime.hour, parkingTime);
    }
  }

  public static ParkingTime valueOfDescription(String description) {
    return BY_DESCRIPTION.get(description);
  }

  public static ParkingTime valueOfHour(Integer hour) {
    return BY_HOUR.get(hour);
  }
}
