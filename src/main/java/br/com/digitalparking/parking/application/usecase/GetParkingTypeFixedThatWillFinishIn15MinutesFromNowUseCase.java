package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.parking.model.service.ParkingService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase {

  private final ParkingService parkingService;

  public GetParkingTypeFixedThatWillFinishIn15MinutesFromNowUseCase(ParkingService parkingService) {
    this.parkingService = parkingService;
  }

  public List<Parking> execute(String parkingType, LocalDateTime actualDateTime) {
    var initialDateTime = actualDateTime.plusMinutes(15);
    var finalDateTime = initialDateTime.plusMinutes(1);
    return parkingService.findByParkingTypeAndFinalParkingBetween(ParkingType.valueOf(parkingType),
        initialDateTime, finalDateTime);
  }
}
