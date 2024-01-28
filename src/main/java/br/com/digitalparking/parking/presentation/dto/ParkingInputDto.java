package br.com.digitalparking.parking.presentation.dto;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.enums.ParkingType;
import br.com.digitalparking.user.presentation.dto.UserInputDto;
import br.com.digitalparking.vehicle.presentation.dto.VehicleInputDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.util.StringUtils;

@Tag(name = "ParkingInputDto", description = "DTO de entrada para registrar uma notificação de estacionamento")
public record ParkingInputDto(
    @Schema(example = "feea1d11-11b9-4e34-9848-e1174bb857e3", description = "Valid UUID")
    String id,
    @JsonAlias("vehicleOutputDto")
    VehicleInputDto vehicleInputDto,
    @JsonAlias("userOutputDto")
    UserInputDto userInputDto,
    @Schema(example = "-23.56404", description = "Latitude é a medida em graus de qualquer ponto da superfície da Terra até o Equador. A combinação de latitude e longitude determina sua posição no mapa glogal e será utilizada para validar o endereço de estacionamento do veículo")
    String latitude,
    @Schema(example = "-46.65219", description = "Longitude é a medida em graus de qualquer ponto da superfície da Terra até o Meridiano de Greenwich. A combinação de latitude e longitude determina sua posição no mapa glogal e será utilizada para validar o endereço de estacionamento do veículo")
    String longitude,
    @Schema(example = "Av Paulista", description = "A rua é o caminho utilizado por pessoas e diferentes veículos")
    String street,
    @Schema(example = "Bela Vista", description = "Bairro é uma comunidade ou região dentro de uma cidade ou município, sendo a unidade mínima de urbanização existente na maioria das cidades do mundo")
    String neighborhood,
    @Schema(example = "São Paulo", description = "O espaço urbano de um município delimitado por um perímetro urbano")
    String city,
    @Schema(example = "São Paulo", description = "O Estado é um conceito da Geografia que está vinculado ao ordenamento jurídico de um determinado território")
    String state,
    @Schema(example = "Brasil", description = "País é uma região geográfica considerada o território físico de um Estado Soberano, ou de uma menor ou antiga divisão política dentro de uma região geográfica")
    String country,
    @Schema(example = "OPEN, BUSY, CLOSED", description = "Uma vaga de estacionamento pode estar disponível, ocupada ou encerrada")
    String parkingState,
    @Schema(example = "FIXED, FLEX", description = "Tipo de estacionamento, com horário fixo ou flexível")
    String parkingType,
    @Schema(example = "1 ou 2", description = "Se o tipo de estacionamento escolhido for FIXED, será a quantidade de horas que o veículo permanecerá estacionado. Se o tipo de estacionamento escolhido for FLEX, a quantidade será indeterminada")
    String parkingTime,
    @Schema(example = "2024-01-28T10:15:52.522561979", description = "Hora inicial do estacionamento.", format = "LocalDateTime")
    LocalDateTime initialParking,
    @Schema(example = "2024-01-28T12:15:52.522561979", description = "Hora final do estacionamento", format = "LocalDateTime")
    LocalDateTime finalParking,
    @JsonAlias("parkingPaymentOutputDto")
    ParkingPaymentInputDto parkingPaymentInputDto) {

  @Operation(summary = "Conversor",
      description = "Converter um DTO de entrada para um objeto da classe de entidade",
      tags = {"ParkingInputDto"})
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
    var parkingPayment = ParkingPaymentInputDto.to(parkingInputDto.parkingPaymentInputDto);

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
        .parkingPayment(parkingPayment)
        .build();
  }
}
