package br.com.digitalparking.notification.presentation.api;

import br.com.digitalparking.parking.application.usecase.CreateParkingPaymentNotification;
import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationApi {

  private final CreateParkingPaymentNotification createParkingPaymentNotification;

  public NotificationApi(CreateParkingPaymentNotification createParkingPaymentNotification) {
    this.createParkingPaymentNotification = createParkingPaymentNotification;
  }

  @PutMapping("/{uuid}/payment")
  public void putParkingPaymentNotification(@PathVariable String uuid,
      @RequestBody ParkingInputDto parkingInputDto) {
    var parking = ParkingInputDto.to(parkingInputDto);
    createParkingPaymentNotification.execute(uuid, parking);
  }
}
