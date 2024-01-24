package br.com.digitalparking.parking.taskscheduler;

import br.com.digitalparking.parking.application.usecase.GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFixedToFinishScheduler implements Runnable {

  private final GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;

  public ParkingTypeFixedToFinishScheduler(
      GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase) {
    this.getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase = getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase;
  }

  @Scheduled(cron = "0 */1 * * * *")
  @Override
  public void run() {
    var listOfParkingToFinish = getParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase.execute(
        ParkingType.FLEX.name(), LocalDateTime.now());
    for (Parking parking : listOfParkingToFinish) {
      System.out.println(parking.getId() + " - " + parking.getInitialParking() + " - "
          + parking.getFinalParking());
    }
  }
}
