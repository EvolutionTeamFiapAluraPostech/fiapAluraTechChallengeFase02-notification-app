package br.com.digitalparking.parking.infrastructure.listener;

import br.com.digitalparking.parking.application.event.ParkingTypeFlexToCompleteOneHourEvent;
import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import br.com.digitalparking.shared.util.DateUtil;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ParkingTypeFlexToCompleteOneHourMailListener {

  private static final String EMAIL_SUBJECT = "Attention! More than one completed hour of your car parking period.";
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
    var finalLocalDateTimeParking = LocalDateTime.now().plusHours(1).plusMinutes(1);
    for (Parking parking : parkingList) {
      parking.setFinalParking(finalLocalDateTimeParking);
      var user = parking.getUser();
      var content = createEmailContentFrom(parking);
      parkingNotificationMessage.send(notificationEmailAddress, user.getEmail(), EMAIL_SUBJECT,
          content);
    }
  }

  private String createEmailContentFrom(Parking parking) {
    var user = parking.getUser();
    var vehicle = parking.getVehicle();
    var parkingHours = parking.getTotalHoursParking();
    var paymentStatus = parking.getParkingPaymentStateDescription(parking);
    var totalAmountToPay = parking.getTotalAmountToPay();
    var predictedValueParkingPeriod = new DecimalFormat("R$ #,##0.00").format(totalAmountToPay);
    return """
        %s. Attention! %s completed hour of your parking period, which will be extended for
         another hour unless the parking lot is vacated. Car %s, license plate %s. Start parking %s.
         Payment status %s. Predicted value for the parked period %s.
        """.formatted(user.getName(), parkingHours, vehicle.getDescription(),
        vehicle.getLicensePlate(),
        DateUtil.localDateTimeToDateWithSlash(parking.getInitialParking()), paymentStatus,
        predictedValueParkingPeriod);
  }
}