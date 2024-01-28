package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingTypeFixedToFinishEvent;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import br.com.digitalparking.shared.util.DateUtil;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFixedToFinishMailListener {

  private static final String EMAIL_SUBJECT = "Attention! 15 minutes to complete your car parking period";
  private final ParkingNotificationMessage parkingNotificationMessage;
  private final String notificationEmailAddress;

  public ParkingTypeFixedToFinishMailListener(
      ParkingNotificationMessage parkingNotificationMessage,
      @Value("${spring.mail.username}") String notificationEmailAddress) {
    this.parkingNotificationMessage = parkingNotificationMessage;
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
      parkingNotificationMessage.send(notificationEmailAddress, user.getEmail(), EMAIL_SUBJECT,
          content);
    }
  }

  private String createEmailContentFrom(Parking parking) {
    var user = parking.getUser();
    var vehicle = parking.getVehicle();
    var amountPerHour = new DecimalFormat("R$ #,##0.00").format(ParkingTime.ONE.getValuePerHour());
    var paymentStatus = parking.getParkingPaymentStateDescription();
    return """
        %s. Attention! 15 minutes to complete your car parking period. Car %s, license plate %s.
        Start parking %s. End parking %s.
        Amount per hour %s. Payment status: %s.
        Digital Parking - Transaction ID: %s
        """.formatted(user.getName(), vehicle.getDescription(), vehicle.getLicensePlate(),
        DateUtil.localDateTimeToDateWithSlash(parking.getInitialParking()),
        DateUtil.localDateTimeToDateWithSlash(parking.getFinalParking()), amountPerHour,
        paymentStatus, parking.getId());
  }
}
