package br.com.digitalparking.parking.application.usecase;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.application.validator.EmailValidator;
import br.com.digitalparking.shared.application.validator.UuidValidator;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import br.com.digitalparking.shared.util.DateUtil;
import java.text.DecimalFormat;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateParkingCloseNotification {

  private static final String EMAIL_SUBJECT = "Parking closed";
  private final ParkingService parkingService;
  private final UuidValidator uuidValidator;
  private final EmailValidator emailValidator;
  private final ParkingNotificationMessage parkingNotificationMessage;
  private final String notificationEmailAddress;

  public CreateParkingCloseNotification(ParkingService parkingService,
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
    var parkingFound = parkingService.findByIdRequired(UUID.fromString(uuid));
    parkingFound.setParkingPayment(parking.getParkingPayment());
    var user = parkingFound.getUser();
    emailValidator.validate(user.getEmail());
    var content = createEmailContentFrom(parkingFound);
    parkingNotificationMessage.send(notificationEmailAddress, user.getEmail(), EMAIL_SUBJECT,
        content);
  }

  private String createEmailContentFrom(Parking parking) {
    var user = parking.getUser();
    var vehicle = parking.getVehicle();
    var initialDate = DateUtil.localDateTimeToDateWithSlash(parking.getInitialParking());
    var finalDate = DateUtil.localDateTimeToDateWithSlash(parking.getFinalParking());
    var amountPerHour = new DecimalFormat("R$ #,##0.00").format(ParkingTime.ONE.getValuePerHour());
    var totalHoursParking = parking.getTotalHoursParking();
    var totalAmountPaid = parking.getTotalAmountPaid();
    var paymentStatus = parking.getParkingPaymentStateDescription();
    return """
        %s. Parking closed. Car %s, license plate %s. Parking at %s, %s, %s, %s.
        Initial parking %s. Final parking %s. Amount per hour: %s. Total hours: %s.
        Total amount paid: %s. Payment status %s.
        """.formatted(user.getName(), vehicle.getDescription(), vehicle.getLicensePlate(),
        parking.getStreet(), parking.getNeighborhood(), parking.getCity(), parking.getState(),
        initialDate, finalDate, amountPerHour, totalHoursParking, totalAmountPaid, paymentStatus);
  }
}
