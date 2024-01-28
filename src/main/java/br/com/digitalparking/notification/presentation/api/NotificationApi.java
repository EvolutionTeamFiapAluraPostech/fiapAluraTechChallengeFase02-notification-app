package br.com.digitalparking.notification.presentation.api;

import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "NotificationApi", description = "API de notificação de estacionamento")
public interface NotificationApi {

  @Operation(summary = "Notificar o pagamento de estacionamento feito pelo usuário",
      description = "Este endpoint é consumido pela aplicação Digital Parking para notificar o usuário (por email) que o pagamento pelo estacionamento foi feito",
      tags = {"notification"})
  void putParkingPaymentNotification(@Parameter(description = "Parking id") String uuid,
      @Parameter(description = "Parking input DTO") ParkingInputDto parkingInputDto);

  @Operation(summary = "Notificar o encerramento do estacionamento",
      description = "Este endpoint é consumido pela aplicação Digital Parking para notificar o usuário (por email) que o estacionamento foi encerrado",
      tags = {"notification"})
  void putParkingCloseNotification(@Parameter(description = "Parking id") String uuid,
      @Parameter(description = "Parking input DTO") ParkingInputDto parkingInputDto);
}
