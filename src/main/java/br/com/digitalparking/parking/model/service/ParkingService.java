package br.com.digitalparking.parking.model.service;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

  private final ParkingRepository parkingRepository;

  public ParkingService(ParkingRepository parkingRepository) {
    this.parkingRepository = parkingRepository;
  }

  public List<Parking> findByParkingStateAndParkingTypeAndFinalParkingBetween(
      ParkingState parkingState, ParkingType parkingType, LocalDateTime initialParkingDateTime,
      LocalDateTime finishParkingDateTime) {
    return parkingRepository.findByParkingStateAndParkingTypeAndFinalParkingBetween(parkingState,
        parkingType, initialParkingDateTime, finishParkingDateTime);
  }
}
