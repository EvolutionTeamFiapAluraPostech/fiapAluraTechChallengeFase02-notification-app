package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.parking.model.service.ParkingService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetParkingTypeFlexThatCompletedOneHourUseCase {

  private final ParkingService parkingService;

  public GetParkingTypeFlexThatCompletedOneHourUseCase(ParkingService parkingService) {
    this.parkingService = parkingService;
  }

  public List<Parking> execute(String parkingType, LocalDateTime initialDateTime) {
    return parkingService.findByParkingStateAndParkingTypeAndInitialParkingGreaterThanEqual(
        ParkingState.BUSY, ParkingType.valueOf(parkingType), initialDateTime.minusHours(1));
  }
}
