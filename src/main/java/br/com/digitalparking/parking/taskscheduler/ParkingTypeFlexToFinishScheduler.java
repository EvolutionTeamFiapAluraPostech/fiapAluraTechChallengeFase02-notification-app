package br.com.digitalparking.parking.taskscheduler;

import br.com.digitalparking.parking.application.usecase.GetParkingTypeFlexThatCompletedOneHourUseCase;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFlexToFinishScheduler implements Runnable {

  private final GetParkingTypeFlexThatCompletedOneHourUseCase getParkingTypeFlexThatCompletedOneHourUseCase;

  public ParkingTypeFlexToFinishScheduler(
      GetParkingTypeFlexThatCompletedOneHourUseCase getParkingTypeFlexThatCompletedOneHourUseCase) {
    this.getParkingTypeFlexThatCompletedOneHourUseCase = getParkingTypeFlexThatCompletedOneHourUseCase;
  }

  @Scheduled(cron = "0 */1 * * * *")
  @Override
  public void run() {
    var listOfParkingToFinish = getParkingTypeFlexThatCompletedOneHourUseCase.execute(
        ParkingType.FLEX.name(), LocalDateTime.now());
    for (Parking parking : listOfParkingToFinish) {
      System.out.println(parking.getId() + " - " + parking.getInitialParking() + " - "
          + parking.getFinalParking());
    }
  }
}
