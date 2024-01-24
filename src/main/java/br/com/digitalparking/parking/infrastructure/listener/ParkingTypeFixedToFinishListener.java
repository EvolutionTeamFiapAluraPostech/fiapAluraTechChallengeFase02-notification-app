package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFixedToFinishListener {

  @Async
  @EventListener
  public void notifyParkingTypeFixedToFinish(
      ParkingTypeFixedToFinishEvent parkingTypeFixedToFinishEvent) {
    var parkingList = parkingTypeFixedToFinishEvent.parkingList();
  }
}
