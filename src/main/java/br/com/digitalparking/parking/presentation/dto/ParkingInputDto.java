package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.user.presentation.dto.UserInputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleInputDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.util.StringUtils;

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

  public static Parking to(ParkingInputDto parkingInputDto) {
    var vehicle = VehicleInputDto.to(parkingInputDto.vehicleInputDto);
    var user = UserInputDto.to(parkingInputDto.userInputDto);
    var latitude =
        parkingInputDto.latitude != null ? new BigDecimal(parkingInputDto.latitude) : null;
    var longitude =
        parkingInputDto.longitude != null ? new BigDecimal(parkingInputDto.longitude) : null;
    var hour = 0;
    if (StringUtils.hasLength(parkingInputDto.parkingTime)) {
      var parkingTime = ParkingTime.valueOfDescription(parkingInputDto.parkingTime);
      hour = parkingTime.getHour();
    }

    return Parking.builder()
        .id(UUID.fromString(parkingInputDto.id))
        .vehicle(vehicle)
        .user(user)
        .latitude(latitude)
        .longitude(longitude)
        .street(parkingInputDto.street)
        .neighborhood(parkingInputDto.neighborhood)
        .city(parkingInputDto.city)
        .state(parkingInputDto.state)
        .country(parkingInputDto.country)
        .parkingType(ParkingType.valueOf(parkingInputDto.parkingType))
        .parkingTime(hour)
        .build();
  }
}
