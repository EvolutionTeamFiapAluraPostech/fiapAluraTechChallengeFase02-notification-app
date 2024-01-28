package br.com.digitalparking.notification.presentation.api;

import br.com.digitalparking.parking.application.usecase.CreateParkingCloseNotification;
import br.com.digitalparking.parking.application.usecase.CreateParkingPaymentNotification;
import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationApiImpl implements NotificationApi {

  private final CreateParkingPaymentNotification createParkingPaymentNotification;
  private final CreateParkingCloseNotification createParkingCloseNotification;

  public NotificationApiImpl(CreateParkingPaymentNotification createParkingPaymentNotification,
      CreateParkingCloseNotification createParkingCloseNotification) {
    this.createParkingPaymentNotification = createParkingPaymentNotification;
    this.createParkingCloseNotification = createParkingCloseNotification;
  }

  @PutMapping("/{uuid}/payment")
  public void putParkingPaymentNotification(@PathVariable String uuid,
      @RequestBody ParkingInputDto parkingInputDto) {
    var parking = ParkingInputDto.to(parkingInputDto);
    createParkingPaymentNotification.execute(uuid, parking);
  }

  @PutMapping("/{uuid}/close")
  public void putParkingCloseNotification(@PathVariable String uuid,
      @RequestBody ParkingInputDto parkingInputDto) {
    var parking = ParkingInputDto.to(parkingInputDto);
    createParkingCloseNotification.execute(uuid, parking);
  }
}
