package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.user.presentation.dto.UserInputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleInputDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDateTime;

public record ParkingInputDto(
    String id,
    @JsonAlias("vehicleOutputDto")
    VehicleInputDto vehicleInputDto,
    @JsonAlias("userOutputDto")
    UserInputDto userInputDto,
    String latitude,
    String longitude,
    String street,
    String neighborhood,
    String city,
    String state,
    String country,
    String parkingState,
    String parkingType,
    String parkingTime,
    LocalDateTime initialParking,
    LocalDateTime finalParking,
    @JsonAlias("parkingPaymentOutputDto")
    ParkingPaymentInputDto parkingPaymentInputDto) {

  public ParkingInputDto(Parking parking) {
    this(parking.getId().toString(),
        parking.getVehicle() != null ? VehicleInputDto.from(parking.getVehicle()) : null,
        parking.getUser() != null ? UserInputDto.from(parking.getUser()) : null,
        parking.getLatitude().toString(), parking.getLongitude().toString(),
        parking.getStreet(), parking.getNeighborhood(), parking.getCity(),
        parking.getState(), parking.getCountry(),
        parking.getParkingState() != null ? parking.getParkingState().name() : null,
        parking.getParkingType() != null ? parking.getParkingType().name() : null,
        parking.getParkingTime() > 0 ?
            ParkingTime.valueOfHour(parking.getParkingTime())
                .getDescription() : "",
        parking.getInitialParking(), parking.getFinalParking(),
        parking.getParkingPayment() != null ? ParkingPaymentInputDto.from(
            parking.getParkingPayment()) : null);
  }

  public static ParkingInputDto from(Parking parking) {
    return new ParkingInputDto(parking);
  }
}
