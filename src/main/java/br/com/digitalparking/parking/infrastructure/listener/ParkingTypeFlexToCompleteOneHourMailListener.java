package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEvent;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import br.com.digitalparking.shared.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFlexToCompleteOneHourMailListener {

  private static final String EMAIL_SUBJECT = "Attention! One completed hour of your car parking period.";
  private final ParkingNotificationMessage parkingNotificationMessage;
  private final String notificationEmailAddress;

  public ParkingTypeFlexToCompleteOneHourMailListener(
      ParkingNotificationMessage parkingNotificationMessage,
      @Value("${spring.mail.username}") String notificationEmailAddress) {
    this.parkingNotificationMessage = parkingNotificationMessage;
    this.notificationEmailAddress = notificationEmailAddress;
  }

  @Async
  @EventListener
  public void notifyParkingTypeFlexToCompleteOneHour(
      ParkingTypeFlexToCompleteOneHourEvent parkingTypeFlexToCompleteOneHourEvent) {
    var parkingList = parkingTypeFlexToCompleteOneHourEvent.parkingList();
    for (Parking parking : parkingList) {
      var user = parking.getUser();
      var content = createEmailContentFrom(parking);
      parkingNotificationMessage.send(notificationEmailAddress, user.getEmail(), EMAIL_SUBJECT,
          content);
    }
  }

  private String createEmailContentFrom(Parking parking) {
    var user = parking.getUser();
    var vehicle = parking.getVehicle();
    var paymentStatus = parking.getParkingPaymentStateDescription(parking);
    return """
        %s. Attention! One completed hour of your parking period, which will be extended for
         another hour unless the parking lot is vacated. Car %s, license plate %s. Start parking %s.
         Payment status %s
        """.formatted(user.getName(), vehicle.getDescription(), vehicle.getLicensePlate(),
        DateUtil.localDateTimeToDateWithSlash(parking.getInitialParking()), paymentStatus);
  }
}