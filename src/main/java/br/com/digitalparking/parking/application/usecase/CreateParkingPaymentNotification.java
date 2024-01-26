package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.application.validator.EmailValidator;
import br.com.digitalparking.shared.application.validator.UuidValidator;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import br.com.digitalparking.shared.util.DateUtil;
import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateParkingPaymentNotification {

  private static final String EMAIL_SUBJECT = "Parking payment made";
  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;
  private final EmailValidator emailValidator;
  private final ParkingNotificationMessage parkingNotificationMessage;
  private final String notificationEmailAddress;

  public CreateParkingPaymentNotification(ParkingService parkingService,
      UuidValidator uuidValidator, EmailValidator emailValidator,
      ParkingNotificationMessage parkingNotificationMessage,
      @Value("${spring.mail.username}") String notificationEmailAddress) {
    this.parkingService = parkingService;
    this.uuidValidator = uuidValidator;
    this.emailValidator = emailValidator;
    this.parkingNotificationMessage = parkingNotificationMessage;
    this.notificationEmailAddress = notificationEmailAddress;
  }

  public void execute(String uuid, Parking parking) {
    uuidValidator.validate(uuid);
    parkingService.findByIdRequired(UUID.fromString(uuid));
    var user = parking.getUser();
    emailValidator.validate(user.getEmail());
    var content = createEmailContentFrom(parking);
    parkingNotificationMessage.send(notificationEmailAddress, user.getEmail(), EMAIL_SUBJECT,
        content);
  }

  private String createEmailContentFrom(Parking parking) {
    var user = parking.getUser();
    var vehicle = parking.getVehicle();
    var initialDate = DateUtil.localDateTimeToDateWithSlash(parking.getInitialParking());
    var finalDate = DateUtil.localDateTimeToDateWithSlash(parking.getFinalParking());
    var totalHoursParking = getTotalHoursParking(parking);
    var totalAmountPaid = getTotalAmountPaid(parking);
    return """
        %s. Parking payment made. Car %s, license plate %s. Parking at %s, %s, %s, %s.
        Initial parking %s. Final parking %s. Total hours: %s. Total amount paid: %s
        """.formatted(user.getName(), vehicle.getDescription(), vehicle.getLicensePlate(),
        parking.getStreet(), parking.getNeighborhood(), parking.getCity(), parking.getState(),
        initialDate, finalDate, totalHoursParking, totalAmountPaid);
  }

  private String getTotalAmountPaid(Parking parking) {
    if (parking.getParkingPayment() == null
        || parking.getParkingPayment().getPaymentValue() == null) {
      return "R$ 0.00";
    }
    return new DecimalFormat("R$ #,##0.00").format(
        parking.getParkingPayment().getPaymentValue());
  }

  private long getTotalHoursParking(Parking parking) {
    if (parking.getInitialParking() == null || parking.getFinalParking() == null) {
      return 0;
    }
    return ChronoUnit.HOURS.between(parking.getInitialParking(),
        parking.getFinalParking());
  }
}
