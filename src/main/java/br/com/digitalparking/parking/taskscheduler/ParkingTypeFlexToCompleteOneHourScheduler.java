package br.com.digitalparking.parking.taskscheduler;

import br.com.digitalparking.ParkingNotificationApplication;
import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEvent;
import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEventPublisher;
import br.com.digitalparking.parking.application.usecase.GetParkingTypeFlexThatCompletedOneHourUseCase;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFlexToCompleteOneHourScheduler implements Runnable {

  private final GetParkingTypeFlexThatCompletedOneHourUseCase getParkingTypeFlexThatCompletedOneHourUseCase;
  private final ParkingTypeFlexToCompleteOneHourEventPublisher parkingTypeFlexToCompleteOneHourEventPublisher;

  public ParkingTypeFlexToCompleteOneHourScheduler(
      GetParkingTypeFlexThatCompletedOneHourUseCase getParkingTypeFlexThatCompletedOneHourUseCase,
      ParkingTypeFlexToCompleteOneHourEventPublisher parkingTypeFlexToCompleteOneHourEventPublisher) {
    this.getParkingTypeFlexThatCompletedOneHourUseCase = getParkingTypeFlexThatCompletedOneHourUseCase;
    this.parkingTypeFlexToCompleteOneHourEventPublisher = parkingTypeFlexToCompleteOneHourEventPublisher;
  }

  @Scheduled(cron = "0 */1 * * * *")
  @Override
  public void run() {
    var actualLocalDateTime = LocalDateTime.now();
    for (int hourCompleted = 1; hourCompleted < 13; hourCompleted++) {
      var listOfParkingToFinish = getParkingTypeFlexThatCompletedOneHourUseCase.execute(
          ParkingType.FLEX.name(), actualLocalDateTime);

      if (!listOfParkingToFinish.isEmpty()) {
        parkingTypeFlexToCompleteOneHourEventPublisher.publishEvent(
            new ParkingTypeFlexToCompleteOneHourEvent(listOfParkingToFinish));
      }
      actualLocalDateTime = actualLocalDateTime.plusHours(1);
    }
    ParkingNotificationApplication.logger.info("Parking flex one completed hour thread executed");
  }
}
