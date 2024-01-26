package br.com.digitalparking.parking.model.service;

import br.com.digitalparking.parking.infrastructure.repository.ParkingRepository;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingState;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.shared.exception.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

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

  public List<Parking> findByParkingStateAndParkingTypeAndInitialParkingGreaterThanEqual(
      ParkingState parkingState, ParkingType parkingType, LocalDateTime initialParkingDateTime) {
    return parkingRepository.findByParkingStateAndParkingTypeAndInitialParkingGreaterThanEqual(
        parkingState, parkingType, initialParkingDateTime);
  }

  public Parking findByIdRequired(UUID uuid) {
    return parkingRepository.findById(uuid).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "uuid",
            "Parking is not found with ID %s.".formatted(uuid))));
  }
}
