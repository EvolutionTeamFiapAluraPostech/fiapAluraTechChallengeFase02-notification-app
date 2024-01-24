package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFlexToCompleteOneHourListener {

  @Async
  @EventListener
  public void notifyParkingTypeFlexToCompleteOneHour(
      ParkingTypeFlexToCompleteOneHourEvent parkingTypeFlexToCompleteOneHourEvent) {
    var parkingList = parkingTypeFlexToCompleteOneHourEvent.parkingList();
  }
}
