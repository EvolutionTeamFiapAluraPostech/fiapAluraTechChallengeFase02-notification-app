package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEvent;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFixedToFinishMailListener {

  private static final String EMAIL_SUBJECT = "Attention! 15 minutes to complete your car parking period";
  private final ParkingNotificationMail parkingNotificationMail;
  private final String notificationEmailAddress;

  public ParkingTypeFixedToFinishMailListener(ParkingNotificationMail parkingNotificationMail,
      @Value("${spring.mail.username}") String notificationEmailAddress) {
    this.parkingNotificationMail = parkingNotificationMail;
    this.notificationEmailAddress = notificationEmailAddress;
  }

  @Async
  @EventListener
  public void notifyParkingTypeFixedToFinish(
      ParkingTypeFixedToFinishEvent parkingTypeFixedToFinishEvent) {
    var parkingList = parkingTypeFixedToFinishEvent.parkingList();
    for (Parking parking : parkingList) {
      var user = parking.getUser();
      var content = createEmailContentFrom(parking);
      parkingNotificationMail.send(notificationEmailAddress, user.getEmail(), EMAIL_SUBJECT,
          content);
    }
  }

  private String createEmailContentFrom(Parking parking) {
    var user = parking.getUser();
    var vehicle = parking.getVehicle();
    return """
        %s. Attention! 15 minutes to complete your car parking period. Car %s, license plate %s.
        Start parking %s. End parking %s
        """.formatted(user.getName(), vehicle.getDescription(), vehicle.getLicensePlate(),
        parking.getInitialParking(), parking.getFinalParking());
  }
}
