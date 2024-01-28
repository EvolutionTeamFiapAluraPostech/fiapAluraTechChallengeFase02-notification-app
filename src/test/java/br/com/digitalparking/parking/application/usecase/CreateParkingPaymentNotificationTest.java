package br.com.digitalparking.parking.application.usecase;

import static br.com.digitalparking.shared.testData.parking.ParkingTestData.createParkingWithPayment;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.digitalparking.parking.model.entity.Parking;
import br.com.digitalparking.parking.model.enums.ParkingTime;
import br.com.digitalparking.parking.model.service.ParkingService;
import br.com.digitalparking.shared.application.validator.EmailValidator;
import br.com.digitalparking.shared.application.validator.UuidValidator;
import br.com.digitalparking.shared.exception.NoResultException;
import br.com.digitalparking.shared.exception.ValidatorException;
import br.com.digitalparking.shared.infrastructure.mail.ParkingNotificationMessage;
import br.com.digitalparking.shared.util.DateUtil;
import java.text.DecimalFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateParkingPaymentNotificationTest {

  private static final String EMAIL_ADDRESS = "kayla45@ethereal.email";
  private static final String EMAIL_SUBJECT = "Parking payment made";
  @Mock
  private ParkingService parkingService;
  @Spy
  private UuidValidator uuidValidator;
  @Spy
  private EmailValidator emailValidator;
  @Mock
  private ParkingNotificationMessage parkingNotificationMessage;
  private CreateParkingPaymentNotification createParkingPaymentNotification;

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
        %s. Parking payment made. Car %s, license plate %s. Parking at %s, %s, %s, %s.
        Initial parking %s. Final parking %s.
        Amount per hour: %s. Total hours: %s.
        Total amount paid: %s. Payment status %s.
        Digital Parking - Transaction ID: %s
        """.formatted(user.getName(), vehicle.getDescription(), vehicle.getLicensePlate(),
        parking.getStreet(), parking.getNeighborhood(), parking.getCity(), parking.getState(),
        initialDate, finalDate, amountPerHour, totalHoursParking, totalAmountPaid, paymentStatus,
        parking.getId());
  }

  @BeforeEach
  void setUp() {
    createParkingPaymentNotification = new CreateParkingPaymentNotification(parkingService,
        uuidValidator, emailValidator, parkingNotificationMessage, EMAIL_ADDRESS);
  }

  @Test
  void shouldNotificateUserOfParkingPayment() {
    var parking = createParkingWithPayment();
    var email = parking.getUser().getEmail();
    var content = createEmailContentFrom(parking);
    when(parkingService.findByIdRequired(parking.getId())).thenReturn(parking);

    assertDoesNotThrow(
        () -> createParkingPaymentNotification.execute(parking.getId().toString(), parking));

    verify(uuidValidator).validate(parking.getId().toString());
    verify(emailValidator).validate(email);
    verify(parkingNotificationMessage).send(EMAIL_ADDRESS, email, EMAIL_SUBJECT, content);
  }

  @Test
  void shouldThrowExceptionWhenUserWasNotFound() {
    var parking = createParkingWithPayment();
    var email = parking.getUser().getEmail();
    var content = createEmailContentFrom(parking);
    when(parkingService.findByIdRequired(parking.getId())).thenThrow(NoResultException.class);

    assertThrows(NoResultException.class,
        () -> createParkingPaymentNotification.execute(parking.getId().toString(), parking));

    verify(uuidValidator).validate(parking.getId().toString());
    verify(emailValidator, never()).validate(email);
    verify(parkingNotificationMessage, never()).send(EMAIL_ADDRESS, email, EMAIL_SUBJECT, content);
  }

  @ParameterizedTest
  @ValueSource(strings = {"email.domain.com", " email.domain.com", "@", "1", "email@domain",
      "A@b@c@example.com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", "email @example.com"})
  void shouldThrowExceptionWhenUserEmailIsInvalid(String email) {
    var parking = createParkingWithPayment();
    parking.getUser().setEmail(email);
    var content = createEmailContentFrom(parking);
    when(parkingService.findByIdRequired(parking.getId())).thenReturn(parking);

    assertThrows(ValidatorException.class,
        () -> createParkingPaymentNotification.execute(parking.getId().toString(), parking));

    verify(uuidValidator).validate(parking.getId().toString());
    verify(emailValidator).validate(email);
    verify(parkingNotificationMessage, never()).send(EMAIL_ADDRESS, email, EMAIL_SUBJECT, content);
  }
}