package br.com.digitalparking.parking.taskscheduler;

import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEvent;
import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEventPublisher;
import br.com.digitalparking.parking.application.usecase.GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFixedToFinishScheduler implements Runnable {

  private final GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;
  private final ParkingTypeFixedToFinishEventPublisher parkingTypeFixedToFinishEventPublisher;

  public ParkingTypeFixedToFinishScheduler(
      GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase,
      ParkingTypeFixedToFinishEventPublisher parkingTypeFixedToFinishEventPublisher) {
    this.getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase = getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;
    this.parkingTypeFixedToFinishEventPublisher = parkingTypeFixedToFinishEventPublisher;
  }

  @Scheduled(cron = "0 */1 * * * *")
  @Override
  public void run() {
    var listOfParkingToFinish = getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase.execute(
        ParkingType.FIXED.name(), LocalDateTime.now());
    if (!listOfParkingToFinish.isEmpty()) {
      parkingTypeFixedToFinishEventPublisher.publishEvent(
          new ParkingTypeFixedToFinishEvent(listOfParkingToFinish));
    }
  }
}
