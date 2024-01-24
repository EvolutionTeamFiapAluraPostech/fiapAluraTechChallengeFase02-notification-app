package br.com.digitalparking.parking.infrastructure.repository;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {

  List<Parking> findByParkingTypeAndFinalParkingBetween(ParkingType parkingType,
      LocalDateTime initialDateTime, LocalDateTime finalDateTime);
}
