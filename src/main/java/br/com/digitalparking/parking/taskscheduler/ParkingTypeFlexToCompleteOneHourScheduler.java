package br.com.digitalparking.parking.taskscheduler;

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
    var listOfParkingToFinish = getParkingTypeFlexThatCompletedOneHourUseCase.execute(
        ParkingType.FLEX.name(), LocalDateTime.now());
    if (!listOfParkingToFinish.isEmpty()) {
      parkingTypeFlexToCompleteOneHourEventPublisher.publishEvent(
          new ParkingTypeFlexToCompleteOneHourEvent(listOfParkingToFinish));
    }
  }
}
