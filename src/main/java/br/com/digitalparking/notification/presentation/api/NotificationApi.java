package br.com.digitalparking.notification.presentation.api;

import br.com.digitalparking.parking.presentation.dto.ParkingInputDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationApi {

  @PutMapping("/{uuid}")
  public void putParkingPaymentNotification(@PathVariable String uuid,
      @RequestBody ParkingInputDto parkingInputDto) {
    System.out.println("Notification test.");
    System.out.println(parkingInputDto.toString());
  }
}
